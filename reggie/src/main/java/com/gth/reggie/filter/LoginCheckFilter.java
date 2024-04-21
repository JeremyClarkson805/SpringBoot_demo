package com.gth.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.gth.reggie.common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

//检查用户是否登陆
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持使用通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        log.info("拦截到请求：{}",request.getRequestURI());
        //1.获取本次请求的URI
        String requestURI = request.getRequestURI();
        log.info("本次请求的URI：{}",requestURI);
        String[] urls = new String[]{//定义不需要处理的请求路径
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        //2.判断本次请求是否需要处理
        boolean check = check(urls,requestURI);

        //3.如果不需要处理，则直接放行
        if (check){
            log.info("本次请求{}不需要处理，放行",requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        //4.判断登陆状态，如果登陆了，放行
        if (request.getSession().getAttribute("employee") != null){
            filterChain.doFilter(request, response);
            log.info("id:{},已登陆，放行",request.getSession().getAttribute("employee"));
            return;
        }

        //5.如果未登陆，则返回未登陆结果,通过输出流对象向客户端页面响应数据
        log.info("用户未登陆，返回未登陆信息");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
//        filterChain.doFilter(request, response);//放行拦截
    }

    //路径匹配，检查本次请求是否需要拦截
    public boolean check(String[] urls,String requestURI){
        for (String url : urls){
            boolean match = PATH_MATCHER.match(url,requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
