package com.spzx.model.globalEnum;

import lombok.Getter;

/**
 * @description: RedisKeyEnum
 * @author: yck
 * @create: 2024-02-16
 */

@Getter
public enum RedisKeyEnum {
    USER_LOGIN(1, "user:login:token:"),
    USER_LOGIN_CAPTCHA(2, "user:login:captcha:"),
    CATEGORY_FIRST(3, "category:first");

    private Integer code;
    private String keyPrefix;

    private RedisKeyEnum(Integer code, String keyPrefix) {
        this.code = code;
        this.keyPrefix = keyPrefix;
    }

}
