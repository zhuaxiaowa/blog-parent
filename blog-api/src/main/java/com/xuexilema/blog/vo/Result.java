package com.xuexilema.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 统一返回结果
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Boolean success;

    private Integer code;

    private String msg;

    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result error(Integer code,String msg){
        return new Result(false,code,msg,null);
    }
}
