package com.navercorp.pinpoint.common.server.bo.stat;

public class BussinessLogV1Bo implements BussinessLogDataPoint{
	
	private String agentId;
    private long startTimestamp;
    private long timestamp;
    private String time;
    private String threadName;
    private String logLevel;
    private String className;
    private String transactionId;
    private String spanId; 
    private String message;

    
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


	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getThreadName() {
		return threadName;
	}


	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}


	public String getLogLevel() {
		return logLevel;
	}


	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getSpanId() {
		return spanId;
	}


	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public BussinessLogType geTBussinessLogType() {
		// TODO Auto-generated method stub
		return BussinessLogType.BUSSINESS_LOG_V1;
	}


	@Override
	public String getTransactionIdANDSpanId() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(this.transactionId).append("#").append(this.spanId);
		return sb.toString();
	}


}
