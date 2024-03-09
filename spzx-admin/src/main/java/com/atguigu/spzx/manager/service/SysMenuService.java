package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.dto.system.SysMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;

import java.util.List;
import java.util.Map;

/**
 * @description: SysMenuService
 * @author: yck
 * @create: 2024-02-21
 */

public interface SysMenuService {

    List<SysMenu> findAllNodes();

    Map<String, Object> findAllNodes(Long roleId);

    void save(SysMenuDto sysMenuDto);

    void updateParentMenuIsHalf(Long parentId);

    void update(SysMenuDto sysMenuDto);

    void delete(Long id);

    List<SysMenuVo> findMenusByUserId(Long userId);
}
