package com.spzx.user.service;

import com.spzx.model.entity.webapp.UserAddress;

import java.util.List;

public interface UserAddressService {

    void insert(UserAddress userAddress);

    void delete(Long id);

    void update(UserAddress userAddress);

    List<UserAddress> select();

    UserAddress selectByAddressId(Long addressId);
}