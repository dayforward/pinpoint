package com.navercorp.pinpoint.common.server.bo.codec.stat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogV1Bo;

@Component
public class BussinessLogV1Decoder extends BussinessLogDecoder<BussinessLogV1Bo>{

	@Autowired
	public BussinessLogV1Decoder(List<BussinessLogCodec<BussinessLogV1Bo>> bussinessLogV1Codecs) {
		super(bussinessLogV1Codecs);
	}
}
