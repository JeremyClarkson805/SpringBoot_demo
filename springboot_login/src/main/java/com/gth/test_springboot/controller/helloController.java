package com.gth.test_springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//请求处理类 通过@restController注解实现
@RestController
public class helloController {
    //当前方法处理哪一个请求，通过RequestMapping注解实现
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("Hello World!!!");
        return "Hello World!!!";
    }
}
