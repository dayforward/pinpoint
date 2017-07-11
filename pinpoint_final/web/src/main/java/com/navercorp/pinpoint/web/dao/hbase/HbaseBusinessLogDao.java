package com.navercorp.pinpoint.web.dao.hbase;


import com.navercorp.pinpoint.common.server.bo.AgentEventBo;
import com.navercorp.pinpoint.common.server.util.AgentEventType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.apache.hadoop.hbase.filter.ColumnPrefixFilter;

import com.navercorp.pinpoint.common.hbase.HBaseTables;
import com.navercorp.pinpoint.common.hbase.HbaseOperations2;
import com.navercorp.pinpoint.common.hbase.RowMapper;
import com.navercorp.pinpoint.web.dao.BusinessLogDao;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
@Repository
public class HbaseBusinessLogDao implements BusinessLogDao {
    private static final int SCANNER_CACHE_SIZE = 20;
    @Autowired
    private HbaseOperations2 hbaseOperations2;
    @Autowired
    @Qualifier("businessLogMapper")
    private RowMapper<String> businessLogMapper;
    @Override
    public List<String> getBusinessLog(String transactionId, String spanId, long time){
        if (transactionId == null) {
            throw new NullPointerException("transactionId must not be null");
        }
        byte[] rowKey = Bytes.toBytes(transactionId);

     /* code implements concrete match
        Get get = new Get(rowKey);
        get.addFamily(HBaseTables.MESSAGE_INFO);

        return hbaseOperations2.get(HBaseTables.AGENT_BUSINESS_LOG, get, agentIdMapper);*/


        Scan scan = new Scan();
        scan.setMaxVersions(1);
        scan.setCaching(SCANNER_CACHE_SIZE);
        scan.addFamily(HBaseTables.MESSAGE_INFO);
        scan.setRowPrefixFilter(rowKey);

        System.out.println("*************************rowKey = " + rowKey + "**************************");

        List<String> agentLogs = this.hbaseOperations2.find(HBaseTables.AGENT_BUSINESS_LOG, scan, businessLogMapper);
        return agentLogs;
    }
}
