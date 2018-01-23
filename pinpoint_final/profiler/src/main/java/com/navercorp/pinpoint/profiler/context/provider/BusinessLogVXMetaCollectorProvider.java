package com.navercorp.pinpoint.profiler.context.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.navercorp.pinpoint.bootstrap.config.ProfilerConfig;
import com.navercorp.pinpoint.profiler.context.module.AgentId;
import com.navercorp.pinpoint.profiler.context.module.BootstrapJarPaths;
import com.navercorp.pinpoint.profiler.monitor.collector.businesslog.BusinessLogV1Collector;
import com.navercorp.pinpoint.profiler.monitor.collector.businesslog.BusinessLogVXMetaCollector;

public class BusinessLogVXMetaCollectorProvider implements Provider<BusinessLogV1Collector>{
	
	private ProfilerConfig profilerConfig;

	private String agentId;

	private String jarPath;
	
	@Inject
	public BusinessLogVXMetaCollectorProvider(ProfilerConfig profilerConfig, @AgentId String agentId) {
		this.profilerConfig = profilerConfig;
		this.agentId = agentId;
	}

	@Override
	public BusinessLogV1Collector get() {
		// TODO Auto-generated method stub
		BusinessLogV1Collector businessLogV1Collector = new BusinessLogV1Collector(profilerConfig, agentId);
		return businessLogV1Collector;
	}

}
