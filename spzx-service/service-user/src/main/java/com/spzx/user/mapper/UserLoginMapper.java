package com.spzx.user.mapper;

import com.spzx.model.entity.webapp.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserLoginMapper {

    void insert(User user);

    User selectByUsername(@Param("username") String username);

}