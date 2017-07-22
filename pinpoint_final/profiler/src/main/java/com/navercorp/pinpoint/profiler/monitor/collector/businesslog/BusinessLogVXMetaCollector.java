package com.navercorp.pinpoint.profiler.monitor.collector.businesslog;

/**
 * Created by Administrator on 2017/7/21.
 */
public interface BusinessLogVXMetaCollector <T extends TBase<T, ? extends TFieldIdEnum>> {
    list<T> collect();
}
