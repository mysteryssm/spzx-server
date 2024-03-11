package com.spzx.admin.mapper;

import com.spzx.model.dto.system.RoleDto;
import com.spzx.model.entity.admin.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: SysRoleMapper
 * @author: yck
 * @create: 2024-02-18
 */

@Mapper
public interface RoleMapper {
    List<Role> findByPage(RoleDto roleDto);

    List<Role> findAllRoles();

    void saveSysRole(Role role);

    void updateSysRole(Role role);

    void deleteSysRole(Long roleId);
}
