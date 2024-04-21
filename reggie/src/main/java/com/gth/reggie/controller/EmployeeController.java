package com.gth.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gth.reggie.common.R;
import com.gth.reggie.entity.Employee;
import com.gth.reggie.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1.将页面提交的密码password进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        log.info(password);
        //2.根据页面提交的用户名去查数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();//创建了一个新的LambdaQueryWrapper对象，它用于构建针对Employee类的查询条件
        queryWrapper.eq(Employee::getUsername,employee.getUsername());//使用eq方法添加了一个等值条件，表示查询username字段等于employee.getUsername()返回值的记录
        Employee emp = employeeService.getOne(queryWrapper);//调用employeeService的getOne方法，传入之前构建的queryWrapper作为参数，执行查询并返回结果。如果查询到匹配的记录，就会返回一个Employee对象，否则返回null

        //3.如果没有查询到则返回登陆失败结果
        if (emp == null){
            return R.error("登陆失败,查无此人");
        }

//        log.info("数据库密码"+emp.getPassword());
//        log.info("md5加密后的密码："+password);
        //4.比对密码，如果不一致则返回登陆失败结果
        if (!emp.getPassword().equals(password)){
            return R.error("登陆失败,密码错误");
        }

        //5.查看员工状态，如果已禁用，则返回员工已被禁用的结果
        if (emp.getStatus() == 0){
            return R.error("员工已被禁用");
        }

        //6.登陆成功，将员工Id存入Session并返回登陆成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    //退出功能的实现
    //清除保存的SessionId
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("成功退出！");
    }

    //新增员工
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee) {
        log.info("新增员工，员工信息{}",employee.toString());
        //设置初始密码，并进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
}
