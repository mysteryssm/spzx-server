package com.spzx.cart.service;

import com.spzx.model.entity.webapp.CartInfo;

import java.util.List;

public interface CartService {
    void insert(Long skuId, Integer skuNum);

    void delete(Long skuId);

    void deleteAll();

    void check(Long skuId, Integer isChecked);

    void checkAll(Integer isChecked);

    List<CartInfo> select();

    List<CartInfo> selectChecked();

    void deleteChecked();
}