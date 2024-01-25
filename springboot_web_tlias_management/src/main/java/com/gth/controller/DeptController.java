package com.gth.controller;

import com.gth.pojo.Dept;
import com.gth.pojo.Result;
import com.gth.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//部门管理的Controller
@Slf4j
@RestController
@RequestMapping("/depts")//一个完整的请求路径是类上的@RequestMapping的value属性+方法上的@RequestMapping的value属性
public class DeptController {
//    private static Logger log = LoggerFactory.getLogger(DeptController.class);//这行代码可通过@Slf4j注解实现

    @Autowired
    private DeptService deptService;

    //查询部门数据
//    @RequestMapping(value = "/depts",method = RequestMethod.GET)//限定请求方式为Mapping
    @GetMapping//也可使用该种注解
    public Result list(){
//        System.out.println("查询全部部门数据");//不专业，不要这样做！用日志来记录
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    //删除部门
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门：{}",id);
        deptService.delete(id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Dept dept){//通过RequestBody来将获得的参数封装进实体类
        deptService.add(dept);
        return Result.success();
    }
}
