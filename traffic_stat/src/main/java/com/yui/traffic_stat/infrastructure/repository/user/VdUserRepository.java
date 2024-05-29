package com.yui.traffic_stat.infrastructure.repository.user;

import com.yui.traffic_stat.infrastructure.entity.VdUser;

/**
 * @Author yfy2001
 * @date 2024/4/23 13:01
 */
public interface VdUserRepository {

    Boolean exists(VdUser vdUser);

    Integer insert(VdUser vdUser);

    VdUser queryOneByUserDetail(VdUser vdUser);

    Integer update(VdUser vdUser);
}
