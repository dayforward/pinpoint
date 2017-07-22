package com.navercorp.pinpoint.profiler.monitor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.navercorp.pinpoint.profiler.monitor.collector.BusinessLogMetaCollector;
import com.navercorp.pinpoint.profiler.sender.DataSender;
import com.navercorp.pinpoint.thrift.dto.TAgentStat;
import com.navercorp.pinpoint.thrift.dto.TAgentStatBatch;
import com.navercorp.pinpoint.thrift.dto.TBussinessLog;
import com.navercorp.pinpoint.thrift.dto.TBussinessLogBatch;

public class CollectBussinessLog implements Runnable{

	private final Logger logger = LoggerFactory.getLogger(CollectBussinessLog.class);
	
	private DataSender dataSender;
    private String agentId;
    private long agentStartTimestamp;
    private BusinessLogMetaCollector<TBussinessLog> bussinessLogMetaCollector;
    private int numCollectionsPerBatch;
	
	private int collectCount = 0;
    private long prevCollectionTimestamp = System.currentTimeMillis();
    private List<TBussinessLog> bussinessLogs;
    
    public CollectBussinessLog(DataSender dataSender, String agentId, long agentStartTimestamp,
                               BusinessLogMetaCollector<TBussinessLog> businessLogMetaCollector, int numCollectionsPerBatch) {
    	this.dataSender = dataSender;
    	this.agentId = agentId;
    	this.agentStartTimestamp = agentStartTimestamp;
    	this.bussinessLogMetaCollector = bussinessLogMetaCollector;
    	this.numCollectionsPerBatch = numCollectionsPerBatch;
    	this.bussinessLogs = new ArrayList<TBussinessLog>(numCollectionsPerBatch);
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
        final long currentCollectionTimestamp = System.currentTimeMillis();
        final long collectInterval = currentCollectionTimestamp - this.prevCollectionTimestamp;
        try {
            final TBussinessLog bussinessLog = bussinessLogMetaCollector.collect();
            logger.info(bussinessLog.toString());
            bussinessLog.setTimestamp(currentCollectionTimestamp);
            bussinessLog.setCollectInterval(collectInterval);
            this.bussinessLogs.add(bussinessLog);
            if (++this.collectCount >= numCollectionsPerBatch) {
                sendBusinessLogs();
                this.collectCount = 0;
            }
        } catch (Exception ex) {
            logger.warn("bussinessLog collect failed. Caused:{}", ex.getMessage(), ex);
        } finally {
            this.prevCollectionTimestamp = currentCollectionTimestamp;
        }
	}
	
	private void sendBusinessLogs() {
        // prepare TBusinessLog object.
        // TODO multi thread issue.
        // If we reuse TBusinessLog, there could be concurrency issue because data sender runs in a different thread.
        final TBussinessLogBatch bussinessLogBatch = new TBussinessLogBatch();
        bussinessLogBatch.setAgentId(agentId);
        bussinessLogBatch.setStartTimestamp(agentStartTimestamp);
        bussinessLogBatch.setBussinessLogs(this.agentLogs);
        // If we reuse businessLogs list, there could be concurrency issue because data sender runs in a different
        // thread.
        // So create new list.
        this.bussinessLogs = new ArrayList<TBussinessLog>(numCollectionsPerBatch);
        logger.trace("collect bussinessLogBatch:{}", bussinessLogBatch);
        dataSender.send(bussinessLogBatch);
	}

}
