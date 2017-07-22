package com.navercorp.pinpoint.collector.dao;

import java.util.List;

import com.navercorp.pinpoint.common.server.bo.stat.BussinessLogDataPoint;

public interface BussinessLogDao<T extends BussinessLogDataPoint> {

	void insert(String agentId, List<T> param);
}
