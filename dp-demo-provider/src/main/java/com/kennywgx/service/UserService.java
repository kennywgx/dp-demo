package com.kennywgx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kennywgx.domain.UserDO;

import java.util.List;

public interface UserService extends IService<UserDO> {
    List<UserDO> listAll();

    UserDO getByUsername(String username);
}
