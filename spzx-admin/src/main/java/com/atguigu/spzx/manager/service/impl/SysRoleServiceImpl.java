package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.mapper.SysUserRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
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
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
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
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    @Override
    public void deleteSysRole(Long roleId) {
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
