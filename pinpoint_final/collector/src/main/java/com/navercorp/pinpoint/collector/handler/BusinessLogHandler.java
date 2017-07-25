package com.navercorp.pinpoint.collector.handler;

import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navercorp.pinpoint.collector.dao.BusinessLogDao;
import com.navercorp.pinpoint.collector.mapper.thrift.stat.BusinessLogBatchMapper;
import com.navercorp.pinpoint.collector.service.BusinessLogService;
import com.navercorp.pinpoint.collector.service.HbaseBusinessLogService;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogBo;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogV1Bo;
import com.navercorp.pinpoint.thrift.dto.TBusinessLogBatch;

@Service("businessLogHandler")
public class BusinessLogHandler implements Handler{
	
	private final Logger logger = LoggerFactory.getLogger(BusinessLogHandler.class);
	
	@Autowired
    private BusinessLogBatchMapper businessLogBatchMapper;
	
	/*@Autowired
    private BusinessLogService businessLogService;*/
	
	@Autowired
	private BusinessLogDao<BusinessLogV1Bo> businessLogDao;

	@Override
	public void handle(TBase<?, ?> tbase) {
		// TODO Auto-generated method stub
		if(tbase instanceof TBusinessLogBatch) {
			TBusinessLogBatch tBusinessLogBatch = (TBusinessLogBatch) tbase;
        	this.handleBusinessLogBatch(tBusinessLogBatch);
		}else {
            throw new IllegalArgumentException("unexpected tbase:" + tbase + " expected:" + TBusinessLogBatch.class.getName());
        }
	}
	
    private void handleBusinessLogBatch(TBusinessLogBatch tBusinessLogBatch) {
    	if (logger.isDebugEnabled()) {
            logger.debug("Received TBusinessLogBatch={}", tBusinessLogBatch);
        }
    	
    	BusinessLogBo businessLogBo = this.businessLogBatchMapper.map(tBusinessLogBatch);
    	
    	if(businessLogBo == null) {
    		return;
    	}
   // 	businessLogService.save(businessLogBo);
    	businessLogDao.insert(businessLogBo.getAgentId(), businessLogBo.getBusinessLogs());
    }

}
