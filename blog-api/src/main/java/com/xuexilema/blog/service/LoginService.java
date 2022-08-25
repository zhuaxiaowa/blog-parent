package com.xuexilema.blog.service;

import com.xuexilema.blog.dao.pojo.SysUser;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.params.LoginParams;

public interface LoginService {

    /**
     * 登录
     *
     * @param loginParams
     * @return
     */
    Result login(LoginParams loginParams);


    /**
     * 校验token
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 登出
     * @return
     * @param token
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParams
     * @return
     */
    Result register(LoginParams loginParams);
}
