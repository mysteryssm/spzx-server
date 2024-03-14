package com.spzx.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spzx.model.entity.webapp.User;
import com.spzx.model.globalConstant.RedisKeyEnum;
import com.spzx.model.vo.common.Result;
import com.spzx.model.globalConstant.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();  // 获取请求
        String path = request.getURI().getPath();   // 从请求中取出请求路径


        // 判断请求路径是否需要校验
        if(antPathMatcher.match("/api/**/auth/**", path)) {
            User user = this.getUserInfo(request);  // 从请求中获取用户信息
            if(null == user) {
                ServerHttpResponse response = exchange.getResponse();
                return out(response, ResultCodeEnum.USER_LOGIN_AUTH);   // 用户未登录返回错误信息
            }
        }

        return chain.filter(exchange);  // 放行
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 从请求中获取用户信息
     * @param request
     * @return
     */
    private User getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(null  != tokenList) {
            token = tokenList.get(0);
        }
        if(!StringUtils.isEmpty(token)) {
            String userInfoJSON = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN_TOKEN.getKeyPrefix() + token);
            if(StringUtils.isEmpty(userInfoJSON)) {
                return null ;
            }else {
                return JSON.parseObject(userInfoJSON , User.class) ;
            }
        }
        return null;
    }

    /**
     * 返回错误信息
     * @param response
     * @param resultCodeEnum
     * @return
     */
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

}