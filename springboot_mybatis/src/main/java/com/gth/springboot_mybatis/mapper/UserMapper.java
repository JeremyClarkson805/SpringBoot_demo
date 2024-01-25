package com.gth.springboot_mybatis.mapper;

import com.gth.springboot_mybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper//在运行时，会自动生成该接口的实现对象（代理对象），并且将该对象交给IOC容器管理
public interface UserMapper {
    @Select("select * from User where id = #{id}")
    public User findById(Integer id);
}
