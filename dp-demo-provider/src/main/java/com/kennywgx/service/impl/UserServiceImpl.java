package com.kennywgx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.JWTManager;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.Payload;
import com.kennywgx.config.mybatisplus.IntegerList;
import com.kennywgx.domain.PermissionDO;
import com.kennywgx.domain.RoleDO;
import com.kennywgx.domain.UserDO;
import com.kennywgx.exception.DemoException;
import com.kennywgx.mapper.UserMapper;
import com.kennywgx.service.PermissionService;
import com.kennywgx.service.RoleService;
import com.kennywgx.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private JWTManager jwtManager;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * cli初始化项目时填的issuer=one
     */
    public static final String issuer = "one";

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
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public String login(String username, String password) {

        String encryptedPassword = EncryptUtils.md5Base64(password);
        UserDO user = this.getByUsername(username);
        if (null == user || !user.getPassword().equals(encryptedPassword)) {
            throw new AuthenticationException("用户名或密码错误");
        }

        // 获取用户角色
        List<RoleDO> roleList = roleService.findByIdList(user.getRoleIds());
        Set<String> roleStringSet = roleList.stream().map(RoleDO::getRoleName)
                .collect(Collectors.toSet());

        // 获取多角色的所有用户权限
        List<Integer> permissionIdList = new LinkedList<>();
        roleList.forEach(role -> permissionIdList.addAll(role.getPermissionIds()));
        List<PermissionDO> permissionList = permissionService.findByIdList(permissionIdList);
        Set<String> permissionStringSet = permissionList.stream().map(PermissionDO::getCode)
                .collect(Collectors.toSet());

        // 设置JWTToken的payload信息
        Payload payload = new Payload();
        payload.put("userId", user.getId());
        payload.put("tenantId", 1);
        payload.put("username", username);
        payload.put("roles", roleStringSet);
        payload.put("permissions", permissionStringSet);
        return jwtManager.create(issuer, payload);
    }

    @Override
    public UserDO register(String username, String password, String roleName) {
        // 查重名用户
        UserDO userDO = this.getByUsername(username);
        if (null != userDO)
            throw new DemoException("账号名已存在");

        // 查角色
        RoleDO roleDO = roleService.getOne(Wrappers.<RoleDO>lambdaQuery().eq(RoleDO::getRoleName, roleName));
        if (null == roleDO)
            throw new DemoException("角色名错误");

        // 保存账号
        userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setPassword(EncryptUtils.md5Base64(password));
        userDO.setRoleIds(IntegerList.toIntegerList(roleDO.getId()));
        boolean success = save(userDO);
        if (!success)
            throw new DemoException("数据异常");
        return userDO;
    }

}
