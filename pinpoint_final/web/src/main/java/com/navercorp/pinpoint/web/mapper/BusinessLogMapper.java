package com.navercorp.pinpoint.web.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.buffer.OffsetFixedBuffer;
import com.navercorp.pinpoint.common.hbase.HBaseTables;
import com.navercorp.pinpoint.common.hbase.RowMapper;
import com.navercorp.pinpoint.common.server.bo.codec.stat.BusinessLogDecoder;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BusinessLogDecodingContext;
import com.navercorp.pinpoint.common.server.bo.serializer.stat.BusinessLogHbaseOperationFactory;
import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogDataPoint;

/**
 * Created by Administrator on 2017/6/14.
 */
@Component
public class BusinessLogMapper implements RowMapper<List<String>> {

	@Autowired
    private  BusinessLogHbaseOperationFactory hbaseOperationFactory;

	@Autowired
    private  BusinessLogDecoder<BusinessLogDataPoint> decoder;

    @Override
    public List<String> mapRow(Result result, int rowNum) throws Exception {
        if (result.isEmpty()) {
            return null;
        }
        List<String> str = new ArrayList<String>();
        final byte[] distributedRowKey = result.getRow();
        final String agentId = this.hbaseOperationFactory.getAgentId(distributedRowKey);
        final Cell[] rawCells = result.rawCells();
        final List<String> agentIdList = new ArrayList<>(rawCells.length);
        for (Cell cell : result.rawCells()) {
            if (CellUtil.matchingFamily(cell, HBaseTables.MESSAGE_INFO)) {
                Buffer qualifierBuffer = new OffsetFixedBuffer(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                Buffer valueBuffer = new OffsetFixedBuffer(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                long timestamp = this.decoder.getQualifier(qualifierBuffer);
                BusinessLogDecodingContext decodingContext = new BusinessLogDecodingContext();
                decodingContext.setAgentId(agentId);
                str = this.decoder.decodeValue(valueBuffer, decodingContext);
            }

        }
        return str;

    }

}
