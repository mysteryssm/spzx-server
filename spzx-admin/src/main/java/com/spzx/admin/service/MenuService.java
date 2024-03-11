package com.spzx.admin.service;

import com.spzx.model.dto.system.MenuDto;
import com.spzx.model.entity.admin.Menu;
import com.spzx.model.vo.system.SysMenuVo;

import java.util.List;
import java.util.Map;

/**
 * @description: SysMenuService
 * @author: yck
 * @create: 2024-02-21
 */

public interface MenuService {

    List<Menu> selectAll();

    Map<String, Object> selectAll(Long roleId);

    void insert(MenuDto menuDto);

    void updateParentMenuIsHalf(Long parentId);

    void update(MenuDto menuDto);

    void delete(Long id);

    List<SysMenuVo> findMenusByUserId(Long userId);
}
