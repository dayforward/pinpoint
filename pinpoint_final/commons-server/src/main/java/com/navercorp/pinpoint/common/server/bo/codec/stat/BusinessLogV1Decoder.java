package com.navercorp.pinpoint.common.server.bo.codec.stat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogV1Bo;

@Component
public class BusinessLogV1Decoder extends BusinessLogDecoder<BusinessLogV1Bo>{

	@Autowired
	public BusinessLogV1Decoder(List<BusinessLogCodec<BusinessLogV1Bo>> businessLogV1Codecs) {
		super(businessLogV1Codecs);
	}
}
