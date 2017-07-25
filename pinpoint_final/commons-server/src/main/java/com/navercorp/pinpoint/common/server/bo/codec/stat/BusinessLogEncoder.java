package com.navercorp.pinpoint.common.server.bo.codec.stat;

import java.nio.ByteBuffer;
import java.util.List;

import com.navercorp.pinpoint.common.buffer.AutomaticBuffer;
import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogDataPoint;

public class BusinessLogEncoder<T extends BusinessLogDataPoint> {

	private final BusinessLogCodec<T> codec;
	
	public BusinessLogEncoder(BusinessLogCodec<T> codec) {
		this.codec = codec;
	}
	
	public ByteBuffer getQualifierBuffer(long currentTime) { 
		 Buffer qualifierBuffer = new AutomaticBuffer(3);
	     qualifierBuffer.putVLong(currentTime);
	     return qualifierBuffer.wrapByteBuffer();
	}
	
	public ByteBuffer encodeValue(List<T> businessLogDataPoints) {
		Buffer valueBuffer = new AutomaticBuffer();
		valueBuffer.putByte(this.codec.getVersion());
		codec.encodeValues(valueBuffer, businessLogDataPoints);
		return valueBuffer.wrapByteBuffer();
	}

}
