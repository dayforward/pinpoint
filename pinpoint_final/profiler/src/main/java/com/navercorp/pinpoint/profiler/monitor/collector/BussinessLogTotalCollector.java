package com.navercorp.pinpoint.profiler.monitor.collector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.navercorp.pinpoint.bootstrap.config.ProfilerConfig;
import com.navercorp.pinpoint.profiler.context.module.AgentId;
import com.navercorp.pinpoint.profiler.context.module.AgentStartTime;
import com.navercorp.pinpoint.profiler.monitor.collector.businesslog.BusinessLogV1Collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.navercorp.pinpoint.thrift.dto.TBussinessLog;

public class BussinessLogTotalCollector implements BusinessLogMetaCollector<TBussinessLog>{
	
	private final Logger logger = LoggerFactory.getLogger(BussinessLogTotalCollector.class);

	private final String agentId;
	private final long agentStartTimestamp;
	private final BusinessLogV1Collector businessLogV1Collector;

	
	@Inject
	public BussinessLogTotalCollector(@AgentId String agentId,
									  @AgentStartTime long agentStartTimestamp,
									  BusinessLogV1Collector businessLogV1Collector) {
		if (businessLogV1Collector == null) {
			throw new NullPointerException("businessLogV1Collector must not be null");
		}
		this.agentId = agentId;
		this.agentStartTimestamp = agentStartTimestamp;
		this.businessLogV1Collector = businessLogV1Collector;
	}

	@Override
	public  TBussinessLog collect() {
		// TODO Auto-generated method stub
		logger.info("start collect bussiness log");
		//收集日志
		try {
			TBussinessLog tBussinessLog = new TBusinessLog();
			tBussinessLog.setAgentId(agentId);
			tBussinessLog.setStartTimestamp(agentStartTimestamp);
			tBussinessLog.setTBusinessLogV1(businessLogV1Collector.collect());
			return tBussinessLog;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}
