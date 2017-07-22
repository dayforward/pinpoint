package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.server.bo.codec.stat.BussinessLogEncoder;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogV1Bo;

@Component
public class BussinessLogV1Serializer extends BussinessLogSerializer<BussinessLogV1Bo>{

	protected BussinessLogV1Serializer(BussinessLogEncoder encoder) {
		super(encoder);
		// TODO Auto-generated constructor stub
	}

	

}
