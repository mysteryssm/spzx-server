package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.utils.MenuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: SysMenuServiceImpl
 * @author: yck
 * @create: 2024-02-21
 */

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

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
}
