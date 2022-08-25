package com.xuexilema.blog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.xuexilema.blog.dao.pojo.SysUser;
import com.xuexilema.blog.service.LoginService;
import com.xuexilema.blog.service.UserService;
import com.xuexilema.blog.utils.JWTUtils;
import com.xuexilema.blog.vo.ErrorCode;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional // 通常加到接口上
public class LoginServiceImpl implements LoginService {

    private static final String slat = "mszlu!@#";
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginParams loginParams) {

        String account = loginParams.getAccount();

        String password = loginParams.getPassword();

        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {

            return Result.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());

        }

        String pwd = DigestUtils.md5Hex(password + slat);

        SysUser sysUser = userService.findUser(account, pwd);

        if (sysUser == null) {
            return Result.error(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        // 登录成功，使用jwt生成token,返回token和redis中
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);


        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }

        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    /**
     * 登出
     *
     * @param token
     * @return
     */
    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    /**
     * 注册
     * @param loginParams
     * @return
     */
    @Override
    public Result register(LoginParams loginParams) {

        // 注意加上事物，当出现异常时，回滚事物
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password) ||
        StringUtils.isBlank(nickname)){
            return Result.error(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = userService.findUserByAccount(account);
        if(sysUser != null){
            return Result.error(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.userService.save(sysUser);

        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token,JSON.toJSONString(sysUser),1,TimeUnit.DAYS);

        return Result.success(token);
    }

//    public static void main(String[] args) {
//        System.out.println(DigestUtils.md5Hex("admin" + slat));
//    }
}
