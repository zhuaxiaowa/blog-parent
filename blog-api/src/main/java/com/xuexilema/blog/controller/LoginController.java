package com.xuexilema.blog.controller;

import com.xuexilema.blog.service.LoginService;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result Login(@RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }

}
