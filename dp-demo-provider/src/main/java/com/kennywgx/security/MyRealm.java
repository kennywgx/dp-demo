package com.kennywgx.security;

import com.alibaba.fastjson.JSON;
import com.kennywgx.config.mybatisplus.IntegerList;
import com.kennywgx.domain.PermissionDO;
import com.kennywgx.domain.RoleDO;
import com.kennywgx.domain.UserDO;
import com.kennywgx.service.PermissionService;
import com.kennywgx.service.RoleService;
import com.kennywgx.service.UserService;
import com.kennywgx.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    /*@Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }*/

    /**
     * 从数据库中获取角色和权限授权用户
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.getUsername(principals.toString());
        UserDO user = userService.getByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 设置用户角色
        List<RoleDO> roleList = roleService.findByIdList(user.getRoleIds());
        Set<String> RoleStringSet = roleList.stream().map(RoleDO::getRoleName).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(RoleStringSet);

        // 获取多角色的所有用户权限
        List<Integer> permissionIdList = new LinkedList<>();
        roleList.forEach(role -> permissionIdList.addAll(role.getPermissionIds()));
        List<PermissionDO> permissionList = permissionService.findByIdList(permissionIdList);
        // 设置用户权限
        Set<String> permissionStringSet = permissionList.stream().map(PermissionDO::getCode).collect(Collectors.toSet());
        simpleAuthorizationInfo.addStringPermissions(permissionStringSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 用户身份认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String credentials = (String) token.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(credentials);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }

        UserDO user = userService.getByUsername(username);
        if (null == user || !JWTUtil.verify(credentials, username, user.getPassword()) ) {
            throw new AuthenticationException("用户名或密码错误");
        }

        return new SimpleAuthenticationInfo(token, token, getName());
    }

    public static void main(String[] args) {
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "abc", true);
        System.out.println(JSON.toJSONString(token));
    }

    /**
     * 清除用户权限缓存
     *
     */
    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }
}
