package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @description: SysRoleService
 * @author: yck
 * @create: 2024-02-18
 */

public interface SysRoleService {

    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    Map<String, Object> findAllRoles(Long userId);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteSysRole(Long roleId);

    void assignMenu(AssignMenuDto assignMenuDto);
}
