package com.yui.traffic_stat.infrastructure.repository.device;

import com.yui.traffic_stat.core.domain.device.DeviceQuery;
import com.yui.traffic_stat.core.domain.device.data.DeviceData;

import java.util.List;

/**
 * @Author yfy2001
 * @date 2024/4/28 10:59
 */
public interface VdDeviceRepository {
    List<DeviceData> queryDeviceList(DeviceQuery query);
}
