package com.yui.traffic_stat.api;

import com.yui.traffic_stat.core.domain.ServerResponse;
import com.yui.traffic_stat.core.domain.user.LoginCommand;
import com.yui.traffic_stat.core.domain.user.RegisterCommand;
import com.yui.traffic_stat.core.domain.user.RetrieveCommand;
import com.yui.traffic_stat.core.domain.user.data.TokenData;
import com.yui.traffic_stat.core.service.CaptchaService;
import com.yui.traffic_stat.core.service.user.VdUserService;
import com.yui.traffic_stat.infrastructure.entity.VdUser;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yfy2001
 * @date 2024/4/23 10:31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private CaptchaService captchaService;

    @Resource
    private VdUserService vdUserService;

    @PostMapping("/register")
    public ServerResponse<Integer> register(RegisterCommand command){
        if (captchaService.verifyCaptcha(command.getEmail(), command.getCaptcha())){
            VdUser vdUser=new VdUser();
            BeanUtils.copyProperties(command,vdUser);
            return ServerResponse.success(vdUserService.register(vdUser));
        }else {
            throw new RuntimeException("注册失败！");
        }
    }

    /**
     * 修改密码
     * @param command
     * @return
     */
    @PostMapping("/retrieve")
    public ServerResponse<Integer> retrieve(RetrieveCommand command){
        if (captchaService.verifyCaptcha(command.getEmail(), command.getCaptcha())){
            VdUser vdUser=new VdUser();
            BeanUtils.copyProperties(command,vdUser);
            return ServerResponse.success(vdUserService.retrieve(vdUser));
        }else {
            throw new RuntimeException("找回密码失败！");
        }
    }

}
