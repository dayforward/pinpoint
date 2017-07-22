package com.navercorp.pinpoint.common.server.bo.stat;

import java.util.List;

public class BussinessLogBo {
	
	private String agentId;
	
	private long startTimestamp;
	
	private List<BussinessLogV1Bo> bussinessLogs;

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

	public List<BussinessLogV1Bo> getBussinessLogs() {
		return bussinessLogs;
	}

	public void setBussinessLogs(List<BussinessLogV1Bo> bussinessLogs) {
		this.bussinessLogs = bussinessLogs;
	}

}
