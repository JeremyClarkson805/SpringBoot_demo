package com.gth.booksmanager.common;

import com.gth.booksmanager.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//全局异常处理
@Slf4j
@RestController
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public Result exceptionHandler(IllegalStateException ex){
        log.error(ex.getMessage());
        return null;
    }
}
