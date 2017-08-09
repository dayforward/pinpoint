package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogType;

/**
 * [XINGUANG]
 */
public class BusinessLogRowKeyComponent {
	
    private final String agentId;
    private final BusinessLogType businessLogType;
    private final String transactionIdANDSpanId;


    public BusinessLogRowKeyComponent(String agentId, BusinessLogType businessLogType, String transactionIdANDSpanId) {
        this.agentId = agentId;
        this.businessLogType = businessLogType;
        this.transactionIdANDSpanId = transactionIdANDSpanId;
    }

    public String getAgentId() {
        return this.agentId;
    }

    public BusinessLogType getBusinessLogType() {
        return this.businessLogType;
    }
    
    public String getTransactionIdANDSpanId() {
    	return this.transactionIdANDSpanId;
    }
}
