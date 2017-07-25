package com.navercorp.pinpoint.collector.mapper.thrift.stat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.collector.mapper.thrift.ThriftBoMapper;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogV1Bo;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogBo;
import com.navercorp.pinpoint.thrift.dto.TBusinessLog;
import com.navercorp.pinpoint.thrift.dto.TBusinessLogBatch;
import com.navercorp.pinpoint.thrift.dto.TBusinessLogV1;

@Component
public class BusinessLogBatchMapper implements ThriftBoMapper<BusinessLogBo, TBusinessLogBatch>{

	@Override
	public BusinessLogBo map(TBusinessLogBatch TBusinessLogBatch) {
		// TODO Auto-generated method stub
		if (!TBusinessLogBatch.isSetBusinessLogs()) {
			return null;
		}
		BusinessLogBo businessLogBo = new BusinessLogBo();
		String agentId = TBusinessLogBatch.getAgentId();
		long startTimestamp = TBusinessLogBatch.getStartTimestamp();
		businessLogBo.setAgentId(agentId);
		businessLogBo.setStartTimestamp(startTimestamp);
		
		int size = TBusinessLogBatch.getBusinessLogsSize();

		List<BusinessLogV1Bo> businessLogs = new ArrayList<BusinessLogV1Bo>(size);
		BusinessLogV1Bo BusinessLogV1Bo = new BusinessLogV1Bo();
		for(TBusinessLog tBusinessLog : TBusinessLogBatch.getBusinessLogs()) {			
			long timestamp = tBusinessLog.getTimestamp();
			for(TBusinessLogV1 tBusinessLogV1 : tBusinessLog.getBusinessLogV1s()) {
				BusinessLogV1Bo.setTime(tBusinessLogV1.getTime());
				BusinessLogV1Bo.setThreadName(tBusinessLogV1.getThreadName());
				BusinessLogV1Bo.setLogLevel(tBusinessLogV1.getLogLevel());
				BusinessLogV1Bo.setClassName(tBusinessLogV1.getClassName());
				BusinessLogV1Bo.setMessage(tBusinessLogV1.getMessage());
				BusinessLogV1Bo.setTransactionId(tBusinessLogV1.getTransactionId());
				BusinessLogV1Bo.setSpanId(tBusinessLogV1.getSpanId());
				
				BusinessLogV1Bo.setAgentId(agentId);
				BusinessLogV1Bo.setStartTimestamp(startTimestamp);
				BusinessLogV1Bo.setTimestamp(timestamp);
	
				businessLogs.add(BusinessLogV1Bo);
			}
		}
 		businessLogBo.setBusinessLogs(businessLogs);
		return businessLogBo;
	}

}
