package com.navercorp.pinpoint.collector.dao;

import java.util.List;

import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogDataPoint;

public interface BusinessLogDao<T extends BusinessLogDataPoint> {

	void insert(String agentId, List<T> param);
}
