package com.spzx.user.service.impl;

import com.spzx.model.globalConstant.RedisKeyEnum;
import com.spzx.user.properties.SmsProperties;
import com.spzx.user.service.SmsService;
import com.spzx.user.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ljl
 * @create 2023-11-03-1:21
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SmsProperties smsProperties;

    /**
     * 通过第三方短信服务发送短信验证码
     * @param phone
     */
    @Override
    public void captcha(String phone) {

        //查询redis是否已经缓存了验证码
//        String redisCaptcha = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN_CAPTCHA.getKeyPrefix() + phone);
//        if(StringUtils.hasText(redisCaptcha)) {
//            return;
//        }

        String captcha = RandomStringUtils.randomNumeric(4);   //生成四位验证码

        //将验证码放入redis，并设置过期时间5分钟
        redisTemplate.opsForValue().set(RedisKeyEnum.USER_LOGIN_CAPTCHA.getKeyPrefix() + phone, captcha , 5 , TimeUnit.MINUTES);

        sendSms(phone, captcha);
    }

    /**
     * 传入手机号和生成的验证码，使用第三方服务进行发送
     * @param phone
     * @param captcha
     */
    public void sendSms(String phone, String captcha) {

        Map<String, String> headers = new HashMap<>();
        Map<String, String> querys = new HashMap<>();
        Map<String, String> bodys = new HashMap<>();

        headers.put("Authorization", "APPCODE " + smsProperties.getAppcode());
//        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        querys.put("mobile", phone);
        querys.put("param", "**code**:" + captcha + "**minute**:5");
        querys.put("smsSignId", smsProperties.getSmsSignId());
        querys.put("templateId", smsProperties.getTemplateId());

        try {

            HttpResponse response = HttpUtils.doPost(smsProperties.getHost(), smsProperties.getPath(),
                    smsProperties.getMethod(), headers, querys, bodys);
            log.info("短信验证码发送成功：" + captcha);
            log.info(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


