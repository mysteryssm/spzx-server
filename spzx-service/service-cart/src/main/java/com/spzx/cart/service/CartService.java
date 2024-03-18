package com.spzx.cart.service;

import com.spzx.model.entity.webapp.CartInfo;

import java.util.List;

public interface CartService {
    void insert(Long skuId, Integer skuNum);

    void delete(Long skuId);

    void deleteChecked();

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    void deleteAll();

    List<CartInfo> getAllCkecked();

    List<CartInfo> getCartList();
}