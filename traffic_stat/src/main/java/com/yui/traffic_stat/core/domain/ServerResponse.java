package com.yui.traffic_stat.core.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author yfy2001
 * @date 2024/4/23 10:11
 */
@Data
public class ServerResponse<T> {
    /**
     * 时间
     */
    private Date time;
    /**
     * 异常码
     */
    private Integer code;
    /**
     * 信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;
    /**
     * 成功返回
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ServerResponse<T> success(T data){
        ServerResponse<T> serverResponse =new ServerResponse<>();
        serverResponse.setTime(new Date());
        serverResponse.setCode(200);
        serverResponse.setMessage("操作成功！");
        serverResponse.setData(data);
        return serverResponse;
    }

    /**
     * 失败返回
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ServerResponse<T> fail(T data){
        ServerResponse<T> serverResponse =new ServerResponse<>();
        serverResponse.setTime(new Date());
        serverResponse.setCode(500);
        serverResponse.setMessage("操作失败!");
        serverResponse.setData(data);
        return serverResponse;
    }
}
