package com.spzx.cart.service;

import com.spzx.model.entity.webapp.CartInfo;

import java.util.List;

public interface CartService {
    void insert(Long skuId, Integer skuNum);

    List<CartInfo> getCartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    void deleteAll();

    List<CartInfo> getAllCkecked();

    void deleteChecked();
}