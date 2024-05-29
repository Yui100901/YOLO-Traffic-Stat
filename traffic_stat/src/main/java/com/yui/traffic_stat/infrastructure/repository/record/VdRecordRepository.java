package com.yui.traffic_stat.infrastructure.repository.record;

import com.yui.traffic_stat.core.domain.record.RecordQuery;
import com.yui.traffic_stat.core.domain.record.data.RecordData;
import com.yui.traffic_stat.infrastructure.entity.VdRecord;

import java.util.List;
import java.util.Map;

/**
 * @Author yfy2001
 * @date 2024/4/23 13:43
 */
public interface VdRecordRepository {
    Integer insert(VdRecord vdRecord);

    Integer batchInsert(List<VdRecord> vdRecordList);

    /**
     * 根据时间（天数）查最近一段时间的数据
     * @param query
     * @return
     */
    List<RecordData> queryRecordList(RecordQuery query);

    /**
     * 根据时间（天数）查最近一段时间的数据
     * 并按照天数进行分组
     * @param query
     * @return
     */
    Map<String,List<RecordData>> queryRecordListGroupByDay(RecordQuery query);
}
