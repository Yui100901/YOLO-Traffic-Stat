package com.yui.traffic_stat.infrastructure.repository.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yui.traffic_stat.infrastructure.entity.VdUser;
import com.yui.traffic_stat.infrastructure.mapper.VdUserMapper;
import com.yui.traffic_stat.infrastructure.repository.user.VdUserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Objects;

/**
 * @Author yfy2001
 * @date 2024/4/23 13:01
 */
@Repository
public class VdUserRepositoryImpl implements VdUserRepository {

    @Resource
    private VdUserMapper vdUserMapper;

    @Override
    public Boolean exists(VdUser vdUser) {
        return vdUserMapper.exists(new LambdaQueryWrapper<VdUser>()
                .eq(VdUser::getEmail,vdUser.getEmail()));
    }

    @Override
    public Integer insert(VdUser vdUser) {
        vdUser.setCreator("SYSTEM");
        vdUser.setModifier("SYSTEM");
        vdUser.setCreated(new Date());
        vdUser.setModified(new Date());
        vdUser.setIsDeleted(0);
        return vdUserMapper.insert(vdUser);
    }

    @Override
    public VdUser queryOneByUserDetail(VdUser vdUserDetail) {
        return vdUserMapper.selectOne(new LambdaQueryWrapper<VdUser>()
                .eq(Objects.nonNull(vdUserDetail.getEmail()),VdUser::getEmail, vdUserDetail.getEmail()));
    }

    @Override
    public Integer update(VdUser vdUser) {
        vdUser.setModifier("SYSTEM");
        vdUser.setModified(new Date());
        return vdUserMapper.updateById(vdUser);
    }
}
