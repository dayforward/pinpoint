package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Put;

import com.navercorp.pinpoint.common.buffer.AutomaticBuffer;
import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.hbase.HBaseTables;
import com.navercorp.pinpoint.common.server.bo.codec.stat.BussinessLogEncoder;
import com.navercorp.pinpoint.common.server.bo.serializer.HbaseSerializer;
import com.navercorp.pinpoint.common.server.bo.serializer.SerializationContext;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogDataPoint;

public class BussinessLogSerializer<T extends BussinessLogDataPoint> implements HbaseSerializer<List<T>, Put>  {
	
	private final BussinessLogEncoder<T> encoder;
	
	protected BussinessLogSerializer(BussinessLogEncoder<T> encoder) {
		this.encoder = encoder;
	}

	@Override
	public void serialize(List<T> BussinessLogV1Bo, Put put, SerializationContext context) {
		// TODO Auto-generated method stub
		if (CollectionUtils.isEmpty(BussinessLogV1Bo)) {
            throw new IllegalArgumentException("BussinessLogBos should not be empty");
        }
        long currentTime = System.currentTimeMillis();
		ByteBuffer qualifierBuffer = this.encoder.getQualifierBuffer(currentTime);
        ByteBuffer valueBuffer = this.encoder.encodeValue(BussinessLogV1Bo);
        put.addColumn(HBaseTables.BUSSINESS_MESSAGEINFO, qualifierBuffer, HConstants.LATEST_TIMESTAMP, valueBuffer);
	}

}
