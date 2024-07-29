package com.gth.booksmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;//响应码：1:成功，0:失败
    private String msg;//响应信息 描述字符串
    private Object data;//返回的数据

    public static Result success(){
        return new Result(1,"success",null);
    }

    public static Result success(Object data){
        return new Result(1,"success",data);
    }

    public static Result success(String msg,Object data){
        return new Result(1,msg,data);
    }

    public static Result error(String msg){
        return new Result(0,msg,null);
    }

    public static Result error(String msg,Object data){ return new Result(0,msg,data); }
}
