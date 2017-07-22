package com.navercorp.pinpoint.common.server.bo.codec.stat;

import java.util.List;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BussinessLogDecodingContext;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogDataPoint;

public interface BussinessLogCodec<T extends BussinessLogDataPoint> {

	byte getVersion();

    void encodeValues(Buffer valueBuffer, List<T> bussinessLogDataPoints);

    List<T> decodeValues(Buffer valueBuffer, BussinessLogDecodingContext decodingContext);
}
