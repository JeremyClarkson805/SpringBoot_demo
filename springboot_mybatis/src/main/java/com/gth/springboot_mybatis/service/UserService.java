package com.gth.springboot_mybatis.service;

import com.gth.springboot_mybatis.pojo.User;

public interface UserService {
    public User findById(Integer id);

}
