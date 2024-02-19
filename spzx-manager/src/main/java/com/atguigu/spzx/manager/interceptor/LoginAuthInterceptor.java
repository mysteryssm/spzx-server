package com.atguigu.spzx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.globalEnum.RedisKeyEnum;
import com.atguigu.spzx.model.globalEnum.RequestMethod;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @description: LoginAuthInterceptor
 * @author: yck
 * @create: 2024-02-17
 */

@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //校验请求的方法，若方法为options，则放行
        String method = request.getMethod();
        if(method.equals(RequestMethod.OPTIONS)) {
            return true;
        }

        String token = request.getHeader("token");  //从请求中获取token

        //若 token 为空，则返回用户未登录状态码给前端并拦截请求
        if(StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }

        //若用户信息为空，则返回用户未登录状态码给前端并拦截请求
        String sysUserInfoJson = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN.getKeyPrefix() + token);
        if(StrUtil.isEmpty(sysUserInfoJson)) {
            responseNoLoginInfo(response);
            return false;
        }

        SysUser sysUser = JSON.parseObject(sysUserInfoJson, SysUser.class); // 将 JSON 格式的用户系统转化为对应的实体类
        AuthContextUtil.set(sysUser);   //将用户数据存储到 ThreadLocal 中

        Long oldExpire = redisTemplate.getExpire(RedisKeyEnum.USER_LOGIN.getKeyPrefix() + token);
        if(oldExpire <= 1800) {
            oldExpire += 1800;
        }
        //重置 Redis 中的 token 以及用户数据的有效时间
        redisTemplate.expire(RedisKeyEnum.USER_LOGIN.getKeyPrefix() + token , oldExpire, TimeUnit.SECONDS);

        return true;   //放行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    //响应用户未登录状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
}
