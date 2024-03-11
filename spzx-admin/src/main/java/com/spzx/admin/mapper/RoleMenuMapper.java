package com.spzx.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @description: SysRoleMenuMapper
 * @author: yck
 * @create: 2024-02-24
 */

@Mapper
public interface RoleMenuMapper {

    List<Long> findAllNodes(Long roleId);

    void deleteMenuByRoleId(Long roleId);

    void assignMenu(Long roleId, Map<String, Number> map);
}
