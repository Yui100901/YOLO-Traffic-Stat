package com.yui.traffic_stat.core.service.user.impl;

import com.yui.traffic_stat.core.domain.user.data.TokenData;
import com.yui.traffic_stat.core.service.user.VdUserService;
import com.yui.traffic_stat.infrastructure.entity.VdUser;
import com.yui.traffic_stat.infrastructure.repository.user.VdUserRepository;
import com.yui.traffic_stat.infrastructure.util.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author yfy2001
 * @date 2024/4/23 11:10
 */
@Service
public class VdUserServiceImpl implements VdUserService {

    @Resource
    private VdUserRepository vdUserRepository;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    RedisTemplate<String,String> redisTemplate;

    @Override
    public Integer register(VdUser vdUser) {
        if (vdUserRepository.exists(vdUser)){
            throw new RuntimeException("该用户已经存在！");
        }
        //encode密码加密存储
        String password=vdUser.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodedPassword= bCryptPasswordEncoder.encode(password);
        vdUser.setPassword(encodedPassword);
        Integer i= vdUserRepository.insert(vdUser);
        if (i.equals(0)){
            throw new RuntimeException("注册失败！");
        }
        return i;
    }

    @Override
    public Integer retrieve(VdUser vdUser) {
        VdUser retrieveUser= vdUserRepository.queryOneByUserDetail(vdUser);
        //encode密码加密存储
        String password=vdUser.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encodedPassword= bCryptPasswordEncoder.encode(password);
        retrieveUser.setPassword(encodedPassword);
        Integer i= vdUserRepository.update(retrieveUser);
        if (i.equals(0)){
            throw new RuntimeException("修改密码失败！");
        }
        return null;
    }

    @Override
    public TokenData login(VdUser vdUser) {
        System.out.println(vdUser.toString());
        VdUser loginUser= vdUserRepository.queryOneByUserDetail(vdUser);
        if (loginUser==null){
            throw new RuntimeException("用户不存在！");
        }
        System.out.println(loginUser.getEmail());
        //密码加密后判断是否相同
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(vdUser.getPassword(),loginUser.getPassword())){
            throw new RuntimeException("用户名或密码错误！");
        }
        //登录成功生成token
        TokenData tokenData=new TokenData();
        tokenData.setVdUserId(loginUser.getId());
        tokenData.setEmail(loginUser.getEmail());
        tokenData.setNickname(loginUser.getNickname());
        tokenData.setToken(jwtUtils.generateToken(loginUser));
        return tokenData;
    }

    @Override
    public String logout(String token) {
        redisTemplate.delete(token);
        return "退出成功";
    }
}
