package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exception.GlobalException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.dto.system.SysMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.MenuUtils;
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
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findAllNodes() {
        List<SysMenu> sysMenuList = sysMenuMapper.findAllNodes();   //其中的 SysMenu 对象没有 children 字段的数据
        if(sysMenuList != null) {
            List<SysMenu> sysMenuTree = MenuUtils.buildTree(sysMenuList);   // 手动加入节点信息，生成菜单树
            return sysMenuTree;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> findAllNodes(Long roleId) {
        Map<String, Object> map = new HashMap<>();
        List<SysMenu> allMenus = findAllNodes();
        List<Long> roleMenus = sysRoleMenuMapper.findAllNodes(roleId);

        map.put("sysMenuList", allMenus);
        map.put("roleMenuIds", roleMenus);
        return map;
    }

    @Override
    public void save(SysMenuDto sysMenuDto) {
        sysMenuMapper.save(sysMenuDto);
        updateParentMenuIsHalf(sysMenuDto.getParentId());
    }

    @Override
    public void updateParentMenuIsHalf(Long parentId) {
        if(parentId.longValue() != 0) {
            sysMenuMapper.updateParentMenuIsHalf(parentId);
            if(sysMenuMapper.findParentMenuId(parentId) != 0) {
                updateParentMenuIsHalf(sysMenuMapper.findParentMenuId(parentId));
            }
        }
    }

    @Override
    public void update(SysMenuDto sysMenuDto) {
        sysMenuMapper.update(sysMenuDto);
    }

    @Override
    public void delete(Long id) {
        if(sysMenuMapper.countChildren(id) == 0) {
            sysMenuMapper.delete(id);
        } else {
            throw new GlobalException(ResultCodeEnum.MENU_DELETE_ERROR);
        }
    }

    @Override
    public List<SysMenuVo> findMenusByUserId(Long userId) {
        List<SysMenu> list = sysMenuMapper.findMenusByUserId(userId);
        List<SysMenu> listTree = MenuUtils.buildTree(list);
        List<SysMenuVo> menuVoList = MenuUtils.menuList2MenuVoList(listTree);

        return menuVoList;
    }
}
