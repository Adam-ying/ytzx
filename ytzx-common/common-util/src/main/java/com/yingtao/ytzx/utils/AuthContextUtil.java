package com.yingtao.ytzx.utils;

import com.yingtao.ytzx.model.entity.system.SysUser;
import com.yingtao.ytzx.model.entity.user.UserInfo;

/**
 * @author Adam
 * @create 2024-04-14 19:38
 */
public class AuthContextUtil {
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();


    public static void setUserInfo(UserInfo userInfo){
        userInfoThreadLocal.set(userInfo);
    }

    public static UserInfo getUserInfo(){
        return userInfoThreadLocal.get();
    }

    public static void removeUserInfo(){
        userInfoThreadLocal.remove();
    }

    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }

    public static SysUser get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }

}
