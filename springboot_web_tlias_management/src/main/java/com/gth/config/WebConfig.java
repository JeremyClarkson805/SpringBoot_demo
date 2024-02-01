package com.gth.config;

import com.gth.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//配置类
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
        /*
        .addPathPatterns->需要拦截哪些资源
        .excludePathPatterns->不需要拦截哪些资源
        /*：一级路径，能匹配/depts，/emps，/login，不能匹配/depts/1
        /**：任意级路径，能匹配/depts，/depts/1
        /depts/*：/depts下的一句路径，能匹配/depts/1，不能匹配/depts/1/2
        /depts/**：/depts下的任意路径，能匹配/depts，/depts/1，/depts/1/2，不能匹配/emps/1
         */
    }
}
