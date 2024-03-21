package com.spzx.model.globalConstant;

import lombok.Getter;

/**
 * @description: PayTypeEnum
 * @author: yck
 * @create: 2024-03-20
 */

@Getter
public enum PayTypeEnum {
    WECHAT_PAY(1, "微信支付"),
    ALI_PAY(2, "支付宝");

    private Integer payTypeCode;
    private String payType;

    PayTypeEnum(Integer payTypeCode, String payType) {
        this.payTypeCode = payTypeCode;
        this.payType = payType;
    }
}
