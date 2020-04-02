package com.kennywgx.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.deepexi.pojo.converter.utils.ConverterUtils;
import com.github.taccisum.shiro.web.autoconfigure.stateless.support.jwt.JWTManager;
import com.kennywgx.config.web.DemoResponse;
import com.kennywgx.domain.UserDO;
import com.kennywgx.domain.query.PaginationRequest;
import com.kennywgx.domain.vo.UserVO;
import com.kennywgx.service.RoleService;
import com.kennywgx.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查看所有用户")
    @GetMapping("/")
    @RequiresPermissions("system:user:manage")
    public DemoResponse<List<UserVO>> listAll() {
        List<UserDO> userDOList = userService.listAll();
        return DemoResponse.success(ConverterUtils.convertAll(userDOList, UserVO.class));
    }

    /**
     * 查看用户信息
     */
    @ApiOperation("查看用户信息")
    @GetMapping("/{id}")
    @RequiresPermissions("system:user:view")
    public DemoResponse<UserVO> get(@PathVariable Integer id) {
        UserDO userDO = userService.getById(id);
        return DemoResponse.success(ConverterUtils.convert(userDO, UserVO.class));
    }

    /**
     * 修改用户角色
     */
    @ApiOperation("修改用户角色")
    @PostMapping("/{userId}/role")
    @RequiresRoles("MANAGER")
    public DemoResponse<UserDO> updateRole(@PathVariable Integer userId,
                                           @RequestBody @Valid UserUpdateRoleForm form) {
        UserDO userDO = userService.updateRoles(userId, form.getRoles());
        return DemoResponse.success(userDO, "更新角色成功");
    }

    @Data
    public static class UserUpdateRoleForm {
        @NotEmpty
        private List<String> roles;
    }
}
