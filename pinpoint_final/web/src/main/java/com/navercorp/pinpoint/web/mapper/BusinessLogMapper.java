package com.navercorp.pinpoint.web.mapper;

import com.navercorp.pinpoint.common.buffer.Buffer;
import com.navercorp.pinpoint.common.buffer.OffsetFixedBuffer;
import com.navercorp.pinpoint.common.hbase.RowMapper;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

import com.navercorp.pinpoint.common.hbase.RowMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
@Component
public class BusinessLogMapper implements RowMapper<String> {

    @Override
    public String mapRow(Result result, int rowNum) throws Exception {
        if (result.isEmpty()) {
            return "";
        }
        final Cell[] rawCells = result.rawCells();
        final List<String> agentIdList = new ArrayList<>(rawCells.length);
        StringBuilder sb = new StringBuilder();
        for (Cell cell : rawCells) {
            // final String agentId = Bytes.toString(CellUtil.cloneQualifier(cell));
        //    final String agentId = Bytes.toString(CellUtil.cloneValue(cell));
           // final Buffer valueBuffer = new OffsetFixedBuffer(CellUtil.cloneValue(cell));
            String agentId = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            sb.append(agentId);
        }
        System.out.println("result = " + result);
        System.out.println("sb = " + sb.toString());
        return sb.toString();
    }
}

