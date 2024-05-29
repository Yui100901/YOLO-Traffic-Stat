package com.yui.traffic_stat.core.service.record;

import com.yui.traffic_stat.core.domain.record.RecordQuery;
import com.yui.traffic_stat.core.domain.record.data.RecordData;
import com.yui.traffic_stat.core.domain.record.data.RecordNumberStatDataInTimeInterval;
import com.yui.traffic_stat.infrastructure.entity.VdRecord;

import java.util.List;
import java.util.Map;

/**
 * @Author yfy2001
 * @date 2024/4/23 11:12
 */
public interface VdRecordService {

    Integer addNewRecord(VdRecord vdRecord);

    /**
     * 查询记录列表
     * @param query
     * @return
     */
    List<RecordData> queryRecordDataList(RecordQuery query);

    RecordNumberStatDataInTimeInterval queryStatData(RecordQuery query);

    Map<String,RecordNumberStatDataInTimeInterval> queryStatEveryDay(RecordQuery query);
}
