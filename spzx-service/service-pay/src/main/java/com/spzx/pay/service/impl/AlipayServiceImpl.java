package com.spzx.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.spzx.common.service.exception.GlobalException;
import com.spzx.model.entity.webapp.PaymentInfo;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.pay.properties.AlipayProperties;
import com.spzx.pay.service.AlipayService;
import com.spzx.pay.service.PaymentInfoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayProperties alipayProperties;

    @SneakyThrows  // lombok的注解，对外声明异常
    @Override
    public String pay(String orderNo) {

        PaymentInfo paymentInfo = paymentInfoService.insertPaymentInfo(orderNo);  //保存支付记录

        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();     //创建 AliPay 支付API对应的 request
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());  // 同步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl()); // 异步回调

        HashMap<String, Object> map = new HashMap<>();  // 声明 map 集合储存支付参数
        map.put("out_trade_no", paymentInfo.getOrderNo());
        map.put("product_code","QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount",new BigDecimal("0.01"));
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        // 发送请求
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
        if(response.isSuccess()){
            log.info("调用成功");
            return response.getBody();
        } else {
            log.info("调用失败");
            throw new GlobalException(ResultCodeEnum.USER_REGISTER_DATA_ERROR);
        }
    }

}