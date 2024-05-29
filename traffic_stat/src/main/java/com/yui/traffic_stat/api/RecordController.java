package com.yui.traffic_stat.api;

import com.yui.traffic_stat.core.domain.ServerResponse;
import com.yui.traffic_stat.core.domain.record.CreateRecordCommand;
import com.yui.traffic_stat.core.domain.record.RecordQuery;
import com.yui.traffic_stat.core.domain.record.data.RecordNumberStatDataInTimeInterval;
import com.yui.traffic_stat.core.service.record.VdRecordService;
import com.yui.traffic_stat.infrastructure.entity.VdRecord;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author yfy2001
 * @date 2024/4/23 10:03
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Resource
    private VdRecordService vdRecordService;

    @PostMapping("/create")
    public ServerResponse<Integer> createRecord(@RequestBody CreateRecordCommand command){
        VdRecord vdRecord=new VdRecord();
        BeanUtils.copyProperties(command,vdRecord);
        return ServerResponse.success(vdRecordService.addNewRecord(vdRecord));
    }

    @GetMapping ("/getStatData")
    public ServerResponse<RecordNumberStatDataInTimeInterval>
    getStatData(RecordQuery query){
        return ServerResponse.success(vdRecordService.queryStatData(query));
    }

    @GetMapping ("/getStatDataEveryDay")
    @Cacheable(cacheNames = {"getStatDataEveryDay"},key = "#query")
    public ServerResponse<Map<String,RecordNumberStatDataInTimeInterval>>
    getStatDataEveryDay(RecordQuery query){
        return ServerResponse.success(vdRecordService.queryStatEveryDay(query));
    }

}
