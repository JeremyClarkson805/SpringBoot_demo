package com.gth.test_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//告诉编译器这是一个SpringBoot项目的主入口程序，这是个启动类，默认扫描当前包及其子包
public class TestSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringbootApplication.class, args);
    }
}
