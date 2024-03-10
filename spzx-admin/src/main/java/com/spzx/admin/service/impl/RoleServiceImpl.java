package com.spzx.admin.service.impl;

import com.spzx.admin.mapper.SysRoleMapper;
import com.spzx.admin.mapper.SysRoleMenuMapper;
import com.spzx.admin.mapper.SysUserRoleMapper;
import com.spzx.admin.service.RoleService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.admin.SysRole;
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
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public PageInfo<SysRole> selectByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        PageHelper.startPage(current, limit);   //设置分页参数
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        Map<String , Object> resultMap = new HashMap<>();
        List<SysRole> allRoles = sysRoleMapper.findAllRoles();  // 返回所有的角色信息
        List<Long> userAllRoles = sysUserRoleMapper.findAllRoles(userId);   //allRoles 已包含所有角色信息，此处仅传入 roleId

        resultMap.put("allRolesList" , allRoles);
        resultMap.put("sysUserRoles", userAllRoles);

        return resultMap;
    }

    @Override
    public void insert(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    @Override
    public void update(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    @Override
    public void delete(Long roleId) {
        sysRoleMapper.deleteSysRole(roleId);
    }

    @Override
    public void assignMenu(AssignMenuDto assignMenuDto) {
        sysRoleMenuMapper.deleteMenuByRoleId(assignMenuDto.getRoleId());    // 删除该角色所对应的菜单数据

        List<Map<String, Number>> list = assignMenuDto.getMenuIdList();// 获取新的角色数据

        list.forEach(map -> {
            sysRoleMenuMapper.assignMenu(assignMenuDto.getRoleId(), map);
        });
    }
}
