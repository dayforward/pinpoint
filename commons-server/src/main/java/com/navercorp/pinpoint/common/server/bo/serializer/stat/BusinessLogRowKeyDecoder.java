package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import static com.navercorp.pinpoint.common.hbase.HBaseTables.AGENT_NAME_MAX_LEN;
import static com.navercorp.pinpoint.common.server.bo.stat.BusinessLogType.TYPE_CODE_BYTE_LENGTH;

import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.PinpointConstants;
import com.navercorp.pinpoint.common.server.bo.serializer.RowKeyDecoder;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogType;
import com.navercorp.pinpoint.common.util.BytesUtils;

/**
 * [XINGUANG]
 */
@Component
public class BusinessLogRowKeyDecoder implements RowKeyDecoder<BusinessLogRowKeyComponent>{

	@Override
	public BusinessLogRowKeyComponent decodeRowKey(byte[] rowkey) {
		final String agentId = BytesUtils.safeTrim(BytesUtils.toString(rowkey, 0, AGENT_NAME_MAX_LEN));
		final BusinessLogType businessLogType = BusinessLogType.fromTypeCode(rowkey[AGENT_NAME_MAX_LEN]);
		final String transactionIdANDSpanId = BytesUtils.toString(rowkey, AGENT_NAME_MAX_LEN + TYPE_CODE_BYTE_LENGTH, PinpointConstants.TRANSACTIONID_AND_SPANID_LEN);
		return new BusinessLogRowKeyComponent(agentId, businessLogType, transactionIdANDSpanId);
	}

}
