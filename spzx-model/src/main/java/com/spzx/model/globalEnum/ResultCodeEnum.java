package com.spzx.model.globalEnum;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    CAPTCHA_EXPIRED_ERROR(201, "验证码过期"),
    CAPTCHA_ERROR(202, "验证码错误"),
    ADMINISTRATOR_LOGIN_AUTH(203, "管理员未登录"),
    ADMINISTRATOR_LOGIN_ERROR(204, "管理员用户名或者密码错误"),
    USER_LOGIN_AUTH(205, "用户未登录"),
    LOGIN_ERROR(206, "用户名或者密码错误"),
    USER_NAME_IS_EXISTS(209, "用户名已经存在"),
    DATA_ERROR(210, "数据异常"),
    ACCOUNT_STOP( 216, "账号已停用"),
    MENU_DELETE_ERROR( 217, "该菜单下有子菜单，不可以删除"),
    STOCK_LESS( 219, "库存不足"),
    NETWORK_ERROR(404, "您的网络有问题请稍后重试"),;

    private Integer code ;      // 业务状态码
    private String message ;    // 响应消息

    private ResultCodeEnum(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }

}
