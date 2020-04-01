package com.kennywgx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kennywgx.config.mybatisplus.IntegerList;
import com.kennywgx.config.mybatisplus.IntegerListTypeHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;


@TableName("t_user")
@Data
public class UserDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField(value = "role_ids", jdbcType = JdbcType.VARCHAR,
            typeHandler = IntegerListTypeHandler.class)
    private IntegerList roleIds;

}
