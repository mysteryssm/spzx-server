package com.spzx.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.spzx.common.service.exception.GlobalException;
import com.spzx.model.dto.webapp.UserLoginDto;
import com.spzx.model.dto.webapp.UserRegisterDto;
import com.spzx.model.entity.webapp.User;
import com.spzx.model.globalConstant.DefaultUserAttribute;
import com.spzx.model.globalConstant.RedisKeyEnum;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.user.mapper.UserLoginMapper;
import com.spzx.user.service.UserLoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description: UserLoginServiceImpl
 * @author: yck
 * @create: 2024-03-14
 */

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserLoginMapper userLoginMapper;

    /**
     * 用户注册
     * @param userRegisterDto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserRegisterDto userRegisterDto) {

        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String captcha = userRegisterDto.getCaptcha();

        // 校验注册信息是否合法，此处仅仅判空，不做其他校验
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) || StringUtils.isEmpty(captcha)) {
            throw new GlobalException(ResultCodeEnum.USER_REGISTER_DATA_ERROR);
        }

        String redisCaptcha = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN_CAPTCHA.getKeyPrefix() + username);

        // 校验验证码是否已过期
        if(StrUtil.isEmpty(redisCaptcha)) {
            throw new GlobalException(ResultCodeEnum.CAPTCHA_EXPIRED_ERROR);
        }

        // 校验验证码正确性
        if(!captcha.equals(redisCaptcha)) {
            throw new GlobalException(ResultCodeEnum.CAPTCHA_ERROR);
        }

        // 根据用户名查询用户信息
        User user = userLoginMapper.selectUserByUsername(username);

        // 判断该手机号是否已经注册
        if(user != null) {
            throw new GlobalException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        user = new User();
        user.setUsername(username);
        user.setNickName(nickName);
        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        user.setPhone(username);
        user.setStatus(1);
        user.setSex(0);
        user.setAvatar(DefaultUserAttribute.DEFAULT_AVATAR);

        userLoginMapper.insert(user);

        // 删除验证码
        redisTemplate.delete(RedisKeyEnum.USER_LOGIN_CAPTCHA.getKeyPrefix() + username);
    }

    /**
     * 用户登录
     * @param userLoginDto
     * @return
     */
    @Override
    public String login(UserLoginDto userLoginDto) {

        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        // 校验用户登录参数，此处仅仅判空，不做其他校验
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new GlobalException(ResultCodeEnum.USER_REGISTER_DATA_ERROR);
        }

        User user = userLoginMapper.selectUserByUsername(username);

        // 校验该手机号是否已注册
        if(user == null) {
            throw new GlobalException(ResultCodeEnum.USER_LOGIN_ERROR);
        }

        String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());

        //校验密码是否正确
        if(!md5InputPassword.equals(user.getPassword())) {
            throw new GlobalException(ResultCodeEnum.USER_LOGIN_ERROR);
        }

        //校验该账号是否被禁用
        if(user.getStatus() == 0) {
            throw new GlobalException(ResultCodeEnum.ACCOUNT_STOP);
        }

        String token = UUID.randomUUID().toString().replaceAll("-", "");    // 随机生成 token
        redisTemplate.opsForValue().set(RedisKeyEnum.USER_LOGIN_TOKEN.getKeyPrefix() + token,
                JSON.toJSONString(user), 30, TimeUnit.DAYS);

        return token;
    }

    /**
     * 用户退出登录
     * @param token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete(RedisKeyEnum.USER_LOGIN_TOKEN.getKeyPrefix() + token);
    }
}
