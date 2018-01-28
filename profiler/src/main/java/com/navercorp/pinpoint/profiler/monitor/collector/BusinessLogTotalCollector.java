package com.navercorp.pinpoint.profiler.monitor.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.navercorp.pinpoint.profiler.monitor.collector.businesslog.BusinessLogV1Collector;
import com.navercorp.pinpoint.profiler.monitor.collector.businesslog.BusinessLogVXMetaCollector;
import com.navercorp.pinpoint.thrift.dto.TBusinessLog;
import com.navercorp.pinpoint.thrift.dto.TBusinessLogV1;

/**
 * [XINGUANG]
 */
public class BusinessLogTotalCollector implements BusinessLogMetaCollector<TBusinessLog>{
	
	private final Logger logger = LoggerFactory.getLogger(BusinessLogTotalCollector.class);

	private  BusinessLogVXMetaCollector businessLogVXMetaCollector;

	@Inject
	public BusinessLogTotalCollector(BusinessLogVXMetaCollector businessLogVXMetaCollector) {
		this.businessLogVXMetaCollector = businessLogVXMetaCollector;
		initDailyLogLineMap();
	}

	@Override
	public  TBusinessLog collect() {
		logger.info("start collect bussiness log");
		TBusinessLog tBusinessLog = new TBusinessLog();
		tBusinessLog.setBusinessLogV1s(businessLogVXMetaCollector.collect());
		return tBusinessLog;	
	}

	@Override
	public void saveLogMark() {
		businessLogVXMetaCollector.saveLogMark();
	}


	private void initDailyLogLineMap() {
		businessLogVXMetaCollector.initDailyLogLineMap();
	}

}
