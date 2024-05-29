package com.yui.traffic_stat.infrastructure;

/**
 * @Author yfy2001
 * @date 2024/4/25 21:35
 */
public enum VehicleType {
    bike(0),
    bus(1),
    car(2),
    truck(3);

    private final Integer code;

    VehicleType(Integer code){
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
}
