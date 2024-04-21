package com.gth.mybatis_plus_demo;

import com.gth.mybatis_plus_demo.mapper.UserMapper;
import com.gth.mybatis_plus_demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MybatisPlusDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testInsert() {
        User user = new User();
        user.setName("gaoziting");
        user.setPasswd("123aaa456bbb");
        userMapper.insert(user);
    }



}
