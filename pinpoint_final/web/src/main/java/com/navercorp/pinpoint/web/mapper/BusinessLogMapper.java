package com.navercorp.pinpoint.web.mapper;

import java.util.ArrayList;
import java.util.List;

import com.navercorp.pinpoint.common.server.bo.stat.BusinessLogV1Bo;
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
import com.navercorp.pinpoint.common.server.bo.codec.stat.BusinessLogV1Decoder;

/**
 * [XINGUANG]Created by Administrator on 2017/6/14.
 */
@Component
public class BusinessLogMapper implements RowMapper<String> {

	@Autowired
    private  BusinessLogHbaseOperationFactory hbaseOperationFactory;

    @Autowired
    private  BusinessLogV1Decoder decoder;

    @Override
    public String mapRow(Result result, int rowNum) throws Exception {
        if (result.isEmpty()) {
            return null;
        }
        List<String> strList = new ArrayList<String>();
        StringBuilder str = new StringBuilder();
        final byte[] rowKey = result.getRow();
        final String agentId = this.hbaseOperationFactory.getAgentId(rowKey);
        final Cell[] rawCells = result.rawCells();
        final List<String> agentIdList = new ArrayList<>(rawCells.length);
        for (Cell cell : result.rawCells()) {
            if (CellUtil.matchingFamily(cell, HBaseTables.BUSINESS_MESSAGEINFO)) {
                Buffer qualifierBuffer = new OffsetFixedBuffer(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                Buffer valueBuffer = new OffsetFixedBuffer(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                long timestamp = this.decoder.getQualifier(qualifierBuffer);
                BusinessLogDecodingContext decodingContext = new BusinessLogDecodingContext();
                decodingContext.setAgentId(agentId);
                strList = this.decoder.decodeValue(valueBuffer, decodingContext);
                for(String s : strList) {
                	str.append(s).append("\r\n");
                }
            }

        }
        return str.toString();

    }

}
