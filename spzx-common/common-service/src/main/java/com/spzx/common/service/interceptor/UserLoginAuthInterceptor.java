package com.spzx.common.service.interceptor;

import com.alibaba.fastjson.JSON;
import com.spzx.model.entity.webapp.User;
import com.spzx.common.utils.AuthContextUtil;
import com.spzx.model.globalConstant.RedisKeyEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 请求验证通过后，处理用户登录 token 以及用户信息
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");  // 从请求头中获取 token
        // 通过 token 从 redis 中获取对应的用户信息，此处是否需要校验用户信息？
        String userJSON = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN_TOKEN.getKeyPrefix() + token);

        User user = JSON.parseObject(userJSON, User.class); // 将 userJson 转化为对应的实体类对象
        AuthContextUtil.setUser(user);

        // 获取当前 token 的过期时间
        Long oldExpire = redisTemplate.getExpire(RedisKeyEnum.USER_LOGIN_TOKEN.getKeyPrefix() + token);
        if(oldExpire <= 1800) {
            oldExpire += 1800;
        }

        redisTemplate.expire(RedisKeyEnum.USER_LOGIN_TOKEN.getKeyPrefix() + token,
                oldExpire, TimeUnit.SECONDS);

        return true;
    }

}