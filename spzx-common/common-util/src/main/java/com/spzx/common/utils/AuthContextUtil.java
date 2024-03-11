package com.spzx.common.utils;

import com.spzx.model.entity.admin.Administrator;
import com.spzx.model.entity.user.UserInfo;

/**
 * @author ljl
 * @create 2023-10-25-12:03
 */
public class  AuthContextUtil {

    private static final ThreadLocal<Administrator> threadLocal = new ThreadLocal<>();   // 创建一个ThreadLocal对象

    // 定义存储数据的静态方法
    public static void set(Administrator administrator) {
        threadLocal.set(administrator);
    }

    // 定义获取数据的方法
    public static Administrator get() {
        return threadLocal.get();
    }

    // 删除数据的方法
    public static void remove() {
        threadLocal.remove();
    }

    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get();
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }

}