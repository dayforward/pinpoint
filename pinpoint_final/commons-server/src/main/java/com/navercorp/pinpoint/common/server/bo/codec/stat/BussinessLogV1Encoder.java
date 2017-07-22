package com.navercorp.pinpoint.common.server.bo.codec.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogV1Bo;

@Component
public class BussinessLogV1Encoder extends BussinessLogEncoder<BussinessLogV1Bo>{

	@Autowired
	public BussinessLogV1Encoder(@Qualifier("bussinessLogCodecV1") BussinessLogCodec<BussinessLogV1Bo> codec) {
		super(codec);
		// TODO Auto-generated constructor stub
	}

}
