package com.gth.test_springboot.controller;

import com.gth.test_springboot.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class RequestController {

    //传统非springboot的形式：需要手动进行类型转换和接收
//    @RequestMapping("/simpleParam")
//    public String simpleParam(HttpServletRequest req){
//        String name = req.getParameter("name");
//        String ageStr = req.getParameter("age");
//        int age = Integer.parseInt(ageStr);
//        System.out.println("name = " + name +  ",age = " + age);
//        return "OK";
//    }

    //使用springboot来接收形参，把形参名定义成我传入的数据的名即可，类型直接写我需要的，springboot会自动把原始传进来的String转换成我需要的数据类型
    @RequestMapping("/simpleParam")
    public String simpleParam(String name,Integer age){
        System.out.println("name = " + name +  ",age = " + age);
        return "OK";
    }
    /*
    可以通过@RequestParam注解的方式来映射形参，比如我浏览器传进来的是username，但我在这里用的是name来接收
    就可以通过public String simpleParam(@RequestParam("username")String name,Integer age)
    此时，如果不传username这个参数实惠报错的，因为后面默认跟了一个参数required 默认是为true的
    代表该参数必须传递，如果不传递将会报错，如果需要可以传递空则需要将其设置为false
     */

    //实体参数
    //简单实体参数的接收只需要请求参数名与形参对象数据名相同，定义pojo接收即可
    @RequestMapping("/simplePojo")
    public String simplePojo(User user){
        System.out.println(user);
        return "OK";
    }

    //复杂一点的通过定义多个实体类并通过他们的嵌套来实现数据的传递
    @RequestMapping("/complexPojo")
    public String complexPojo(User user){
        System.out.println(user);
        return "OK";
    }

    //数组参数：请求参数名与形参数组名称相同且请求参数为多个，定义数组类型形参即可接收参数
    @RequestMapping("/arrayParam")
    public String arrayParam(String[] hobby){
        System.out.println(Arrays.toString(hobby));
        return "OK";
    }

    //请求参数名与形参中集合变量名相同，通过@RequestParam绑定参数关系
    @RequestMapping("listParam")
    public String listParam(@RequestParam List<String> hobby){
        System.out.println(hobby);
        return "OK";
    }

    //日期时间参数
    //通过DateTimeFormat（pattern=“”）来指定传入参数的格式
    @RequestMapping("/dateParam")
    public String dateParam(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime updateTime){
        System.out.println(updateTime);
        return "OK";
    }

    //JSON的传递
    //JSON数据键名与形参对象属性名相同，定义pojo类型形参即可接收参数，需使用@RequestBody标识
    //@RequestBody注解可以将json格式的数据封装到实体类中去
    @RequestMapping("/jsonParam")
    public String jsonParam(@RequestBody User user){
        System.out.println(user);
        return "OK";
    }

    //路径参数：通过请求URL直接传递参数，使用{}来标识该路径参数,可以传多个值，但这样需要传入的形参和绑定的形参名字要一致
    //@PathVariable注解可以获取路径参数,并把获取到的值绑定给某个变量
    @RequestMapping("/path/{id}")
    public String pathParam(@PathVariable Integer id){
        System.out.println(id);
        return "OK";
    }
}
