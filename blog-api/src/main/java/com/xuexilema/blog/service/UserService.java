package com.xuexilema.blog.service;

import com.xuexilema.blog.dao.pojo.SysUser;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.UserVo;

public interface UserService {

    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    SysUser findById(Long userId);

    /**
     * 登录查询用户
     * @param account
     * @param pwd
     * @return
     */
    SysUser findUser(String account, String pwd);

    /**
     * 登录成功后校验token
     * @param token
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查询用户是否存在
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存注册后的用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据作者Id查找用户信息
     * @param authorId
     * @return
     */
    UserVo findUserVoById(Long authorId);

    /**
     *
     * @param toUid
     * @return
     */
}
