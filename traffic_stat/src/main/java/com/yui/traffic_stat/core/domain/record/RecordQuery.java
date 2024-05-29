package com.yui.traffic_stat.core.domain.record;

import lombok.Data;

/**
 * @Author yfy2001
 * @date 2024/4/25 9:10
 */
@Data
public class RecordQuery {
    /**
     * 额外条件
     * 时间间隔
     */
    private Long timeInterval;

    /**
     * 检测设备的id
     */
    private Long deviceId;

    /**
     * 车辆类型（与检测系统类型对应）
     */
    private Integer vehicleType;

    /**
     * 车辆in数量
     */
    private Integer vehicleInNumber;

    /**
     * 车辆out数量
     */
    private Integer vehicleOutNumber;
}
