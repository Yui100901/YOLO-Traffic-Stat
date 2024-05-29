package com.yui.traffic_stat.core.domain.user;

import lombok.Data;

/**
 * @Author yfy2001
 * @date 2024/4/23 10:38
 */
@Data
public class RegisterCommand {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String captcha;
    /**
     * 昵称
     */
    private String nickname;
}
