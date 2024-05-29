package com.yui.traffic_stat.api.authentication;

import com.yui.traffic_stat.core.domain.ServerResponse;
import com.yui.traffic_stat.core.domain.user.LoginCommand;

import com.yui.traffic_stat.core.domain.user.data.TokenData;
import com.yui.traffic_stat.core.service.user.VdUserService;
import com.yui.traffic_stat.infrastructure.entity.VdUser;
import com.yui.traffic_stat.infrastructure.util.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yfy2001
 * @date 2024/4/23 13:54
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Resource
    private VdUserService vdUserService;

    @Resource
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ServerResponse<TokenData> login(LoginCommand command){
        VdUser vdUser=new VdUser();
        if (command.getEmail()!=null){
            vdUser.setEmail(command.getEmail());
        }
        vdUser.setPassword(command.getPassword());
        TokenData tokenData=vdUserService.login(vdUser);
        return ServerResponse.success(tokenData);
    }

    @PostMapping("/logout")
    public ServerResponse<String> logout(HttpServletRequest request){
        String token=request.getHeader("token");
        return ServerResponse.success(vdUserService.logout(token));
    }

    @PostMapping("/loginVerify")
    public ServerResponse<Boolean> loginVerify(HttpServletRequest request){
        String token=request.getHeader("token");
        if(token==null||(!jwtUtils.verifyToken(token))){
            throw new RuntimeException("登录过期！");
        }
        return ServerResponse.success(true);
    }
}
