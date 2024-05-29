package com.yui.traffic_stat.core.service;

import com.yui.traffic_stat.infrastructure.entity.VdRecord;
import com.yui.traffic_stat.infrastructure.repository.record.VdRecordRepository;
import com.yui.traffic_stat.infrastructure.util.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author yfy2001
 * @date 2024/4/24 11:06
 */
@Service
public class DataGenService {

    @Resource
    private VdRecordRepository vdRecordRepository;

    @Transactional
    public void GenRecordData(Long deviceId){
        List<VdRecord> vdRecordList=new ArrayList<>();
//        String startDateString = "2010-01-01 00:00:00";
//        Date startDate=dateUtils.formatDate(startDateString);
        Map<Integer, List<Integer>> hourFlowMap=new HashMap<>();
        hourFlowMap.put(0, List.of(10, 0,200,50));
        hourFlowMap.put(1, List.of(10, 0,100,20));
        hourFlowMap.put(2, List.of(20, 0,60,20));
        hourFlowMap.put(3,List.of(10, 0,30,10));
        hourFlowMap.put(4,List.of(10, 0,50,30));
        hourFlowMap.put(5,List.of(20, 0,100,50));
        hourFlowMap.put(6,List.of(50, 30,3000,100));
        hourFlowMap.put(7,List.of(300, 50,7000,300));
        hourFlowMap.put(8,List.of(500, 50,12000,500));
        hourFlowMap.put(9,List.of(300, 50,6000,300));
        hourFlowMap.put(10,List.of(300, 50,5000,100));
        hourFlowMap.put(11,List.of(100, 40,3000,100));
        hourFlowMap.put(12,List.of(100, 40,2000,100));
        hourFlowMap.put(13,List.of(50, 50,3000,100));
        hourFlowMap.put(14,List.of(50, 50,2000,100));
        hourFlowMap.put(15,List.of(50, 50,2000,200));
        hourFlowMap.put(16,List.of(200, 40,6000,100));
        hourFlowMap.put(17,List.of(300, 40,10000,100));
        hourFlowMap.put(18,List.of(600, 30,8000,100));
        hourFlowMap.put(19,List.of(400, 20,6000,300));
        hourFlowMap.put(20,List.of(200, 20,3000,200));
        hourFlowMap.put(21,List.of(50, 10,1000,100));
        hourFlowMap.put(22,List.of(30, 10,500,80));
        hourFlowMap.put(23,List.of(10, 0,300,60));
        Date startDate=new Date();
        int totalHour=3*365*24;
        Random random=new Random();
        for (int i = totalHour; i > 0; i--) {
            Date currentDate=DateUtils.addHour(startDate,-i);
            int currentHour=DateUtils.getHour(currentDate);
            int currentDayOfWeek=DateUtils.getWeek(currentDate);
//            System.out.println("当前时间："+currentHour);
            //四个类型
            for (int j = 0; j < 4; j++) {
                    VdRecord vdRecord=new VdRecord();
                    vdRecord.setCreated(currentDate);
                    vdRecord.setModified(currentDate);
                    vdRecord.setVehicleType(j);
                    vdRecord.setDeviceId(deviceId);
                    int baseFlow=hourFlowMap.get(currentHour).get(j);
                    int vehicleInNumber;
                    int vehicleOutNumber;
                    if (baseFlow==0){
                        vehicleInNumber=0;
                        vehicleOutNumber=0;
                    }else {
                        if (currentDayOfWeek==1||currentDayOfWeek==7){
                            vehicleInNumber= (int) (random.nextInt((int) (baseFlow*0.3))+(int)(baseFlow*0.7)
                                    *random.nextFloat(0.4f)+0.2);
                            vehicleOutNumber= (int) (random.nextInt((int) (baseFlow*0.3))+(int)(baseFlow*0.7)
                                    *random.nextFloat(0.4f)+0.2);
                        }else {
                            vehicleInNumber=random.nextInt((int) (baseFlow*0.3))+(int)(baseFlow*0.7);
                            vehicleOutNumber=random.nextInt((int) (baseFlow*0.3))+(int)(baseFlow*0.7);
                        }
                    }
                    vdRecord.setVehicleInNumber(vehicleInNumber);
                    vdRecord.setVehicleOutNumber(vehicleOutNumber);
                    vdRecord.setGranularity(3600);//粒度1小时
                    vdRecordList.add(vdRecord);
                }

        }
        vdRecordRepository.batchInsert(vdRecordList);
    }


}
