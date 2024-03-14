package com.spzx.common.utils;

import com.spzx.model.entity.admin.Administrator;
import com.spzx.model.entity.webapp.User;

/**
 * @author ljl
 * @create 2023-10-25-12:03
 */
public class  AuthContextUtil {

    private static final ThreadLocal<Administrator> administratorThreadLocal = new ThreadLocal<>();   // 创建一个ThreadLocal对象

    // 定义存储数据的静态方法
    public static void setAdministrator(Administrator administrator) {
        administratorThreadLocal.set(administrator);
    }

    // 定义获取数据的方法
    public static Administrator getAdministrator() {
        return administratorThreadLocal.get();
    }

    // 删除数据的方法
    public static void deleteAdministrator() {
        administratorThreadLocal.remove();
    }

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    // 定义存储数据的静态方法
    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    // 定义获取数据的方法
    public static User getUser() {
        return userThreadLocal.get();
    }

    // 删除数据的方法
    public static void deleteUser() {
        userThreadLocal.remove();
    }

}