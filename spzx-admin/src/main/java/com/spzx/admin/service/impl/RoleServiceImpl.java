package com.spzx.admin.service.impl;

import com.spzx.admin.mapper.RoleMapper;
import com.spzx.admin.mapper.RoleMenuMapper;
import com.spzx.admin.mapper.AdministratorRoleMapper;
import com.spzx.admin.service.RoleService;
import com.spzx.model.dto.admin.AssignMenuDto;
import com.spzx.model.dto.admin.RoleDto;
import com.spzx.model.entity.admin.Role;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: SysRoleServiceImpl
 * @author: yck
 * @create: 2024-02-18
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdministratorRoleMapper administratorRoleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public PageInfo<Role> selectByPage(RoleDto roleDto, Integer current, Integer limit) {
        PageHelper.startPage(current, limit);   //设置分页参数
        List<Role> roleList = roleMapper.findByPage(roleDto);
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    @Override
    public Map<String, Object> selectByAdministratorId(Long userId) {
        Map<String , Object> resultMap = new HashMap<>();
        List<Role> allRoles = roleMapper.findAllRoles();  // 返回所有的角色信息
        List<Long> userAllRoles = administratorRoleMapper.findAllRoles(userId);   //allRoles 已包含所有角色信息，此处仅传入 roleId

        resultMap.put("allRolesList" , allRoles);
        resultMap.put("sysUserRoles", userAllRoles);

        return resultMap;
    }

    @Override
    public void insert(Role role) {
        roleMapper.saveSysRole(role);
    }

    @Override
    public void update(Role role) {
        roleMapper.updateSysRole(role);
    }

    @Override
    public void delete(Long roleId) {
        roleMapper.deleteSysRole(roleId);
    }

    @Override
    public void assignMenu(AssignMenuDto assignMenuDto) {
        roleMenuMapper.deleteMenuByRoleId(assignMenuDto.getRoleId());    // 删除该角色所对应的菜单数据

        List<Map<String, Number>> list = assignMenuDto.getMenuIdList();// 获取新的角色数据

        list.forEach(map -> {
            roleMenuMapper.assignMenu(assignMenuDto.getRoleId(), map);
        });
    }
}
