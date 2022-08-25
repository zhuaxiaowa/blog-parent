package com.xuexilema.blog.controller;

import com.xuexilema.blog.dao.pojo.SysUser;
import com.xuexilema.blog.utils.UserThreadLocal;
import com.xuexilema.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public Result test(){
        // 怎么拿到访问该资源的用户信息该怎么办？
        // 用ThreadLocal
        SysUser sysUser = UserThreadLocal.getLocal();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
