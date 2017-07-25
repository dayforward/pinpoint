package com.navercorp.pinpoint.common.server.bo.codec.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogV1Bo;

@Component
public class BusinessLogV1Encoder extends BusinessLogEncoder<BusinessLogV1Bo>{

	@Autowired
	public BusinessLogV1Encoder(@Qualifier("businessLogCodecV1") BusinessLogCodec<BusinessLogV1Bo> codec) {
		super(codec);
		// TODO Auto-generated constructor stub
	}

}
