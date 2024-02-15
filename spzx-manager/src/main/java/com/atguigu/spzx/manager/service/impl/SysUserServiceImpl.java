package com.atguigu.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.SpzxException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ljl
 * @create 2023-10-22-16:37
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper ;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    public LoginVo login(LoginDto loginDto) {

//        String captcha = loginDto.getCaptcha();     // 用户输入的验证码
//        String codeKey = loginDto.getCodeKey();     // 验证码的数据key，用于在Redis中查询对应的验证码
//        String redisCode = redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);   //从Redis中获取验证码
//
//        // 校验验证码是否正确
//        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode , captcha)) {
//            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);   //验证码错误时抛出异常
//        }
//
//        redisTemplate.delete("user:login:validatecode:" + codeKey);    //验证通过需要删除redis中的验证码

        SysUser sysUser = sysUserMapper.queryUserByName(loginDto.getUserName());    //根据用户名查询用户
        if(sysUser == null) {
            log.info("用户名为{}的用户不存在", loginDto.getUserName());
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR); //用户不存在时抛出异常
        }

        String inputPassword = loginDto.getPassword(); //从loginDto中获取用户输入的密码
        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes()); // 将用户输入密码进行md5加密，32位小
        //验证密码是否正确
        if(!md5InputPassword.equals(sysUser.getPassword())) {
            log.info("用户名为{}的用户密码不为{}", loginDto.getUserName(), md5InputPassword);
            throw new SpzxException(ResultCodeEnum.LOGIN_ERROR); //密码错误时抛出异常
        }

        String token = UUID.randomUUID().toString().replace("-", "");   // 生成token
        // 将token作为key放入Redis中，value为用户信息，并设置过期时间
        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(sysUser) , 30 , TimeUnit.MINUTES);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        return loginVo; // 返回loginVo
    }

    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        return JSON.parseObject(userJson , SysUser.class) ;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token) ;
    }

    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum , pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto) ;
        PageInfo pageInfo = new PageInfo(sysUserList) ;
        return pageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {

        // 根据输入的用户名查询用户
        SysUser dbSysUser = sysUserMapper.queryUserByName(sysUser.getUserName()) ;
        if(dbSysUser != null) {
            throw new SpzxException(ResultCodeEnum.USER_NAME_IS_EXISTS) ;
        }

        // 对密码进行加密
        String password = sysUser.getPassword();
        String digestPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(digestPassword);
        sysUser.setStatus(1);
        sysUserMapper.saveSysUser(sysUser) ;
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser) ;
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId) ;
    }
}