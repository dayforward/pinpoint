package com.navercorp.pinpoint.common.server.bo.codec.stat;

import java.util.List;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BussinessLogDecodingContext;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogDataPoint;

public class BussinessLogDecoder<T extends BussinessLogDataPoint> {

	private final List<BussinessLogCodec<T>> codecs;
	
	public BussinessLogDecoder(List<BussinessLogCodec<T>> codecs) {
		this.codecs = codecs;
	}
	
	public Long getQualifier(Buffer qualifierBuffer) {
		return qualifierBuffer.readVLong();
	}
	
	public List<T> decodeValue(Buffer valueBuffer, BussinessLogDecodingContext decodingContext) {
		 byte version = valueBuffer.readByte();
	        for (BussinessLogCodec<T> codec : this.codecs) {
	            if (version == codec.getVersion()) {
	                return codec.decodeValues(valueBuffer, decodingContext);
	            }
	        }
	        throw new IllegalArgumentException("Unknown version : " + version);
	}
}
