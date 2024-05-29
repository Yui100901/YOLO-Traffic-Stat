package com.yui.traffic_stat.core.domain.record;

import lombok.Data;

/**
 * @Author yfy2001
 * @date 2024/4/23 11:04
 */
@Data
public class CreateRecordCommand {
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

    /**
     * 统计的时间粒度（秒）
     */
    private Integer granularity;
}
