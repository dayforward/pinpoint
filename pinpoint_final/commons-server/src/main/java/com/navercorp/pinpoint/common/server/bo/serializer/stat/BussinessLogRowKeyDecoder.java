package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import static com.navercorp.pinpoint.common.hbase.HBaseTables.AGENT_NAME_MAX_LEN;
import static com.navercorp.pinpoint.common.server.bo.stat.BussinessLogType.TYPE_CODE_BYTE_LENGTH;

import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.PinpointConstants;
import com.navercorp.pinpoint.common.server.bo.serializer.RowKeyDecoder;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogType;
import com.navercorp.pinpoint.common.util.BytesUtils;

@Component
public class BussinessLogRowKeyDecoder implements RowKeyDecoder<BussinessLogRowKeyComponent>{

	@Override
	public BussinessLogRowKeyComponent decodeRowKey(byte[] rowkey) {
		// TODO Auto-generated method stub
		final String agentId = BytesUtils.safeTrim(BytesUtils.toString(rowkey, 0, AGENT_NAME_MAX_LEN));
		final BussinessLogType bussinessLogType = BussinessLogType.fromTypeCode(rowkey[AGENT_NAME_MAX_LEN]);
		final String transactionIdANDSpanId = BytesUtils.toString(rowkey, AGENT_NAME_MAX_LEN + TYPE_CODE_BYTE_LENGTH, PinpointConstants.TRANSACTIONID_AND_SPANID_LEN);
		return new BussinessLogRowKeyComponent(agentId, bussinessLogType, transactionIdANDSpanId);
	}

}
