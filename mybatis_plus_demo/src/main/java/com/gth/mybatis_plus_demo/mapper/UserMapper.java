package com.gth.mybatis_plus_demo.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gth.mybatis_plus_demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
