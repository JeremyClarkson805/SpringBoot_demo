package com.gth.test_springboot.controller;

import com.gth.test_springboot.pojo.Address;
import com.gth.test_springboot.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
/*
@RestController = @Controller+@ResponseBody
@ResponseBody
    位置：方法/类上
    作用：将方法返回值直接响应，如果返回值时实体对象/集合，将会转换为JSON格式响应
 */

@RestController
public class ResponseController {
    @RequestMapping("/getAddr")
    public Address getAddr() {return new Address("广东","湛江");}

    @RequestMapping("/listAddr")
    public Result listAddr(){
        List<Address> list = new ArrayList<>();
        Address a1 = new Address("广东","湛江");
        Address a2 = new Address("广东","广州");
        list.add(a1);list.add(a2);
        return Result.success(list);
    }
}
