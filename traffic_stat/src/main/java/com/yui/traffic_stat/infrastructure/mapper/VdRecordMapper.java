package com.yui.traffic_stat.infrastructure.mapper;

import com.yui.traffic_stat.infrastructure.entity.VdRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author yfy2001
* @description 针对表【vd_record】的数据库操作Mapper
* @createDate 2024-04-25 21:13:50
* @Entity com.yui.traffic_stat.infrastructure.entity.VdRecord
*/
public interface VdRecordMapper extends BaseMapper<VdRecord> {

    Integer batchInsert(List<VdRecord> vdRecordList);

    VdRecord selectOneDayData(String day);
}




