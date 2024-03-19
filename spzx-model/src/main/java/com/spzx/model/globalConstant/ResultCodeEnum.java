package com.spzx.model.globalConstant;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"),
    CAPTCHA_EXPIRED_ERROR(301, "验证码过期"),
    CAPTCHA_ERROR(302, "验证码错误"),
    ADMINISTRATOR_LOGIN_AUTH(303, "管理员未登录"),
    ADMINISTRATOR_LOGIN_ERROR(304, "管理员用户名或者密码错误"),
    USER_LOGIN_AUTH(305, "用户未登录"),
    USER_LOGIN_ERROR(306, "用户名或者密码错误"),
    USER_NAME_IS_EXISTS(307, "用户名已存在，请重新注册或直接登录"),
    USER_REGISTER_DATA_ERROR(308, "用户注册数据异常！"),
    USER_LOGIN_DATA_ERROR(309, "用户登录数据异常！"),
    ACCOUNT_STOP( 310, "该账号已停用！"),
    NETWORK_ERROR(404, "您的网络有问题请稍后重试"),
    STOCK_LESS( 501, "库存不足"),
    PRODUCT_EXIST_ERROR( 502, "商品不存在！"),
    MENU_DELETE_ERROR( 601, "该菜单下有子菜单，无法删除！");

    private Integer code ;      // 业务状态码
    private String message ;    // 响应消息

    private ResultCodeEnum(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }

}
