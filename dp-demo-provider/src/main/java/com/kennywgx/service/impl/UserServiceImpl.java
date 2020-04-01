package com.kennywgx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kennywgx.domain.UserDO;
import com.kennywgx.exception.DemoException;
import com.kennywgx.mapper.UserMapper;
import com.kennywgx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Override
    public List<UserDO> listAll() {
        return this.baseMapper.selectAll();
    }

    @Override
    public UserDO getByUsername(String username) {
        if (StringUtils.isEmpty(username))
            return null;
        LambdaQueryWrapper<UserDO> wrapper =
                Wrappers.<UserDO>lambdaQuery().eq(UserDO::getUsername, username);
        UserDO userDo = this.baseMapper.selectOne(wrapper);
        return userDo;
    }

    @Override
    public boolean save(UserDO userDo) {
        if (null == userDo)
            throw new NullPointerException();

        String username = userDo.getUsername();
        String password = userDo.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            throw new DemoException("用户名或密码不能为空");

        // 加密password
        userDo.setPassword(EncryptUtils.md5Base64(password));

        System.out.println(userDo);
        return super.save(userDo);
    }
}
