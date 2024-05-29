package com.yui.traffic_stat.core.domain.user;

import lombok.Data;

/**
 * @Author yfy2001
 * @date 2024/4/23 11:09
 */
@Data
public class LoginCommand {
    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;
}
