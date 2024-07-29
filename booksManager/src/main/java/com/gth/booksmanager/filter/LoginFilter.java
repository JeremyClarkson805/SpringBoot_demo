package com.gth.booksmanager.filter;

import com.alibaba.fastjson.JSONObject;
import com.gth.booksmanager.pojo.Result;
import com.gth.booksmanager.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(filterName = "testFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        //1.获取请求的url
        String url = req.getRequestURL().toString();
        //2.判断请求url中是否包含login，如果包含，说明是登陆操作，放行
        if (url.contains("login")){
            log.info("登陆操作，放行......");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //3.获取请求头中的令牌（token）
        String jwt = req.getHeader("token");
//        log.info(req.toString());
        System.out.println(jwt);
        //4.判断令牌是否存在，如果不存在，返回错误结果（未登陆）
        if (!StringUtils.hasLength(jwt)){
            log.info("请求头token为空，返回未登陆的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象->json--->fastjson工具包
            String notLogin = JSONObject.toJSONString(error);//转为json格式的字符串
            resp.getWriter().write(notLogin);//响应未登陆的结果给浏览器
            return;
        }
        //5.解析token，如果解析失败，返回错误结果（未登陆）
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {//说明解析令牌失败
            e.printStackTrace();
            log.info("解析令牌失败，返回未登陆的错误消息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象->json--->fastjson工具包
            String notLogin = JSONObject.toJSONString(error);//转为json格式的字符串
            resp.getWriter().write(notLogin);//响应未登陆的结果给浏览器
            return;
        }
        //6.放行
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
