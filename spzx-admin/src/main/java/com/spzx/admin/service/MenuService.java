package com.spzx.admin.service;

import com.atguigu.spzx.model.dto.system.SysMenuDto;
import com.atguigu.spzx.model.entity.admin.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;

import java.util.List;
import java.util.Map;

/**
 * @description: SysMenuService
 * @author: yck
 * @create: 2024-02-21
 */

public interface MenuService {

    List<SysMenu> selectAll();

    Map<String, Object> selectAll(Long roleId);

    void insert(SysMenuDto sysMenuDto);

    void updateParentMenuIsHalf(Long parentId);

    void update(SysMenuDto sysMenuDto);

    void delete(Long id);

    List<SysMenuVo> findMenusByUserId(Long userId);
}
