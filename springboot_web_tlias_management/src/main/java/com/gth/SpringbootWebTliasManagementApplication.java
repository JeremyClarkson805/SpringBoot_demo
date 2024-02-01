package com.gth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@ServletComponentScan//开启对Servlet组件的支持
@ServletComponentScan
@SpringBootApplication
public class SpringbootWebTliasManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebTliasManagementApplication.class, args);
    }

}
