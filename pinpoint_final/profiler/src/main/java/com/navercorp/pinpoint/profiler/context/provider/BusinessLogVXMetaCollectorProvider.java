package com.navercorp.pinpoint.profiler.context.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.navercorp.pinpoint.bootstrap.config.ProfilerConfig;
import com.navercorp.pinpoint.profiler.monitor.collector.businesslog.BusinessLogV1Collector;
import com.navercorp.pinpoint.profiler.monitor.collector.businesslog.BusinessLogVXMetaCollector;

public class BusinessLogVXMetaCollectorProvider implements Provider<BusinessLogV1Collector>{
	
	private ProfilerConfig profilerConfig;
	
	@Inject
	public BusinessLogVXMetaCollectorProvider(ProfilerConfig profilerConfig) {
		this.profilerConfig = profilerConfig;
	}

	@Override
	public BusinessLogV1Collector get() {
		// TODO Auto-generated method stub
		BusinessLogV1Collector businessLogV1Collector = new BusinessLogV1Collector(profilerConfig);
		return businessLogV1Collector;
	}

}
