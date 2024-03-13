package com.spzx.user.mapper;

import com.spzx.model.entity.webapp.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {

    void save(UserInfo userInfo);

    UserInfo getByUsername(@Param("username") String username);

}