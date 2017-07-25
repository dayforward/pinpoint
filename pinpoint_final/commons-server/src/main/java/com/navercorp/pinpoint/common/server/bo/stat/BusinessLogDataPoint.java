package com.navercorp.pinpoint.common.server.bo.stat;

public interface BusinessLogDataPoint {
	String getAgentId();
    void setAgentId(String agentId);
    long getStartTimestamp();
    void setStartTimestamp(long startTimestamp);
    long getTimestamp();
    void setTimestamp(long timestamp);
    String getTransactionIdANDSpanId();
    BusinessLogType geTBusinessLogType();
}
