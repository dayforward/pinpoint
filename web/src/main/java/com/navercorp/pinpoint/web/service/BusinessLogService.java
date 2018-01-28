package com.navercorp.pinpoint.web.service;

/**
 * [XINGUANG]Created by Administrator on 2017/6/12.
 */
public interface BusinessLogService {
    String getBusinessLog(String agentId,String transactionId,String spanId,long time);
}


