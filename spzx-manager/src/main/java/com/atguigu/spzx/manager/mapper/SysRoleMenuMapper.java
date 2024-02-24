package com.atguigu.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @description: SysRoleMenuMapper
 * @author: yck
 * @create: 2024-02-24
 */

@Mapper
public interface SysRoleMenuMapper {

    List<Long> findAllNodes(Long roleId);

    void deleteMenuByRoleId(Long roleId);

    void assignMenu(Long roleId, Map<String, Number> map);
}
