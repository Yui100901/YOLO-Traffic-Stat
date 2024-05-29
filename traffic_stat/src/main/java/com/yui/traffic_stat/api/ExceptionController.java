package com.yui.traffic_stat.api;

import com.yui.traffic_stat.core.domain.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author yfy2001
 * @date 2024/4/23 11:01
 */
@RestControllerAdvice
public class ExceptionController {
    private static final Logger log= LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServerResponse<String> exception(Exception e){
        log.error("全局异常：e={}",e.getMessage(),e);
        return ServerResponse.fail(e.getMessage());
    }

}
