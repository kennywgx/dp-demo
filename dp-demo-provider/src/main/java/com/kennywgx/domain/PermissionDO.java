package com.kennywgx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("t_permission")
@Data
public class PermissionDO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;

    @TableField("description")
    private String description;

}
