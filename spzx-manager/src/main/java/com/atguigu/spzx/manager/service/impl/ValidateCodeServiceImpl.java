package com.atguigu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.globalEnum.RedisKeyEnum;
import com.atguigu.spzx.model.globalEnum.RedisValueEnum;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ljl
 * @create 2023-10-24-23:06
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    public ValidateCodeVo generateValidateCode() {

        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);  // 生成图片验证码，参数：宽  高  验证码位数 干扰线数量
        String codeValue = circleCaptcha.getCode();     //  获取4位验证码值
        String imageBase64 = circleCaptcha.getImageBase64();    //返回图片验证码base64编码
        String captchaKey = UUID.randomUUID().toString().replace("-", "");     // 生成uuid作为图片验证码的key

        redisTemplate.opsForValue().set(RedisKeyEnum.USER_LOGIN_CAPTCHA.getKeyPrefix() + captchaKey, codeValue, 5 , TimeUnit.MINUTES);   // 将验证码存储到Redis中

        // 构建验证码响应结果数据
        ValidateCodeVo validateCodeVo = new ValidateCodeVo() ;
        validateCodeVo.setCodeKey(captchaKey);
        validateCodeVo.setCodeValue(RedisValueEnum.USER_LOGIN_CAPTCHA.getValuePrefix() + imageBase64);

        return validateCodeVo;   // 返回数据
    }

}
