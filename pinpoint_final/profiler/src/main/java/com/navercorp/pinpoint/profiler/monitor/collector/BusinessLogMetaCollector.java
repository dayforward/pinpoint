package com.navercorp.pinpoint.profiler.monitor.collector;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
public interface BusinessLogMetaCollector <T extends TBase<T, ? extends TFieldIdEnum>>  {
    T collect();
}
