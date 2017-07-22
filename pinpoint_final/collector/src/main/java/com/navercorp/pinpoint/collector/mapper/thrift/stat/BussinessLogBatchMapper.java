package com.navercorp.pinpoint.collector.mapper.thrift.stat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.collector.mapper.thrift.ThriftBoMapper;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogV1Bo;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogBo;
import com.navercorp.pinpoint.thrift.dto.TBussinessLog;
import com.navercorp.pinpoint.thrift.dto.TBussinessLogBatch;
import com.navercorp.pinpoint.thrift.dto.TBussinessLogV1;

@Component
public class BussinessLogBatchMapper implements ThriftBoMapper<BussinessLogBo, TBussinessLogBatch>{

	@Override
	public BussinessLogBo map(TBussinessLogBatch TBussinessLogBatch) {
		// TODO Auto-generated method stub
		if (!TBussinessLogBatch.isSetBussinessLogs()) {
			return null;
		}
		BussinessLogBo bussinessLogBo = new BussinessLogBo();
		String agentId = TBussinessLogBatch.getAgentId();
		long startTimestamp = TBussinessLogBatch.getStartTimestamp();
		bussinessLogBo.setAgentId(agentId);
		bussinessLogBo.setStartTimestamp(startTimestamp);
		
		int size = TBussinessLogBatch.getBussinessLogsSize();

		List<BussinessLogV1Bo> bussinessLogs = new ArrayList<BussinessLogV1Bo>(size);
		BussinessLogV1Bo BussinessLogV1Bo = new BussinessLogV1Bo();
		for(TBussinessLog tBussinessLog : TBussinessLogBatch.getBussinessLogs()) {			
			long timestamp = tBussinessLog.getTimestamp();
			for(TBussinessLogV1 tBussinessLogV1 : tBussinessLog.getBussinessLogV1s()) {
				BussinessLogV1Bo.setTime(tBussinessLogV1.getTime());
				BussinessLogV1Bo.setThreadName(tBussinessLogV1.getThreadName());
				BussinessLogV1Bo.setLogLevel(tBussinessLogV1.getLogLevel());
				BussinessLogV1Bo.setClassName(tBussinessLogV1.getClassName());
				BussinessLogV1Bo.setMessage(tBussinessLogV1.getMessage());
				BussinessLogV1Bo.setTransactionId(tBussinessLogV1.getTransactionId());
				BussinessLogV1Bo.setSpanId(tBussinessLogV1.getSpanId());
				
				BussinessLogV1Bo.setAgentId(agentId);
				BussinessLogV1Bo.setStartTimestamp(startTimestamp);
				BussinessLogV1Bo.setTimestamp(timestamp);
	
				bussinessLogs.add(BussinessLogV1Bo);
			}
		}
 		bussinessLogBo.setBussinessLogs(bussinessLogs);
		return bussinessLogBo;
	}

}
