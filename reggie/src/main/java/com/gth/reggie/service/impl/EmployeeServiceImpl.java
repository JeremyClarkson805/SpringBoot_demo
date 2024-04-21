package com.gth.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gth.reggie.entity.Employee;
import com.gth.reggie.mapper.EmployeeMapper;
import com.gth.reggie.service.EmployeeService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService{
}
