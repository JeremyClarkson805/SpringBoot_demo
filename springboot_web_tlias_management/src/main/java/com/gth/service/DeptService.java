package com.gth.service;

import com.gth.pojo.Dept;

import java.util.List;

public interface DeptService {
    //查询全部部门数据
    List<Dept> list();

    //删除部门
    void delete(Integer id);

    //新增部门
    void add(Dept dept);
}
