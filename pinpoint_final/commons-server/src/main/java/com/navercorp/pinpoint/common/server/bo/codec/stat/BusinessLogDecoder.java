package com.navercorp.pinpoint.common.server.bo.codec.stat;

import java.util.List;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BusinessLogDecodingContext;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogDataPoint;

public class BusinessLogDecoder<T extends BusinessLogDataPoint> {

	private final List<BusinessLogCodec<T>> codecs;
	
	public BusinessLogDecoder(List<BusinessLogCodec<T>> codecs) {
		this.codecs = codecs;
	}
	
	public Long getQualifier(Buffer qualifierBuffer) {
		return qualifierBuffer.readVLong();
	}
	
	public List<String> decodeValue(Buffer valueBuffer, BusinessLogDecodingContext decodingContext) {
		 byte version = valueBuffer.readByte();
	        for (BusinessLogCodec<T> codec : this.codecs) {
	            if (version == codec.getVersion()) {
	                return codec.decodeValues(valueBuffer, decodingContext);
	            }
	        }
	        throw new IllegalArgumentException("Unknown version : " + version);
	}
}
