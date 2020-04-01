package com.kennywgx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kennywgx.domain.PermissionDO;

import java.util.Collection;
import java.util.List;

public interface PermissionService extends IService<PermissionDO> {

    List<PermissionDO> findByIdList(Collection<Integer> idList);

}
