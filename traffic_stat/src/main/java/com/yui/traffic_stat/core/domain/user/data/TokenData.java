package com.yui.traffic_stat.core.domain.user.data;

import lombok.Data;

/**
 * @Author yfy2001
 * @date 2024/4/23 14:04
 */
@Data
public class TokenData {
    private Long VdUserId;
    private String email;
    private String nickname;
    private String token;
}