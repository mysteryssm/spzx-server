package com.spzx.common.utils;

import com.spzx.model.entity.admin.Administrator;
import com.spzx.model.entity.webapp.User;

/**
 * @author ljl
 * @create 2023-10-25-12:03
 */
public class  AuthContextUtil {

    // 创建一个ThreadLocal对象
    private static final ThreadLocal<Administrator> administratorThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setAdministrator(Administrator administrator) {
        administratorThreadLocal.set(administrator);
    }

    public static Administrator getAdministrator() {
        return administratorThreadLocal.get();
    }

    public static void deleteAdministrator() {
        administratorThreadLocal.remove();
    }

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void deleteUser() {
        userThreadLocal.remove();
    }

}