package com.yui.traffic_stat.core.service.user;

import com.yui.traffic_stat.core.domain.user.data.TokenData;
import com.yui.traffic_stat.infrastructure.entity.VdUser;

/**
 * @Author yfy2001
 * @date 2024/4/23 11:09
 */
public interface VdUserService {

    Integer register(VdUser vdUser);

    Integer retrieve(VdUser vdUser);

    TokenData login(VdUser vdUser);

    String logout(String token);


}
