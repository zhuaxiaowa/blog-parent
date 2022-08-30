package com.xuexilema.blog.admin.service;

import com.xuexilema.blog.admin.pojo.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    public UserDetails loadUserByUsername(String username){

        log.info("username:{}",username);
        // 当用户登录的时候，springsecurity就会转发到此
        // 根据用户名查找用户，如果不存在就抛出异常，如果找到用户，就把用户名，密码和授权列表组装成spring
        // security的user对象，并返回
        Admin admin = adminService.findUserByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails userDetails = new User(username,admin.getPassword(),authorities);
        // 剩下的认证就由框架帮我们完成
        return userDetails;
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
