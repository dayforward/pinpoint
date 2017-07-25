package com.navercorp.pinpoint.common.server.bo.codec.stat;

import java.util.List;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BusinessLogDecodingContext;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogDataPoint;

public interface BusinessLogCodec<T extends BusinessLogDataPoint> {

	byte getVersion();

    void encodeValues(Buffer valueBuffer, List<T> businessLogDataPoints);

    List<String> decodeValues(Buffer valueBuffer, BusinessLogDecodingContext decodingContext);
}
