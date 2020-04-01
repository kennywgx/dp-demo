package com.kennywgx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kennywgx.domain.RoleDO;
import com.kennywgx.mapper.RoleMapper;
import com.kennywgx.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {


    @Override
    public List<RoleDO> findByIdList(Collection<Integer> idList) {
        if (CollectionUtils.isEmpty(idList))
            return Collections.emptyList();

        LambdaQueryWrapper<RoleDO> query = Wrappers.<RoleDO>lambdaQuery()
                .in(RoleDO::getId, idList);

        return super.list(query);
    }
}
