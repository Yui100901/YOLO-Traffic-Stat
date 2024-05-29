package com.yui.traffic_stat.infrastructure.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Author yfy2001
 * @date 2024/4/23 10:43
 */
@Component
public class CaptchaCodeUtils {

    public String generateCode(int n){  // n的作用就是指定验证码的长度
        Random random = new Random(); //创建一个生成随机数的对象
        StringBuffer sb =new StringBuffer(); // StringBuffer是一种可变的字符串，长度、内容都可变
        for(int i = 0;i < n;i ++){    //一个for循环
            int nextInt = random.nextInt(10);//生成一个大于0小于10的随机数
            sb.append(nextInt);//调用append方法将我们生成的随机数存放到StringBuffer对象sb中
        }
        System.out.println(sb);
        return sb.toString();      //将sb以String的形式输出
    }

}

