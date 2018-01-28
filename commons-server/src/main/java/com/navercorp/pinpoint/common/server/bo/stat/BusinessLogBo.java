package com.navercorp.pinpoint.common.server.bo.stat;

import java.util.List;

public class BusinessLogBo {
	
	private String agentId;
	
	private long startTimestamp;
	
	private List<BusinessLogV1Bo> businessLogs;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public long getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public List<BusinessLogV1Bo> getBusinessLogs() {
		return businessLogs;
	}

	public void setBusinessLogs(List<BusinessLogV1Bo> businessLogs) {
		this.businessLogs = businessLogs;
	}

}
