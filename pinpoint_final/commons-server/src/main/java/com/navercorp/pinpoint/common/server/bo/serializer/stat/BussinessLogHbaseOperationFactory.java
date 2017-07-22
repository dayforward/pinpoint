package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.shaded.org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.navercorp.pinpoint.common.server.bo.serializer.HbaseSerializer;
import com.navercorp.pinpoint.common.server.bo.stat.AgentStatType;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogDataPoint;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogType;
import com.sematext.hbase.wd.AbstractRowKeyDistributor;

@Component
public class BussinessLogHbaseOperationFactory {
	
	 	private final BussinessLogRowKeyEncoder rowKeyEncoder;

	    private final BussinessLogRowKeyDecoder rowKeyDecoder;

	    private final AbstractRowKeyDistributor rowKeyDistributor;

	    @Autowired
	    public BussinessLogHbaseOperationFactory(
	    		BussinessLogRowKeyEncoder rowKeyEncoder,
	    		BussinessLogRowKeyDecoder rowKeyDecoder,
	            @Qualifier("bussinessLogRowKeyDistributor") AbstractRowKeyDistributor rowKeyDistributor) {
	        Assert.notNull(rowKeyEncoder, "rowKeyEncoder must not be null");
	        Assert.notNull(rowKeyDecoder, "rowKeyDecoder must not be null");
	        Assert.notNull(rowKeyDistributor, "rowKeyDistributor must not be null");
	        this.rowKeyEncoder = rowKeyEncoder;
	        this.rowKeyDecoder = rowKeyDecoder;
	        this.rowKeyDistributor = rowKeyDistributor;
	    }

	    public <T extends BussinessLogDataPoint> List<Put> createPuts(String agentId, BussinessLogType bussinessLogType, List<T> bussinessLogDataPoints, HbaseSerializer<List<T>, Put> bussinessLogSerializer) {
	    	if (CollectionUtils.isEmpty(bussinessLogDataPoints)) {
	    		return Collections.emptyList();
	    	}
	    	Map<String, List<T>> transactionIdAndSpanIdslots = slotBussinessLogDataPoints(bussinessLogDataPoints);
	    	List<Put> puts = new ArrayList<Put>();
	    	for (Map.Entry<String , List<T>> tANDsIdslot : transactionIdAndSpanIdslots.entrySet()) {
	    		String tANDsId = tANDsIdslot.getKey();
	    		List<T> slottedBussinessLogDataPoints = tANDsIdslot.getValue();
	    		
	    		final BussinessLogRowKeyComponent rowkeyComponent = new BussinessLogRowKeyComponent(agentId, bussinessLogType, tANDsId);
	    		byte[] rowKey = this.rowKeyEncoder.encodeRowKey(rowkeyComponent);
	    		byte[] distributeRowKey = this.rowKeyDistributor.getDistributedKey(rowKey);
	    		
	    		Put put = new Put(distributeRowKey);
	    		bussinessLogSerializer.serialize(slottedBussinessLogDataPoints, put, null);
	    		puts.add(put);
	    	}
	    	return puts;
	    }
	    
	    private <T extends BussinessLogDataPoint> Map<String, List<T>> slotBussinessLogDataPoints(List<T> bussinessLogDataPoints) {
	    	Map<String, List<T>> transactionIdAndSpanIdslots = new TreeMap<String, List<T>>();
	    	for (T bussinessLogDataPoint : bussinessLogDataPoints) {
	    		String transactionIdAndSpanId = bussinessLogDataPoint.getTransactionIdANDSpanId();
	    		List<T> slottedDataPoints = transactionIdAndSpanIdslots.get(transactionIdAndSpanId);
	    		if (slottedDataPoints == null) {
	    			slottedDataPoints = new ArrayList<T>();
	    			transactionIdAndSpanIdslots.put(transactionIdAndSpanId, slottedDataPoints);
	    		}
	    		slottedDataPoints.add(bussinessLogDataPoint);
	    	}
	    	return transactionIdAndSpanIdslots;
	    }
	    
	    public String getAgentId(byte[] distributedRowKey) {
	        byte[] originalRowKey = this.rowKeyDistributor.getOriginalKey(distributedRowKey);
	        return this.rowKeyDecoder.decodeRowKey(originalRowKey).getAgentId();
	    }
	    
	    public BussinessLogType getAgentStatType(byte[] distributedRowKey) {
	        byte[] originalRowKey = this.rowKeyDistributor.getOriginalKey(distributedRowKey);
	        return this.rowKeyDecoder.decodeRowKey(originalRowKey).getBussinessLogType();
	    }
	    
	    public String getTransactionIdANDSpanId(byte[] distributedRowKey){
	        byte[] originalRowKey = this.rowKeyDistributor.getOriginalKey(distributedRowKey);
	        return this.rowKeyDecoder.decodeRowKey(originalRowKey).getTransactionIdANDSpanId();
	    }
}
