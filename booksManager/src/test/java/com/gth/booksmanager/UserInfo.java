package com.gth.booksmanager;

import com.alibaba.excel.annotation.ExcelProperty;

// 定义一个与Excel数据对应的Java类
public class UserInfo {
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("邮箱")
    private String email;

    // 添加公共的无参构造函数
    public UserInfo() {
    }

    // 保留现有的带参构造函数
    public UserInfo(String name, int age, String mail) {
        this.name = name;
        this.age = age;
        this.email = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}