package com.navercorp.pinpoint.common.server.bo.codec.stat.v1;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.server.bo.codec.stat.BussinessLogCodec;
import com.navercorp.pinpoint.common.server.bo.codec.stat.BussinessLogDataPointCodec;
import com.navercorp.pinpoint.common.server.bo.codec.stat.header.AgentStatHeaderDecoder;
import com.navercorp.pinpoint.common.server.bo.codec.stat.header.AgentStatHeaderEncoder;
import com.navercorp.pinpoint.common.server.bo.codec.stat.header.BitCountingHeaderDecoder;
import com.navercorp.pinpoint.common.server.bo.codec.stat.header.BitCountingHeaderEncoder;
import com.navercorp.pinpoint.common.server.bo.codec.stat.strategy.StrategyAnalyzer;
import com.navercorp.pinpoint.common.server.bo.codec.stat.strategy.StringEncodingStrategy;
import com.navercorp.pinpoint.common.server.bo.codec.stat.strategy.UnsignedLongEncodingStrategy;
import com.navercorp.pinpoint.common.server.bo.codec.strategy.EncodingStrategy;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BussinessLogDecodingContext;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogV1Bo;

@Component("bussinessLogCodecV1")
public class BussinessLogCodecV1 implements BussinessLogCodec<BussinessLogV1Bo>{
	
	private static final byte VERSION = 1;

	private final BussinessLogDataPointCodec codec;
	
	@Autowired
    public BussinessLogCodecV1(BussinessLogDataPointCodec codec) {
        Assert.notNull(codec, "BussinessLogDataPointCodec must not be null");
        this.codec = codec;
    }

	@Override
	public byte getVersion() {
		// TODO Auto-generated method stub
		return VERSION;
	}

	@Override
	public void encodeValues(Buffer valueBuffer, List<BussinessLogV1Bo> bussinessLogV1Bos) {
		// TODO Auto-generated method stub
		if (CollectionUtils.isEmpty(bussinessLogV1Bos)) {
            throw new IllegalArgumentException("bussinessLogV1Bos must not be empty");
        }
        final int numValues = bussinessLogV1Bos.size();
        valueBuffer.putVInt(numValues);
        
        List<Long> startTimestamps = new ArrayList<Long>(numValues);
        List<Long> timestamps = new ArrayList<Long>(numValues);       
        StringEncodingStrategy.Analyzer.Builder timeAnalyzerBuilder = new StringEncodingStrategy.Analyzer.Builder();
        StringEncodingStrategy.Analyzer.Builder threadNameAnalyzerBuilder = new StringEncodingStrategy.Analyzer.Builder();
        StringEncodingStrategy.Analyzer.Builder logLevelAnalyzerBuilder = new StringEncodingStrategy.Analyzer.Builder();
        StringEncodingStrategy.Analyzer.Builder classNameAnalyzerBuilder = new StringEncodingStrategy.Analyzer.Builder();
        StringEncodingStrategy.Analyzer.Builder transactionIdAnalyzerBuilder = new StringEncodingStrategy.Analyzer.Builder();
        StringEncodingStrategy.Analyzer.Builder spanIdAnalyzerBuilder = new StringEncodingStrategy.Analyzer.Builder();
        StringEncodingStrategy.Analyzer.Builder messageAnalyzerBuilder = new StringEncodingStrategy.Analyzer.Builder();
        for(BussinessLogV1Bo BussinessLogV1Bo : bussinessLogV1Bos) {
        	startTimestamps.add(BussinessLogV1Bo.getStartTimestamp());
        	timestamps.add(BussinessLogV1Bo.getTimestamp());
        	timeAnalyzerBuilder.addValue(BussinessLogV1Bo.getTime());
        	threadNameAnalyzerBuilder.addValue(BussinessLogV1Bo.getThreadName());
        	logLevelAnalyzerBuilder.addValue(BussinessLogV1Bo.getLogLevel());
        	classNameAnalyzerBuilder.addValue(BussinessLogV1Bo.getClassName());
        	transactionIdAnalyzerBuilder.addValue(BussinessLogV1Bo.getTransactionIdANDSpanId());
        	spanIdAnalyzerBuilder.addValue(BussinessLogV1Bo.getSpanId());
        	messageAnalyzerBuilder.addValue(BussinessLogV1Bo.getMessage());
        }

        this.codec.encodeValues(valueBuffer, UnsignedLongEncodingStrategy.REPEAT_COUNT, startTimestamps);
        this.codec.encodeTimestamps(valueBuffer, timestamps);
        this.encodeDataPoint(
        		valueBuffer,
        		timeAnalyzerBuilder.build(),
        		threadNameAnalyzerBuilder.build(),
        		logLevelAnalyzerBuilder.build(),
        		classNameAnalyzerBuilder.build(),
        		transactionIdAnalyzerBuilder.build(),
        		spanIdAnalyzerBuilder.build(),
        		messageAnalyzerBuilder.build());
	}
	
	private void encodeDataPoint(
			Buffer valueBuffer,
			StrategyAnalyzer<String> timeAnalyzerBuilder,
			StrategyAnalyzer<String> threadNameAnalyzerBuilder,
			StrategyAnalyzer<String> logLevelAnalyzerBuilder,
			StrategyAnalyzer<String> classNameAnalyzerBuilder,
			StrategyAnalyzer<String> transactionIdAnalyzerBuilder,
			StrategyAnalyzer<String> spanIdAnalyzerBuilder,
			StrategyAnalyzer<String> messageAnalyzerBuilder) {
		AgentStatHeaderEncoder headerEncoder = new BitCountingHeaderEncoder();
		headerEncoder.addCode(timeAnalyzerBuilder.getBestStrategy().getCode());
		headerEncoder.addCode(threadNameAnalyzerBuilder.getBestStrategy().getCode());
		headerEncoder.addCode(logLevelAnalyzerBuilder.getBestStrategy().getCode());
		headerEncoder.addCode(classNameAnalyzerBuilder.getBestStrategy().getCode());
		headerEncoder.addCode(transactionIdAnalyzerBuilder.getBestStrategy().getCode());
		headerEncoder.addCode(spanIdAnalyzerBuilder.getBestStrategy().getCode());
		headerEncoder.addCode(messageAnalyzerBuilder.getBestStrategy().getCode());
		final byte[] header = headerEncoder.getHeader();
        valueBuffer.putPrefixedBytes(header);
        
        this.codec.encodeValues(valueBuffer, timeAnalyzerBuilder.getBestStrategy(), timeAnalyzerBuilder.getValues());
        this.codec.encodeValues(valueBuffer, threadNameAnalyzerBuilder.getBestStrategy(), threadNameAnalyzerBuilder.getValues());
        this.codec.encodeValues(valueBuffer, logLevelAnalyzerBuilder.getBestStrategy(), logLevelAnalyzerBuilder.getValues());
        this.codec.encodeValues(valueBuffer, classNameAnalyzerBuilder.getBestStrategy(), classNameAnalyzerBuilder.getValues());
        this.codec.encodeValues(valueBuffer, transactionIdAnalyzerBuilder.getBestStrategy(), transactionIdAnalyzerBuilder.getValues());
        this.codec.encodeValues(valueBuffer, spanIdAnalyzerBuilder.getBestStrategy(), spanIdAnalyzerBuilder.getValues());
        this.codec.encodeValues(valueBuffer, messageAnalyzerBuilder.getBestStrategy(), messageAnalyzerBuilder.getValues());
	}

	@Override
	public List<BussinessLogV1Bo> decodeValues(Buffer valueBuffer, BussinessLogDecodingContext decodingContext) {
		// TODO Auto-generated method stub
		final String agentId = decodingContext.getAgentId();
        final long baseTimestamp = decodingContext.getBaseTimestamp();
        final long timestampDelta = decodingContext.getTimestampDelta();
        final long initialTimestamp = baseTimestamp + timestampDelta;
        
        int numValues = valueBuffer.readVInt();
        List<Long> startTimestamps = this.codec.decodeValues(valueBuffer, UnsignedLongEncodingStrategy.REPEAT_COUNT, numValues);
        List<Long> timestamps = this.codec.decodeTimestamps(initialTimestamp, valueBuffer, numValues);
        
        final byte[] header = valueBuffer.readPrefixedBytes();
        AgentStatHeaderDecoder headerDecoder = new BitCountingHeaderDecoder(header);
        EncodingStrategy<String> timeEncodingStrategy = StringEncodingStrategy.getFromCode(headerDecoder.getCode());
        EncodingStrategy<String> threadNameEncodingStrategy = StringEncodingStrategy.getFromCode(headerDecoder.getCode());
        EncodingStrategy<String> logLevelEncodingStrategy = StringEncodingStrategy.getFromCode(headerDecoder.getCode());
        EncodingStrategy<String> classNameEncodingStrategy = StringEncodingStrategy.getFromCode(headerDecoder.getCode());
        EncodingStrategy<String> transactionIdEncodingStrategy = StringEncodingStrategy.getFromCode(headerDecoder.getCode());
        EncodingStrategy<String> spanIdEncodingStrategy = StringEncodingStrategy.getFromCode(headerDecoder.getCode());
        EncodingStrategy<String> messageEncodingStrategy = StringEncodingStrategy.getFromCode(headerDecoder.getCode());
        
        List<String> times = this.codec.decodeValues(valueBuffer, timeEncodingStrategy, numValues);
        List<String> threadNames = this.codec.decodeValues(valueBuffer, threadNameEncodingStrategy, numValues);
        List<String> logLevels = this.codec.decodeValues(valueBuffer, logLevelEncodingStrategy, numValues);
        List<String> classNames = this.codec.decodeValues(valueBuffer, classNameEncodingStrategy, numValues);
        List<String> transactionIds = this.codec.decodeValues(valueBuffer, transactionIdEncodingStrategy, numValues);
        List<String> spanIds = this.codec.decodeValues(valueBuffer, spanIdEncodingStrategy, numValues);
        List<String> messages = this.codec.decodeValues(valueBuffer, messageEncodingStrategy, numValues);
        
        List<BussinessLogV1Bo> bussinessLogV1Bos = new ArrayList<BussinessLogV1Bo>(numValues);
        for(int i = 0; i < numValues; ++i) {
        	BussinessLogV1Bo bussinessLogV1Bo = new BussinessLogV1Bo();
        	bussinessLogV1Bo.setAgentId(agentId);
        	bussinessLogV1Bo.setStartTimestamp(startTimestamps.get(i));
        	bussinessLogV1Bo.setTimestamp(timestamps.get(i));
        	bussinessLogV1Bo.setTime(times.get(i));
        	bussinessLogV1Bo.setThreadName(threadNames.get(i));
        	bussinessLogV1Bo.setLogLevel(logLevels.get(i));
        	bussinessLogV1Bo.setClassName(classNames.get(i));
        	bussinessLogV1Bo.setTransactionId(transactionIds.get(i));
        	bussinessLogV1Bo.setSpanId(spanIds.get(i));
        	bussinessLogV1Bo.setMessage(messages.get(i));
        }
		return bussinessLogV1Bos;
	}

}
