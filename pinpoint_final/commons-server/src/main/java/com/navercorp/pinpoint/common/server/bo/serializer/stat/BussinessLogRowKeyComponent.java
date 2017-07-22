package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogType;

public class BussinessLogRowKeyComponent {
	
    private final String agentId;
    private final BussinessLogType bussinessLogType;
    private final String transactionIdANDSpanId;


    public BussinessLogRowKeyComponent(String agentId, BussinessLogType bussinessLogType, String transactionIdANDSpanId) {
        this.agentId = agentId;
        this.bussinessLogType = bussinessLogType;
        this.transactionIdANDSpanId = transactionIdANDSpanId;
    }

    public String getAgentId() {
        return this.agentId;
    }

    public BussinessLogType getBussinessLogType() {
        return this.bussinessLogType;
    }
    
    public String getTransactionIdANDSpanId() {
    	return this.transactionIdANDSpanId;
    }
}
