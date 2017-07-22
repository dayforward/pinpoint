package com.navercorp.pinpoint.collector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navercorp.pinpoint.collector.dao.BussinessLogDao;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogV1Bo;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogBo;

@Service("hbaseBussinessLogService")
public class HbaseBussinessLogService implements BussinessLogService{
	
	private final Logger logger = LoggerFactory.getLogger(HbaseBussinessLogService.class);
	
	@Autowired
	private BussinessLogDao<BussinessLogV1Bo> bussinessLogDao;

	@Override
	public void save(BussinessLogBo bussinessLogBo) {
		// TODO Auto-generated method stub
		String agentId = bussinessLogBo.getAgentId();
		try{
			this.bussinessLogDao.insert(agentId, bussinessLogBo.getBussinessLogs());
		} catch(Exception e) {
			logger.warn("Error inserting BussinessLogBo. Caused:{}", e.getMessage(), e);
		}
		
	}

}
