package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.PinpointConstants;
import com.navercorp.pinpoint.common.server.bo.serializer.RowKeyEncoder;
import com.navercorp.pinpoint.common.util.BytesUtils;

import static com.navercorp.pinpoint.common.hbase.HBaseTables.AGENT_NAME_MAX_LEN;

@Component
public class BussinessLogRowKeyEncoder implements RowKeyEncoder<BussinessLogRowKeyComponent>{

	@Override
    public byte[] encodeRowKey(BussinessLogRowKeyComponent component) {
		if (component == null) {
			throw new NullPointerException("component must not be null");
		}
		byte[] bAgentId = BytesUtils.toBytes(component.getAgentId());
		byte[] bStatType = new byte[]{component.getBussinessLogType().getRawTypeCode()};
		byte[] bTransactionIdAndSpanId = BytesUtils.toBytes(component.getTransactionIdANDSpanId());
		byte[] rowKey = new byte[AGENT_NAME_MAX_LEN + bStatType.length + PinpointConstants.TRANSACTIONID_AND_SPANID_LEN];
		
		BytesUtils.writeBytes(rowKey, 0, bAgentId);
        BytesUtils.writeBytes(rowKey, AGENT_NAME_MAX_LEN, bStatType);
        BytesUtils.writeBytes(rowKey, AGENT_NAME_MAX_LEN + bStatType.length, bTransactionIdAndSpanId);
		return rowKey;
    }

}
