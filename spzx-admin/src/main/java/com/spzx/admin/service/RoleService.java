package com.spzx.admin.service;

import com.spzx.model.dto.admin.AssignMenuDto;
import com.spzx.model.dto.admin.RoleDto;
import com.spzx.model.entity.admin.Role;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @description: SysRoleService
 * @author: yck
 * @create: 2024-02-18
 */

public interface RoleService {

    PageInfo<Role> selectByPage(RoleDto roleDto, Integer current, Integer limit);

    Map<String, Object> selectByAdministratorId(Long userId);

    void insert(Role role);

    void update(Role role);

    void delete(Long roleId);

    void assignMenu(AssignMenuDto assignMenuDto);
}
