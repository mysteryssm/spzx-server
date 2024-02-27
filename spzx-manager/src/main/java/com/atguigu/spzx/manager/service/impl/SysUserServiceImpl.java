package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GlobalException;
import com.atguigu.spzx.manager.mapper.SysUserRoleMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.globalEnum.RedisKeyEnum;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
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
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {

        String captcha = loginDto.getCaptcha();     // 用户输入的验证码
        String codeKey = loginDto.getCodeKey();     // 验证码的key，用于在Redis中查询对应的验证码
        String redisCaptcha = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN_CAPTCHA.getKeyPrefix() + codeKey);   //从Redis中获取验证码

        // 校验验证码是否过期
        if (StrUtil.isEmpty(redisCaptcha)) {
            log.info("验证码已过期");
            throw new GlobalException(ResultCodeEnum.CAPTCHA_EXPIRED_ERROR);   //验证码过期时抛出异常
        }

        // 校验验证码是否正确
        if (StrUtil.isEmpty(redisCaptcha) || !StrUtil.equalsIgnoreCase(redisCaptcha, captcha)) {
            log.info("验证码{}错误，正确验证码为{}", captcha, redisCaptcha);
            throw new GlobalException(ResultCodeEnum.CAPTCHA_ERROR);   //验证码错误时抛出异常
        }

        SysUser sysUser = sysUserMapper.queryUserByName(loginDto.getUserName());    //根据用户名查询用户
        //验证用户是否存在
        if(sysUser == null) {
            log.info("用户名为{}的用户不存在", loginDto.getUserName());
            throw new GlobalException(ResultCodeEnum.LOGIN_ERROR); //用户不存在时抛出异常
        }

        String inputPassword = loginDto.getPassword(); //从loginDto中获取用户输入的密码
        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes()); // 将用户输入密码进行md5加密，32位小
        //验证密码是否正确
        if(!md5InputPassword.equals(sysUser.getPassword())) {
            log.info("用户名为{}的用户密码不为{}", loginDto.getUserName(), md5InputPassword);
            throw new GlobalException(ResultCodeEnum.LOGIN_ERROR); //密码错误时抛出异常
        }

        redisTemplate.delete(RedisKeyEnum.USER_LOGIN_CAPTCHA.getKeyPrefix() + codeKey);    //用户成功登录需要删除redis中的验证码

        String token = UUID.randomUUID().toString().replace("-", "");   // 生成token
        // 将token作为key放入Redis中，value为用户信息，并设置过期时间
        redisTemplate.opsForValue().set(RedisKeyEnum.USER_LOGIN.getKeyPrefix() + token , JSON.toJSONString(sysUser) , 7, TimeUnit.DAYS);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        return loginVo; // 返回loginVo
    }

    public SysUser getUserInfo(String token) {
        String sysUserInfoJson = redisTemplate.opsForValue().get(RedisKeyEnum.USER_LOGIN.getKeyPrefix() + token);   // 通过 token 获取用户信息
        return JSON.parseObject(sysUserInfoJson , SysUser.class) ; // 将 json 格式的用户信息转换为 SysUser 类
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(RedisKeyEnum.USER_LOGIN.getKeyPrefix() + token) ;
    }

    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto);
        PageInfo pageInfo = new PageInfo(sysUserList);
        return pageInfo;
    }

    @Override
    public void save(SysUser sysUser) {

        // 验证用户名是否已存在
        if(sysUserMapper.queryUserByName(sysUser.getUserName()) != null) {
            throw new GlobalException(ResultCodeEnum.USER_NAME_IS_EXISTS);  // 若用户名已存在，抛出 USER_NAME_IS_EXISTS 异常
        }

        String password = sysUser.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());    // 对用户密码进行 md5 加密
        sysUser.setPassword(md5Password);
        sysUser.setStatus(1);
        sysUserMapper.saveSysUser(sysUser); // 将用户信息存入数据库
    }

    @Override
    public void update(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public void delete(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    @Override
    public void assignRole(AssignRoleDto assignRoleDto) {
        sysUserRoleMapper.deleteByUserId(assignRoleDto.getUserId());    // 删除该用户所对应的角色数据

        List<Long> roleIdList = assignRoleDto.getRoleIdList();  // 获取新的角色数据
        roleIdList.forEach(roleId -> {
            sysUserRoleMapper.assignRole(assignRoleDto.getUserId(), roleId);
        });
    }
}