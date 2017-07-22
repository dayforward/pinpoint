package com.navercorp.pinpoint.collector.handler;

import org.apache.thrift.TBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.navercorp.pinpoint.collector.mapper.thrift.stat.BussinessLogBatchMapper;
import com.navercorp.pinpoint.collector.service.BussinessLogService;
import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogBo;
import com.navercorp.pinpoint.thrift.dto.TBussinessLogBatch;

@Service("bussinessLogHandler")
public class BussinessLogHandler implements Handler{
	
	private final Logger logger = LoggerFactory.getLogger(BussinessLogHandler.class);
	
	@Autowired
    private BussinessLogBatchMapper bussinessLogBatchMapper;
	
	@Autowired
    private BussinessLogService bussinessLogService;

	@Override
	public void handle(TBase<?, ?> tbase) {
		// TODO Auto-generated method stub
		if(tbase instanceof TBussinessLogBatch) {
			TBussinessLogBatch tBussinessLogBatch = (TBussinessLogBatch) tbase;
        	this.handleBussinessLogBatch(tBussinessLogBatch);
		}else {
            throw new IllegalArgumentException("unexpected tbase:" + tbase + " expected:" + TBussinessLogBatch.class.getName());
        }
	}
	
    private void handleBussinessLogBatch(TBussinessLogBatch tBussinessLogBatch) {
    	if (logger.isDebugEnabled()) {
            logger.debug("Received TBussinessLogBatch={}", tBussinessLogBatch);
        }
    	
    	BussinessLogBo bussinessLogBo = this.bussinessLogBatchMapper.map(tBussinessLogBatch);
    	
    	if(bussinessLogBo == null) {
    		return;
    	}
    	bussinessLogService.save(bussinessLogBo);
    	
    }

}
