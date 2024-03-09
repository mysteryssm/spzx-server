package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @description: sysMenuMapper
 * @author: yck
 * @create: 2024-02-21
 */

@Mapper
public interface SysMenuMapper {

    List<SysMenu> findAllNodes();

    void save(SysMenuDto sysMenuDto);

    void update(SysMenuDto sysMenuDto);

    void delete(Long id);

    int countChildren(Long id);

    List<SysMenu> findMenusByUserId(Long userId);

    void updateParentMenuIsHalf(Long parentId);

    Long findParentMenuId(Long parentId);
}
