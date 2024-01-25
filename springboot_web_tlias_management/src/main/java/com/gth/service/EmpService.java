package com.gth.service;

import com.gth.pojo.PageBean;

import java.time.LocalDate;

public interface EmpService {
    //分页查询
    PageBean page(Integer page, Integer pageSize, String name, Short gender, LocalDate begin,LocalDate end);
}
