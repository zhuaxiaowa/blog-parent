package com.xuexilema.blog.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuexilema.blog.dao.mapper.SysUserMapper;
import com.xuexilema.blog.dao.pojo.SysUser;
import com.xuexilema.blog.service.LoginService;
import com.xuexilema.blog.service.UserService;
import com.xuexilema.blog.vo.ErrorCode;
import com.xuexilema.blog.vo.LoginUserVo;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    /**
     * 根据id查询用户
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser findById(Long userId) {

        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("xuexilema");
        }
        return sysUser;
    }

    /**
     * 登录时根据加密后的密码查找用户
     *
     * @param account
     * @param pwd
     * @return
     */
    @Override
    public SysUser findUser(String account, String pwd) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, pwd);
        queryWrapper.select(SysUser::getId, SysUser::getAccount, SysUser::getAvatar, SysUser::getNickname);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    /**
     * 根据token 校验登录后的用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Result findUserByToken(String token) {

        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.error(ErrorCode.TOKEN_NO_EXIST.getCode(), ErrorCode.TOKEN_NO_EXIST.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());

        return Result.success(loginUserVo);
    }

    /**
     * 根据用户查找账户存在
     * @param account
     * @return
     */
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    /**
     * 保存注册后的用户
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {

        // 注意默认生成的id ,是分布式id，由雪花算法生成
        this.sysUserMapper.insert(sysUser);
    }

    /**
     * 根据作者id查找用户信息
     * @param authorId
     * @return
     */
    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if(sysUser == null){  // 一般情况下是不会为null的
            sysUser = new SysUser();
            sysUser.setId(1l);
            sysUser.setNickname("麻神");
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        }
        UserVo userVo = new UserVo();
        userVo.setId(sysUser.getId());
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickName(sysUser.getNickname());
        return userVo;
    }
}
