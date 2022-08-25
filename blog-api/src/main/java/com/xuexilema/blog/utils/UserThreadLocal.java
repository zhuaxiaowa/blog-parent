package com.xuexilema.blog.utils;

import com.xuexilema.blog.dao.pojo.SysUser;


public class UserThreadLocal {


    private UserThreadLocal() {
    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void putLocal(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser getLocal() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
