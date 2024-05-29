package com.yui.traffic_stat.core.domain.device;

import lombok.Data;

/**
 * @Author yfy2001
 * @date 2024/4/28 10:54
 */
@Data
public class DeviceQuery {

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备ip
     */
    private String ip;

    /**
     * 设备详细地址
     */
    private String address;
}
