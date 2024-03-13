package com.spzx.admin.service;

import com.spzx.model.dto.admin.AssignRoleDto;
import com.spzx.model.dto.admin.AdministratorLoginDto;
import com.spzx.model.dto.admin.AdministratorDto;
import com.spzx.model.entity.admin.Administrator;
import com.spzx.model.vo.admin.LoginVo;
import com.github.pagehelper.PageInfo;

/**
 * @author ljl
 * @create 2023-10-22-16:36
 */
public interface AdministratorService {

    /**
     * 根据用户名查询用户数据
     * @return
     */
    public abstract LoginVo login(AdministratorLoginDto administratorLoginDto) ;

    /**
     * 根据token查询用户数据
     * @return
     */
    Administrator getUserInfo(String token);

    /**
     * 退出功能
     * @return
     */
    void logout(String token);

    /**
     * @Description: 用户条件分页查询接口
     * @param administratorDto
     * @param pageNum
     * @param pageSize
     */
    PageInfo<Administrator> selectByPage(AdministratorDto administratorDto, Integer pageNum, Integer pageSize);

    /**
     * @Description: 用户添加接口
     * @param administrator
     */
    void insert(Administrator administrator);

    /**
     * @Description: 用户修改接口
     * @param administrator
     */
    void update(Administrator administrator);

    /**
     * @Description: 用户删除接口
     * @param userId
     */
    void delete(Long userId);

    void assignRole(AssignRoleDto assignRoleDto);
}
