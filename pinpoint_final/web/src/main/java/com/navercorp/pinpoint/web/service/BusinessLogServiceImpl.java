package com.navercorp.pinpoint.web.service;


import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.navercorp.pinpoint.web.dao.businesslog.BusinessLogDao;

/**
 * [XINGUANG]Created by Administrator on 2017/6/12.
 */
@Service
public class BusinessLogServiceImpl implements BusinessLogService{
    @Autowired
    BusinessLogDao businessLogDao;
    @Override
    public  String getBusinessLog(String agentId, String transactionId,String spanId,long time){
        System.out.println("agentId = "+ agentId + "transactionId = " + transactionId + " ; spanId = " + spanId +  " ; time = " + time);
        List<String> lString =  businessLogDao.getBusinessLog(agentId,transactionId,spanId,time);
        StringBuilder sb = new StringBuilder();
        for (String str : lString){
            sb.append(str);
            sb.append("\r\n");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
