package com.yui.traffic_stat.core.service;

import com.yui.traffic_stat.infrastructure.util.CaptchaCodeUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author yfy2001
 * @date 2024/4/23 10:42
 */
@Service
public class CaptchaService {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Value("${spring.mail.username}")
    private String sender;

    @Resource
    private CaptchaCodeUtils captchaCodeUtils;

    public String sendCaptcha(String receiver){
        String captcha= captchaCodeUtils.generateCode(6);
        SimpleMailMessage message = new SimpleMailMessage();
        //发送方
        message.setFrom(sender);
        //接收方
        message.setTo(receiver);
        //邮件标题
        message.setSubject("欢迎来到车流量统计系统平台");
        //邮件内容
        message.setText("您的验证码是："+captcha+"。\n验证码10分钟内有效。\n若不是本人操作请无视该邮件。");
        javaMailSender.send(message);
        redisTemplate.opsForValue().set(receiver,captcha,10, TimeUnit.MINUTES);
        return "发送成功";
    }

    public boolean verifyCaptcha(String email,String captcha){
        String correctCaptcha=redisTemplate.opsForValue().get(email);
//        System.out.println("正确验证码"+correctCaptcha);
//        System.out.println("输入的验证码"+captcha);
        if (correctCaptcha==null){
            throw new RuntimeException("验证码错误！");
        }
        return correctCaptcha.equals(captcha);
    }
}
