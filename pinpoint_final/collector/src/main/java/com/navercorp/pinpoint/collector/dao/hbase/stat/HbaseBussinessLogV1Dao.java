package com.navercorp.pinpoint.collector.dao.hbase.stat;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.client.Put;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.navercorp.pinpoint.collector.dao.BussinessLogDao;
import com.navercorp.pinpoint.common.hbase.HBaseTables;
import com.navercorp.pinpoint.common.hbase.HbaseOperations2;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BussinessLogHbaseOperationFactory;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BussinessLogV1Serializer;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogV1Bo;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogType;

@Repository
public class HbaseBussinessLogV1Dao implements BussinessLogDao<BussinessLogV1Bo>{
	
	@Autowired
    private HbaseOperations2 hbaseTemplate;

    @Autowired
    private BussinessLogHbaseOperationFactory bussinessLogHbaseOperationFactory;

    @Autowired
    private BussinessLogV1Serializer bussinessLogV1Serializer;

    @Override
	public void insert(String agentId, List<BussinessLogV1Bo> bussinessLogV1Bo) {
		// TODO Auto-generated method stub
		if (agentId == null) {
            throw new NullPointerException("agentId must not be null");
        }
		if (bussinessLogV1Bo == null) {
			return;
		}
				
		List<Put> bussinessLogPut = bussinessLogHbaseOperationFactory.createPuts(agentId, BussinessLogType.BUSSINESS_LOG_V1, bussinessLogV1Bo, this.bussinessLogV1Serializer);
		if (!bussinessLogPut.isEmpty()) {
            List<Put> rejectedPuts = this.hbaseTemplate.asyncPut(HBaseTables.BUSSINESS_LOG, bussinessLogPut);
            if (CollectionUtils.isNotEmpty(rejectedPuts)) {
                this.hbaseTemplate.put(HBaseTables.BUSSINESS_LOG, rejectedPuts);
            }
        }
	}

}
