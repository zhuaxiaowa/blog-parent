package com.xuexilema.blog.handler;

import com.xuexilema.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;

// 对加了controller注解的方法进行拦截   aop的实现
@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody // 返回json数据
    public Result doException(Exception exception){
        exception.printStackTrace();
        return Result.error(-999,"系统错误");
    }
}
