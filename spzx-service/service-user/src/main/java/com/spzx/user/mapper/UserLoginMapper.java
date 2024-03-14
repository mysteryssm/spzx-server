package com.spzx.user.mapper;

import com.spzx.model.entity.webapp.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserLoginMapper {

    void insert(UserInfo userInfo);

    UserInfo selectUserByUsername(@Param("username") String username);

}