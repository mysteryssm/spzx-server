package com.spzx.admin.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.admin.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: SysRoleMapper
 * @author: yck
 * @create: 2024-02-18
 */

@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    List<SysRole> findAllRoles();

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteSysRole(Long roleId);
}
