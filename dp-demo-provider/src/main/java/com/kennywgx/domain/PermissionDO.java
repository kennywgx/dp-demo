package com.kennywgx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kennywgx.config.mybatisplus.IntegerList;
import com.kennywgx.config.mybatisplus.IntegerListTypeHandler;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;


@TableName("t_permission")
@Data
public class PermissionDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("parent_id")
    private Long parentId;

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;

    @TableField("description")
    private String description;

}
