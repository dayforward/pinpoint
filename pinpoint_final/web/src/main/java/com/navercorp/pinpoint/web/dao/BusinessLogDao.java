package com.navercorp.pinpoint.web.dao;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public interface BusinessLogDao {
    List<String> getBusinessLog(String transactionId,String spanId,long time);
}
