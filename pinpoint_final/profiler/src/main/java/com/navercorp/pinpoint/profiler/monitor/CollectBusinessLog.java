package com.navercorp.pinpoint.profiler.monitor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.navercorp.pinpoint.profiler.monitor.collector.BusinessLogMetaCollector;
import com.navercorp.pinpoint.profiler.sender.DataSender;
import com.navercorp.pinpoint.thrift.dto.TAgentStat;
import com.navercorp.pinpoint.thrift.dto.TAgentStatBatch;
import com.navercorp.pinpoint.thrift.dto.TBusinessLog;
import com.navercorp.pinpoint.thrift.dto.TBusinessLogBatch;

public class CollectBusinessLog implements Runnable{

	private final Logger logger = LoggerFactory.getLogger(CollectBusinessLog.class);
	
	private DataSender dataSender;
    private String agentId;
    private long agentStartTimestamp;
    private BusinessLogMetaCollector<TBusinessLog> businessLogMetaCollector;
    private int numCollectionsPerBatch;
	
	private int collectCount = 0;
    private long prevCollectionTimestamp = System.currentTimeMillis();
    private List<TBusinessLog> businessLogs;
    
    public CollectBusinessLog(DataSender dataSender, String agentId, long agentStartTimestamp,
                               BusinessLogMetaCollector<TBusinessLog> businessLogMetaCollector, int numCollectionsPerBatch) {
    	this.dataSender = dataSender;
    	this.agentId = agentId;
    	this.agentStartTimestamp = agentStartTimestamp;
    	this.businessLogMetaCollector = businessLogMetaCollector;
    	this.numCollectionsPerBatch = numCollectionsPerBatch;
    	this.businessLogs = new ArrayList<TBusinessLog>(numCollectionsPerBatch);
    }
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
        final long currentCollectionTimestamp = System.currentTimeMillis();
        final long collectInterval = currentCollectionTimestamp - this.prevCollectionTimestamp;
        try {
            final TBusinessLog businessLog = businessLogMetaCollector.collect();
            logger.info(businessLog.toString());
            //if (businessLog.getBusinessLogV1s().size() != 0) {
	            businessLog.setTimestamp(currentCollectionTimestamp);
	            businessLog.setCollectInterval(collectInterval);
	            this.businessLogs.add(businessLog);
	            if (++this.collectCount >= numCollectionsPerBatch) {
	                sendBusinessLogs();
	                this.collectCount = 0;
	            }
           // } else {
            	//logger.info("businessLog is null!");
            //}
        } catch (Exception ex) {
            logger.warn("businessLog collect failed. Caused:{}", ex.getMessage(), ex);
        } finally {
            this.prevCollectionTimestamp = currentCollectionTimestamp;
        }
	}
	
	private void sendBusinessLogs() {
        // prepare TBusinessLog object.
        // TODO multi thread issue.
        // If we reuse TBusinessLog, there could be concurrency issue because data sender runs in a different thread.
        final TBusinessLogBatch businessLogBatch = new TBusinessLogBatch();
        businessLogBatch.setAgentId(agentId);
        businessLogBatch.setStartTimestamp(agentStartTimestamp);
        businessLogBatch.setBusinessLogs(this.businessLogs);
        // If we reuse businessLogs list, there could be concurrency issue because data sender runs in a different
        // thread.
        // So create new list.
        this.businessLogs = new ArrayList<TBusinessLog>(numCollectionsPerBatch);
        logger.trace("collect businessLogBatch:{}", businessLogBatch);
        dataSender.send(businessLogBatch);
	}

}
