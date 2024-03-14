package com.spzx.model.globalConstant;

import lombok.Getter;

/**
 * @description: RedisValueEnum
 * @author: yck
 * @create: 2024-02-16
 */

@Getter
public enum RedisValueEnum {

    USER_LOGIN_CAPTCHA(1, "data:image/png;base64,");

    private Integer code;
    private String valuePrefix;

    private RedisValueEnum(Integer code, String valuePrefix) {
        this.code = code;
        this.valuePrefix = valuePrefix;
    }
}
