package com.yui.traffic_stat.api.authentication;

import com.yui.traffic_stat.core.domain.ServerResponse;
import com.yui.traffic_stat.core.service.CaptchaService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yfy2001
 * @date 2024/4/23 10:58
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @Resource
    private CaptchaService captchaService;

    @GetMapping("/getCaptcha")
    public ServerResponse<String> getCaptcha(String receiver){
        System.out.println(receiver);
        return ServerResponse.success(captchaService.sendCaptcha(receiver));
    }
}