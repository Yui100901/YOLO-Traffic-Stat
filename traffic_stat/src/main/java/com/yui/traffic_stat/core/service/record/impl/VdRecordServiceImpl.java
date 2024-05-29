package com.yui.traffic_stat.core.service.record.impl;

import com.yui.traffic_stat.core.domain.record.RecordQuery;
import com.yui.traffic_stat.core.domain.record.data.RecordData;
import com.yui.traffic_stat.core.domain.record.data.RecordNumberStatDataInTimeInterval;
import com.yui.traffic_stat.core.service.record.VdRecordService;
import com.yui.traffic_stat.infrastructure.entity.VdRecord;
import com.yui.traffic_stat.infrastructure.repository.record.VdRecordRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author yfy2001
 * @date 2024/4/23 11:12
 */
@Service
public class VdRecordServiceImpl implements VdRecordService {

    @Resource
    private VdRecordRepository vdRecordRepository;

    @Override
    public Integer addNewRecord(VdRecord vdRecord) {
        return vdRecordRepository.insert(vdRecord);
    }

    @Override
    public List<RecordData> queryRecordDataList(RecordQuery query) {
        return vdRecordRepository.queryRecordList(query);
    }

    @Override
    public RecordNumberStatDataInTimeInterval queryStatData(RecordQuery query) {
        List<RecordData> recordDataList=vdRecordRepository.queryRecordList(query);
        return calculateStat(query.getTimeInterval(), recordDataList);
    }

    @Override
    public Map<String,RecordNumberStatDataInTimeInterval> queryStatEveryDay(RecordQuery query) {
        Map<String,List<RecordData>> dataMap=vdRecordRepository.queryRecordListGroupByDay(query);
        return dataMap.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey, v->calculateStat(1L,v.getValue())
        ));
    }

    private RecordNumberStatDataInTimeInterval calculateStat(Long timeInterval,List<RecordData> recordDataList){
        RecordNumberStatDataInTimeInterval statData=new RecordNumberStatDataInTimeInterval();
        statData.setTimeInterval(timeInterval);
        Long allTotal=0L;
        Long bikeInTotal=0L;
        Long busInTotal=0L;
        Long carInTotal=0L;
        Long truckInTotal=0L;
        Long bikeOutTotal=0L;
        Long busOutTotal=0L;
        Long carOutTotal=0L;
        Long truckOutTotal=0L;
        for (RecordData d:recordDataList) {
            allTotal+=d.getVehicleInNumber();
            allTotal+=d.getVehicleOutNumber();
            switch (d.getVehicleType()) {
                case 0  -> {
                    bikeInTotal += d.getVehicleInNumber();
                    bikeOutTotal += d.getVehicleOutNumber();
                }
                case 1  -> {
                    busInTotal+=d.getVehicleInNumber();
                    busOutTotal+=d.getVehicleOutNumber();
                }
                case 2  -> {
                    carInTotal+=d.getVehicleInNumber();
                    carOutTotal+=d.getVehicleOutNumber();
                }
                case 3  -> {
                    truckInTotal+=d.getVehicleInNumber();
                    truckOutTotal+=d.getVehicleOutNumber();
                }
            }
        }
        //总流量和平均流量
        statData.setAllTotal(allTotal);
        Long allAvg=allTotal/statData.getTimeInterval();
        statData.setAllAvg(allAvg);
        //8类总量
        statData.setBikeInTotal(bikeInTotal);
        statData.setBusInTotal(busInTotal);
        statData.setCarInTotal(carInTotal);
        statData.setTruckInTotal(truckInTotal);
        statData.setBikeOutTotal(bikeOutTotal);
        statData.setBusOutTotal(busOutTotal);
        statData.setCarOutTotal(carOutTotal);
        statData.setTruckOutTotal(truckOutTotal);
        //6类总量
        Long inTotal=bikeInTotal+busOutTotal+carInTotal+truckInTotal;
        Long outTotal=bikeOutTotal+busOutTotal+carOutTotal+truckOutTotal;
        Long bikeTotal=bikeInTotal+bikeOutTotal;
        Long busTotal=busOutTotal+busOutTotal;
        Long carTotal=carInTotal+carOutTotal;
        Long truckTotal=truckInTotal+truckOutTotal;
        statData.setInTotal(inTotal);
        statData.setOutTotal(outTotal);
        statData.setBikeTotal(bikeTotal);
        statData.setBusTotal(busTotal);
        statData.setCarTotal(carTotal);
        statData.setTruckTotal(truckTotal);
        //8类平均量
        Long bikeInAvg=bikeInTotal/statData.getTimeInterval();
        Long busInAvg=busInTotal/statData.getTimeInterval();
        Long carInAvg=carInTotal/statData.getTimeInterval();
        Long truckInAvg=truckInTotal/statData.getTimeInterval();
        Long bikeOutAvg=bikeOutTotal/statData.getTimeInterval();
        Long busOutAvg=busOutTotal/statData.getTimeInterval();
        Long carOutAvg=carOutTotal/statData.getTimeInterval();
        Long truckOutAvg=truckOutTotal/statData.getTimeInterval();
        statData.setBikeInAvg(bikeInAvg);
        statData.setBusInAvg(busInAvg);
        statData.setCarInAvg(carInAvg);
        statData.setTruckInAvg(truckInAvg);
        statData.setBikeOutAvg(bikeOutAvg);
        statData.setBusOutAvg(busOutAvg);
        statData.setCarOutAvg(carOutAvg);
        statData.setTruckOutAvg(truckOutAvg);
        //6类平均量
        Long inAvg=inTotal/statData.getTimeInterval();
        Long outAvg=outTotal/statData.getTimeInterval();
        Long bikeAvg=bikeTotal/statData.getTimeInterval();
        Long busAvg=busTotal/statData.getTimeInterval();
        Long carAvg=carTotal/statData.getTimeInterval();
        Long truckAvg=truckTotal/statData.getTimeInterval();
        statData.setInAvg(inAvg);
        statData.setOutAvg(outAvg);
        statData.setBikeAvg(bikeAvg);
        statData.setBusAvg(busAvg);
        statData.setCarAvg(carAvg);
        statData.setTruckAvg(truckAvg);
        return statData;
    }



}
