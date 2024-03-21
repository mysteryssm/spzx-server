package com.spzx.model.globalConstant;

import lombok.Getter;

/**
 * @description: PayStatusEnum
 * @author: yck
 * @create: 2024-03-20
 */

@Getter
public enum PayStatusEnum {
    UNPAID(0, "待付款"),
    SHIPPED(1, "发货中"),
    HARVESTED(2, "已收货"),
    CANCEL(3, "取消订单"),
    REFUNDED(-1, "退款");

    private Integer payStatusCode;

    private String payStatus;

    PayStatusEnum(Integer payStatusCode, String payStatus) {
        this.payStatusCode = payStatusCode;
        this.payStatus = payStatus;
    }
}
