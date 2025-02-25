package com.spzx.feign.cart;

import com.spzx.model.entity.webapp.CartInfo;
import com.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-05-1:20
 */
@FeignClient(value = "service-cart")
public interface CartFeignClient {

    @GetMapping(value = "/api/order/cart/auth/selectChecked")
    public abstract List<CartInfo> selectChecked();

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    public abstract Result deleteChecked();

}