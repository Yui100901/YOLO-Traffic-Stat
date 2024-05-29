package com.yui.traffic_stat.infrastructure.repository.device.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yui.traffic_stat.core.domain.device.DeviceQuery;
import com.yui.traffic_stat.core.domain.device.data.DeviceData;
import com.yui.traffic_stat.infrastructure.entity.VdDevice;
import com.yui.traffic_stat.infrastructure.mapper.VdDeviceMapper;
import com.yui.traffic_stat.infrastructure.mapper.VdRecordMapper;
import com.yui.traffic_stat.infrastructure.repository.device.VdDeviceRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @Author yfy2001
 * @date 2024/4/28 10:59
 */
@Repository
public class VdDeviceRepositoryImpl implements VdDeviceRepository {

    @Resource
    private VdDeviceMapper vdDeviceMapper;

    @Override
    public List<DeviceData> queryDeviceList(DeviceQuery query) {
        List<VdDevice> vdDeviceList=vdDeviceMapper.selectList(new LambdaQueryWrapper<VdDevice>()
                .isNotNull(VdDevice::getId)
                .eq(Objects.nonNull(query.getName()),VdDevice::getName,query.getName())
                .eq(Objects.nonNull(query.getIp()),VdDevice::getIp,query.getIp()));
        return vdDeviceList.stream().map(item->{
            DeviceData data=new DeviceData();
            BeanUtils.copyProperties(item,data);
            return data;
        }).toList();
    }
}
