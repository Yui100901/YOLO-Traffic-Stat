package com.yui.traffic_stat.core.domain.record.data;

import lombok.Data;

/**
 * 一段时间内的统计数据
 * @Author yfy2001
 * @date 2024/4/25 13:33
 */
@Data
public class RecordNumberStatDataInTimeInterval {

    /**
     * 时间间隔(天)
     */
    private Long timeInterval;

    /**
     * 总流量数
     */
    private Long allTotal;

    private Long inTotal;//入流量总数

    private Long outTotal;//出流量总数

    private Long bikeTotal;//bike类所有流量总数

    private Long busTotal;//bus类所有流量总数

    private Long carTotal;//car类所有流量总数

    private Long truckTotal;//truck类所有流量总数

    private Long bikeInTotal;//bike类入流量总数

    private Long busInTotal;//bus类入流量总数

    private Long carInTotal;//car类入流量总数

    private Long truckInTotal;//truck类入流量总数

    private Long bikeOutTotal;//bike类出流量总数

    private Long busOutTotal;//bus类出流量总数

    private Long carOutTotal;//car类出流量总数

    private Long truckOutTotal;//truck类出流量总数

    /**
     * 平均数据
     */
    private Long allAvg;

    private Long inAvg;

    private Long outAvg;

    private Long bikeAvg;

    private Long busAvg;

    private Long carAvg;

    private Long truckAvg;

    private Long bikeInAvg;

    private Long busInAvg;

    private Long carInAvg;

    private Long truckInAvg;

    private Long bikeOutAvg;

    private Long busOutAvg;

    private Long carOutAvg;

    private Long truckOutAvg;

}
