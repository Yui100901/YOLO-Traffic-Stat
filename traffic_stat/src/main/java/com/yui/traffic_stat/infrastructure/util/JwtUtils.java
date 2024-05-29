package com.yui.traffic_stat.infrastructure.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.yui.traffic_stat.infrastructure.entity.VdUser;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @Author yfy2001
 * @date 2023/4/8 10:27
 */
@Component
public class JwtUtils {
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 过期时间
     * 1小时
     */
    private static final Long EXPIRE_TIME=24*60*60*1000L;

    /**
     * 生成token过期时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }

    /**
     * 加密密钥
     */
    private static final String KEY = "SecretFlower";

    public String generateToken(VdUser vdUser){
        Algorithm algorithm=Algorithm.HMAC256(KEY);//设置加密算法
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("type", "JWT");
        String token=JWT.create()
                .withHeader(header)//头部
                .withIssuer("VdServer")//签发者
                .withAudience("User") //接受者
                .withIssuedAt(Instant.now())//签发时间
                .withExpiresAt(generateExpirationDate())//到期时间
                .withClaim("id",vdUser.getId())//向token中加入id
                .withClaim("username",vdUser.getEmail())//向token中加入用户名
                .sign(algorithm);
        redisTemplate.opsForValue().set(token,"在线",24, TimeUnit.HOURS);
        return token;
    }


    public String refreshToken(String token){
        return generateToken(null);
    }

    public boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(KEY)).build();
            verifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException("验证失败！");
        }
        return true;
    }


    /**
     * 从token获取id
     * @param token
     * @return
     */
    public Long getUserId(String token){
        //验证token
        verifyToken(token);
        try {
            return JWT.decode(token)
                    .getClaim("id").asLong();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 从token获取用户名
     * @param token
     * @return
     */
    public String getUsername(String token){
        //验证token
        verifyToken(token);
        try {
            return JWT.decode(token)
                    .getClaim("username").asString();
        }catch (Exception e){
            return null;
        }
    }



}


