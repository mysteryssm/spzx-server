package com.spzx.user.mapper;

import com.spzx.model.entity.webapp.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {

    void insert(UserAddress userAddress);

    void delete(Long id);

    void update(UserAddress userAddress);

    UserAddress selectByAddressId(Long id);

    List<UserAddress> select(Long userId);
}