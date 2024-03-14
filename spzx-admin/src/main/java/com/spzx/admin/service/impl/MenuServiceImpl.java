package com.spzx.admin.service.impl;

import com.spzx.common.service.exception.GlobalException;
import com.spzx.model.entity.admin.Menu;
import com.spzx.admin.mapper.MenuMapper;
import com.spzx.admin.mapper.RoleMenuMapper;
import com.spzx.admin.service.MenuService;
import com.spzx.model.dto.admin.MenuDto;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.admin.MenuVo;
import com.spzx.admin.utils.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: SysMenuServiceImpl
 * @author: yck
 * @create: 2024-02-21
 */

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> selectAll() {
        List<Menu> menuList = menuMapper.findAllNodes();   //其中的 SysMenu 对象没有 children 字段的数据
        if(menuList != null) {
            List<Menu> menuTree = MenuUtil.buildTree(menuList);   // 手动加入节点信息，生成菜单树
            return menuTree;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> selectAll(Long roleId) {
        Map<String, Object> map = new HashMap<>();
        List<Menu> allMenus = selectAll();
        List<Long> roleMenus = roleMenuMapper.findAllNodes(roleId);

        map.put("sysMenuList", allMenus);
        map.put("roleMenuIds", roleMenus);
        return map;
    }

    @Override
    public void insert(MenuDto menuDto) {
        menuMapper.save(menuDto);
        updateParentMenuIsHalf(menuDto.getParentId());
    }

    @Override
    public void updateParentMenuIsHalf(Long parentId) {
        if(parentId.longValue() != 0) {
            menuMapper.updateParentMenuIsHalf(parentId);
            if(menuMapper.findParentMenuId(parentId) != 0) {
                updateParentMenuIsHalf(menuMapper.findParentMenuId(parentId));
            }
        }
    }

    @Override
    public void update(MenuDto menuDto) {
        menuMapper.update(menuDto);
    }

    @Override
    public void delete(Long id) {
        if(menuMapper.countChildren(id) == 0) {
            menuMapper.delete(id);
        } else {
            throw new GlobalException(ResultCodeEnum.MENU_DELETE_ERROR);
        }
    }

    @Override
    public List<MenuVo> findMenusByUserId(Long userId) {
        List<Menu> list = menuMapper.findMenusByUserId(userId);
        List<Menu> listTree = MenuUtil.buildTree(list);
        List<MenuVo> menuVoList = MenuUtil.menuList2MenuVoList(listTree);

        return menuVoList;
    }
}
