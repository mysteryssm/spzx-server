package com.spzx.pay.service;

import com.spzx.model.entity.webapp.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService {
    PaymentInfo savePaymentInfo(String orderNo);

    void updatePaymentStatus(Map<String, String> paramMap ,Integer payType);
}