package com.kennywgx.converter;

import com.kennywgx.config.mybatisplus.IntegerList;
import com.kennywgx.domain.RoleDO;
import com.kennywgx.domain.UserDO;
import com.kennywgx.domain.vo.UserVO;
import com.kennywgx.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDO2UserVOConverter implements Converter<UserDO, UserVO> {

    @Autowired
    private RoleService roleService;

    @SuppressWarnings("NullableProblems")
    @Override
    public UserVO convert(UserDO userDO) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);

        // 转换roleId => roleName
        IntegerList roleIds = userDO.getRoleIds();
        if (CollectionUtils.isEmpty(roleIds)) {
            userVO.setRoles(Collections.emptyList());
        } else {
            List<String> roleNames = roleService.findByIdList(roleIds)
                    .stream()
                    .map(RoleDO::getRoleName)
                    .collect(Collectors.toList());
            userVO.setRoles(roleNames);
        }
        return userVO;
    }
}
