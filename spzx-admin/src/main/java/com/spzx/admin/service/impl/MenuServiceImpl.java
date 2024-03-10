package com.spzx.admin.service.impl;

import com.atguigu.spzx.common.exception.GlobalException;
import com.spzx.admin.mapper.SysMenuMapper;
import com.spzx.admin.mapper.SysRoleMenuMapper;
import com.spzx.admin.service.MenuService;
import com.atguigu.spzx.model.dto.system.SysMenuDto;
import com.atguigu.spzx.model.entity.admin.SysMenu;
import com.atguigu.spzx.model.globalEnum.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.MenuUtil;
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
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> selectAll() {
        List<SysMenu> sysMenuList = sysMenuMapper.findAllNodes();   //其中的 SysMenu 对象没有 children 字段的数据
        if(sysMenuList != null) {
            List<SysMenu> sysMenuTree = MenuUtil.buildTree(sysMenuList);   // 手动加入节点信息，生成菜单树
            return sysMenuTree;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> selectAll(Long roleId) {
        Map<String, Object> map = new HashMap<>();
        List<SysMenu> allMenus = selectAll();
        List<Long> roleMenus = sysRoleMenuMapper.findAllNodes(roleId);

        map.put("sysMenuList", allMenus);
        map.put("roleMenuIds", roleMenus);
        return map;
    }

    @Override
    public void insert(SysMenuDto sysMenuDto) {
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
        List<SysMenu> listTree = MenuUtil.buildTree(list);
        List<SysMenuVo> menuVoList = MenuUtil.menuList2MenuVoList(listTree);

        return menuVoList;
    }
}
