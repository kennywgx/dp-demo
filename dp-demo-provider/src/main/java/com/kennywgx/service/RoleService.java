package com.kennywgx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kennywgx.domain.RoleDO;

import java.util.Collection;
import java.util.List;

public interface RoleService extends IService<RoleDO> {

    List<RoleDO> findByIdList(Collection<Integer> idList);

}
