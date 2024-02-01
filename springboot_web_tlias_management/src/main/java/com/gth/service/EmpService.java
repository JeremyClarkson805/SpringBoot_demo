package com.gth.service;

import com.gth.pojo.Emp;
import com.gth.pojo.PageBean;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    //分页查询
    PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin,LocalDate end);

    //批量删除操作
    void delete(List<Integer> ids);

    //新增员工
    void save(Emp emp);

    //根据ID查询员工
    Emp getById(Integer id);

    //更新员工
    void update(Emp emp);

    //员工登陆
    Emp login(Emp emp);
}
