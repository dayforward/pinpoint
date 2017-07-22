package com.navercorp.pinpoint.common.server.bo.codec.stat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.server.bo.codec.strategy.EncodingStrategy;

@Component
public class BussinessLogDataPointCodec {

	public void encodeTimestamps(Buffer buffer, List<Long> timestamps) {
        long prevTimestamp = timestamps.get(0);
        long prevDelta = 0;
        // skip first timestamp as this value is encoded as the qualifier and delta is meaningless
        for (int i = 1; i < timestamps.size(); ++i) {
            long timestamp = timestamps.get(i);
            long timestampDelta = timestamp - prevTimestamp;
            buffer.putVLong(timestampDelta - prevDelta);
            prevTimestamp = timestamp;
            prevDelta = timestampDelta;
        }
    }

    public List<Long> decodeTimestamps(long initialTimestamp, Buffer buffer, int numValues) {
        List<Long> timestamps = new ArrayList<Long>(numValues);
        timestamps.add(initialTimestamp);
        long prevTimestamp = initialTimestamp;
        long prevDelta = 0;
        // loop through numValues - 1 as the first timestamp is gotten from the qualifier
        for (int i = 0; i < numValues - 1; ++i) {
            long timestampDelta = prevDelta + buffer.readVLong();
            long timestamp = prevTimestamp + timestampDelta;
            timestamps.add(timestamp);
            prevTimestamp = timestamp;
            prevDelta = timestampDelta;
        }
        return timestamps;
    }

    public <T> void encodeValues(Buffer buffer, EncodingStrategy<T> encodingStrategy, List<T> values) {
        encodingStrategy.encodeValues(buffer, values);
    }

    public <T> List<T> decodeValues(Buffer buffer, EncodingStrategy<T> encodingStrategy, int numValues) {
        return encodingStrategy.decodeValues(buffer, numValues);
    }
}
