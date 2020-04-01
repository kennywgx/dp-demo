package com.kennywgx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kennywgx.config.mybatisplus.IntegerList;
import com.kennywgx.config.mybatisplus.IntegerListTypeHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;


@TableName("t_role")
@Data
public class RoleDO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("role_name")
    private String roleName;

    @TableField("description")
    private String description;

    @TableField(value = "permission_ids", jdbcType = JdbcType.VARCHAR,
            typeHandler = IntegerListTypeHandler.class)
    private IntegerList permissionIds;

}
