package com.navercorp.pinpoint.web.service;

/**
 * Created by Administrator on 2017/6/12.
 */
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.navercorp.pinpoint.web.dao.BusinessLogDao;

@Service
public class BusinessLogServiceImpl implements BusinessLogService{
    @Autowired
    BusinessLogDao businessLogDao;
    @Override
    public  String getBusinessLog(String transactionId,String spanId,long time){
        transactionId = transactionId.replace('@','^');
        System.out.println("transactionId = " + transactionId + " ; spanId = " + spanId +  " ; time = " + time);
        List<String> lString =  businessLogDao.getBusinessLog(transactionId,spanId,time);
        StringBuilder sb = new StringBuilder();
        for (String str : lString){
            sb.append(str);
            sb.append("\r\n");
        }
        return sb.toString();
    }
}
