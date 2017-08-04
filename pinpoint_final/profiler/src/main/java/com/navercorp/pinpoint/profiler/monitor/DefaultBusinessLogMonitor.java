package com.navercorp.pinpoint.profiler.monitor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.navercorp.pinpoint.common.util.PinpointThreadFactory;
import com.navercorp.pinpoint.profiler.context.module.AgentId;
import com.navercorp.pinpoint.profiler.context.module.AgentStartTime;
import com.navercorp.pinpoint.profiler.context.module.StatDataSender;
import com.navercorp.pinpoint.profiler.monitor.collector.BusinessLogMetaCollector;
import com.navercorp.pinpoint.profiler.sender.DataSender;
import com.navercorp.pinpoint.profiler.sender.EmptyDataSender;
import com.navercorp.pinpoint.thrift.dto.TBusinessLog;



public class DefaultBusinessLogMonitor implements BusinessLogMonitor {
	
	public static final long DEFAULT_COLLECTION_INTERVAL_MS = 1000 * 5;	
	private static final int DEFAULT_NUM_COLLECTIONS_PER_SEND = 6;
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultBusinessLogMonitor.class);
	
	private long collectionIntervalMs;

	private final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1, new PinpointThreadFactory("Pinpoint-BusinessLogInfo-monitor", true));

	private CollectBusinessLog collectBusinessLog;
	
	//private BusinessLogInfo businessLogInfo;



	@Inject
    public DefaultBusinessLogMonitor(@StatDataSender DataSender dataSender,
                                   @AgentId String agentId, @AgentStartTime long agentStartTimestamp,
                                   @Named("BusinessLogTotalCollector") BusinessLogMetaCollector<TBusinessLog> businessLogMetaCollector) {
        this(dataSender, agentId, agentStartTimestamp, businessLogMetaCollector, DEFAULT_COLLECTION_INTERVAL_MS, DEFAULT_NUM_COLLECTIONS_PER_SEND);
    }
	
	public DefaultBusinessLogMonitor(DataSender dataSender, String agentId, long agentStartTimestamp,
									  BusinessLogMetaCollector<TBusinessLog> businessLogMetaCollector, long collectionInterval, int numCollectionsPerBatch) {
		 if (dataSender == null) {
	            throw new NullPointerException("dataSender must not be null");
	        }
	        if (agentId == null) {
	            throw new NullPointerException("agentId must not be null");
	        }
	        if (businessLogMetaCollector == null) {
	            throw new NullPointerException("agentStatCollector must not be null");
	        }

	        this.collectionIntervalMs = collectionInterval;
	        this.collectBusinessLog = new CollectBusinessLog(dataSender, agentId, agentStartTimestamp, businessLogMetaCollector, numCollectionsPerBatch);
	        
	        preLoadClass(agentId, agentStartTimestamp, businessLogMetaCollector);
	}

	private void preLoadClass(String agentId, long agentStartTimestamp, BusinessLogMetaCollector<TBusinessLog> businessLogMetaCollector) {
		logger.debug("pre-load class start");
		//CollectBusinessLog collectBusinessLog = new CollectBusinessLog(EmptyDataSender.INSTANCE, agentId, agentStartTimestamp, businessLogMetaCollector, 1);

        // It is called twice to initialize some fields.
		//collectBusinessLog.run();
		//collectBusinessLog.run();
        logger.debug("pre-load class end");
	}
	
	@Override
	public void start() {
		executor.scheduleAtFixedRate(collectBusinessLog, this.collectionIntervalMs, this.collectionIntervalMs, TimeUnit.MILLISECONDS);
        logger.info("BusinessLog monitor started");
	}
	
	@Override
	public void stop() {
		 executor.shutdown();
	        try {
	            executor.awaitTermination(3000, TimeUnit.MILLISECONDS);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	        logger.info("BusinessLog monitor stopped");
	}
}
