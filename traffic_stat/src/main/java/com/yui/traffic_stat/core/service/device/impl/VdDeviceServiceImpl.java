package com.yui.traffic_stat.core.service.device.impl;

import com.yui.traffic_stat.core.domain.device.DeviceQuery;
import com.yui.traffic_stat.core.domain.device.data.DeviceData;
import com.yui.traffic_stat.core.service.device.VdDeviceService;
import com.yui.traffic_stat.infrastructure.entity.VdDevice;
import com.yui.traffic_stat.infrastructure.repository.device.VdDeviceRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author yfy2001
 * @date 2024/4/28 10:58
 */
@Service
public class VdDeviceServiceImpl implements VdDeviceService {

    @Resource
    private VdDeviceRepository vdDeviceRepository;
    @Override
    public List<DeviceData> queryDeviceDataList(DeviceQuery query) {
        return vdDeviceRepository.queryDeviceList(query);
    }
}
