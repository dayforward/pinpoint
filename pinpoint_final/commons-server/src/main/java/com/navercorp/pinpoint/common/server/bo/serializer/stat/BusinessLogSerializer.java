package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Put;

import com.navercorp.pinpoint.common.buffer.AutomaticBuffer;
import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.hbase.HBaseTables;
import com.navercorp.pinpoint.common.server.bo.codec.stat.BusinessLogEncoder;
import com.navercorp.pinpoint.common.server.bo.serializer.HbaseSerializer;
import com.navercorp.pinpoint.common.server.bo.serializer.SerializationContext;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogDataPoint;

public class BusinessLogSerializer<T extends BusinessLogDataPoint> implements HbaseSerializer<List<T>, Put>  {
	
	private final BusinessLogEncoder<T> encoder;
	
	protected BusinessLogSerializer(BusinessLogEncoder<T> encoder) {
		this.encoder = encoder;
	}

	@Override
	public void serialize(List<T> BusinessLogV1Bo, Put put, SerializationContext context) {
		// TODO Auto-generated method stub
		if (CollectionUtils.isEmpty(BusinessLogV1Bo)) {
            throw new IllegalArgumentException("BusinessLogBos should not be empty");
        }
        long currentTime = System.currentTimeMillis();
		ByteBuffer qualifierBuffer = this.encoder.getQualifierBuffer(currentTime);
        ByteBuffer valueBuffer = this.encoder.encodeValue(BusinessLogV1Bo);
        put.addColumn(HBaseTables.BUSSINESS_MESSAGEINFO, qualifierBuffer, HConstants.LATEST_TIMESTAMP, valueBuffer);
	}

}
