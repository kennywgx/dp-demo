package com.kennywgx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kennywgx.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
    default List<UserDO> selectAll() {
        return this.selectList(null);
    }
}
