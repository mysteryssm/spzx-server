package com.spzx.user.service;

import com.spzx.model.dto.webapp.UserLoginDto;
import com.spzx.model.dto.webapp.UserRegisterDto;

/**
 * @description: UserLoginService
 * @author: yck
 * @create: 2024-03-14
 */

public interface UserLoginService {

    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    void logout(String token);
}
