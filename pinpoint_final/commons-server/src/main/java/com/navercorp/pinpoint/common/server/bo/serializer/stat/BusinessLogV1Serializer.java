package com.navercorp.pinpoint.common.server.bo.serializer.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.server.bo.codec.stat.BusinessLogEncoder;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogV1Bo;

/**
 * [XINGUANG]
 */
@Component
public class BusinessLogV1Serializer extends BusinessLogSerializer<BusinessLogV1Bo>{

	@Autowired
	protected BusinessLogV1Serializer(BusinessLogEncoder encoder) {
		super(encoder);
		// TODO Auto-generated constructor stub
	}

	

}
