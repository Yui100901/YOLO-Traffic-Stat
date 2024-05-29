package com.yui.traffic_stat.core.domain.record.data;

import lombok.Data;

import java.util.Date;

/**
 * @Author yfy2001
 * @date 2024/4/25 9:11
 */
@Data
public class RecordData {
    /**
     * 创建时间
     */
    private Date created;

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
