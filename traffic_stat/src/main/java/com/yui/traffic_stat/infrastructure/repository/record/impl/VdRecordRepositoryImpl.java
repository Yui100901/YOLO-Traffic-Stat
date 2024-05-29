package com.yui.traffic_stat.infrastructure.repository.record.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yui.traffic_stat.core.domain.record.RecordQuery;
import com.yui.traffic_stat.core.domain.record.data.RecordData;
import com.yui.traffic_stat.infrastructure.entity.VdRecord;
import com.yui.traffic_stat.infrastructure.mapper.VdRecordMapper;
import com.yui.traffic_stat.infrastructure.repository.record.VdRecordRepository;
import com.yui.traffic_stat.infrastructure.util.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author yfy2001
 * @date 2024/4/23 13:44
 */
@Repository
public class VdRecordRepositoryImpl implements VdRecordRepository {

    @Resource
    private VdRecordMapper vdRecordMapper;

    @Override
    public Integer insert(VdRecord vdRecord) {
        vdRecord.setCreator("SYSTEM");
        vdRecord.setModifier("SYSTEM");
        vdRecord.setCreated(new Date());
        vdRecord.setModified(new Date());
        vdRecord.setIsDeleted(0);
        return vdRecordMapper.insert(vdRecord);
    }

    @Override
    public Integer batchInsert(List<VdRecord> vdRecordList) {
        for (VdRecord vdRecord:vdRecordList) {
            vdRecord.setCreator("SYSTEM");
            vdRecord.setModifier("SYSTEM");
            vdRecord.setIsDeleted(0);
        }
        return vdRecordMapper.batchInsert(vdRecordList);
    }

    @Override
    public List<RecordData> queryRecordList(RecordQuery query) {
        LocalDateTime start=LocalDateTime.now().minusDays(query.getTimeInterval());
        LambdaQueryWrapper<VdRecord> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.gt(VdRecord::getCreated,start);
        //设备id
        queryWrapper.eq(Objects.nonNull(query.getDeviceId()),VdRecord::getDeviceId,query.getDeviceId());
        //类型
        queryWrapper.eq(Objects.nonNull(query.getVehicleType()),VdRecord::getVehicleType,query.getVehicleType());
        List<VdRecord> vdRecordList=vdRecordMapper.selectList(queryWrapper);
        return vdRecordList.stream().map(item->{
            RecordData data=new RecordData();
            BeanUtils.copyProperties(item,data);
            return data;
        }).toList();
    }

    @Override
    public Map<String, List<RecordData>> queryRecordListGroupByDay(RecordQuery query) {
        LocalDateTime start=LocalDateTime.now().minusDays(query.getTimeInterval());
        LambdaQueryWrapper<VdRecord> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.gt(VdRecord::getCreated,start);
        //设备id
        queryWrapper.eq(Objects.nonNull(query.getDeviceId()),VdRecord::getDeviceId,query.getDeviceId());
        //类型
        queryWrapper.eq(Objects.nonNull(query.getVehicleType()),VdRecord::getVehicleType,query.getVehicleType());
        List<VdRecord> vdRecordList=vdRecordMapper.selectList(queryWrapper);
        List<RecordData> recordDataList= vdRecordList.stream().map(item->{
            RecordData data=new RecordData();
            BeanUtils.copyProperties(item,data);
            return data;
        }).toList();
        return recordDataList.stream().collect(Collectors.groupingBy(item->
                DateUtils.formatDateDay(item.getCreated())
        ));
    }
}
