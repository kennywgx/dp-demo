package com.kennywgx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kennywgx.domain.PermissionDO;
import com.kennywgx.mapper.PermissionMapper;
import com.kennywgx.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDO> implements PermissionService {

    @Override
    public List<PermissionDO> findByIdList(Collection<Integer> idList) {
        if (CollectionUtils.isEmpty(idList))
            return Collections.emptyList();

        LambdaQueryWrapper<PermissionDO> query = Wrappers.<PermissionDO>lambdaQuery()
                .in(PermissionDO::getId, idList);

        return super.list(query);
    }
}
