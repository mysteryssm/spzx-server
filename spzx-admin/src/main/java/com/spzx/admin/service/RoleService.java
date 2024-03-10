package com.spzx.admin.service;

import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.admin.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @description: SysRoleService
 * @author: yck
 * @create: 2024-02-18
 */

public interface RoleService {

    PageInfo<SysRole> selectByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    Map<String, Object> findAllRoles(Long userId);

    void insert(SysRole sysRole);

    void update(SysRole sysRole);

    void delete(Long roleId);

    void assignMenu(AssignMenuDto assignMenuDto);
}
