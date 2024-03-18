package com.spzx.user.service;

import com.spzx.model.entity.webapp.UserAddress;

import java.util.List;

//业务接口
public interface UserAddressService {

    //获取用户地址列表
    List<UserAddress> select();

    //用户收货地址修改
    void update(UserAddress userAddress);

    //用户收货地址新增
    void insert(UserAddress userAddress);

    //用户收货地址刪除
    void delete(Long id);

    //获取地址信息
    UserAddress selectByAddressId(Long id);
}