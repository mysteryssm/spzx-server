package com.spzx.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: SysRoleUserMapper
 * @author: yck
 * @create: 2024-02-20
 */

@Mapper
public interface AdministratorRoleMapper {
    List<Long> findAllRoles(Long userId);

    void assignRole(Long userId, Long roleId);

    void deleteByUserId(Long userId);
}
