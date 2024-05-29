package com.yui.traffic_stat.api;

import com.yui.traffic_stat.core.domain.ServerResponse;
import com.yui.traffic_stat.core.domain.device.DeviceQuery;
import com.yui.traffic_stat.core.domain.device.data.DeviceData;
import com.yui.traffic_stat.core.domain.record.RecordQuery;
import com.yui.traffic_stat.core.domain.record.data.RecordNumberStatDataInTimeInterval;
import com.yui.traffic_stat.core.service.device.VdDeviceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author yfy2001
 * @date 2024/4/28 10:53
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Resource
    private VdDeviceService vdDeviceService;

    @GetMapping("/getDeviceList")
    public ServerResponse<List<DeviceData>>
    getDeviceList(DeviceQuery query){
        return ServerResponse.success(vdDeviceService.queryDeviceDataList(query));
    }
}
