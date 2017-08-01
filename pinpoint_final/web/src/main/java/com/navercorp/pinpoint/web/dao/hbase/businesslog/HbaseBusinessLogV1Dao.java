package com.navercorp.pinpoint.web.dao.hbase.businesslog;


import java.util.List;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.navercorp.pinpoint.common.hbase.HBaseTables;
import com.navercorp.pinpoint.common.hbase.HbaseOperations2;
import com.navercorp.pinpoint.common.hbase.RowMapper;
import com.navercorp.pinpoint.common.server.bo.codec.stat.BusinessLogV1Decoder;
import com.navercorp.pinpoint.common.server.bo.stat.AgentStatType;
import com.navercorp.pinpoint.web.dao.businesslog.BusinessLogV1Dao;
import com.navercorp.pinpoint.web.mapper.BusinessLogMapper;

/**
 * Created by Administrator on 2017/6/14.
 */
@Repository
public class HbaseBusinessLogV1Dao implements BusinessLogV1Dao {
	
    private static final int SCANNER_CACHE_SIZE = 20;
    
    @Autowired
    private BusinessLogV1Decoder businessLogV1Decoder;
    
    @Autowired
    private HbaseOperations2 hbaseOperations2;
    
    @Autowired
    @Qualifier("businessLogMapper")
    private RowMapper<String> businessLogMapper;
    
   /* @Autowired
    private BusinessLogMapper businessLogMapper;*/
    
    @Override
    public List<String> getBusinessLog(String agentId, String transactionId, String spanId, long time){
        StringBuilder sb = new StringBuilder();
        sb.append(agentId).append(AgentStatType.JVM_GC).append(transactionId);
        if (transactionId == null) {
            throw new NullPointerException("transactionId must not be null");
        }
        byte[] rowKey = Bytes.toBytes(sb.toString());
        Scan scan = new Scan();
        scan.setMaxVersions(1);
        scan.setCaching(SCANNER_CACHE_SIZE);
        scan.addFamily(HBaseTables.BUSINESS_MESSAGEINFO);
        scan.setRowPrefixFilter(rowKey);
        List<String> agentLogs = this.hbaseOperations2.find(HBaseTables.BUSINESS_LOG, scan, businessLogMapper);
        return agentLogs;
    }
}
