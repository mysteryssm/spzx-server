package com.spzx.user.service;

import com.spzx.model.vo.webapp.UserInfoVo;

// 业务接口
public interface UserInfoService {

    UserInfoVo getCurrentUserInfo(String token);
}