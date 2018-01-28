package com.navercorp.pinpoint.collector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.navercorp.pinpoint.collector.dao.BusinessLogDao;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogV1Bo;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogBo;

@Service("hbaseBusinessLogService")
public class HbaseBusinessLogService implements BusinessLogService{
	
	private final Logger logger = LoggerFactory.getLogger(HbaseBusinessLogService.class);
	
	@Autowired
	private BusinessLogDao<BusinessLogV1Bo> businessLogDao;

	@Override
	public void save(BusinessLogBo businessLogBo) {
		// TODO Auto-generated method stub
		String agentId = businessLogBo.getAgentId();
		try{
			this.businessLogDao.insert(agentId, businessLogBo.getBusinessLogs());
		} catch(Exception e) {
			logger.warn("Error inserting BusinessLogBo. Caused:{}", e.getMessage(), e);
		}
		
	}

}
