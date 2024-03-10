package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.admin.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yck
 * @create 2023-10-26-20:10
 */

public class MenuUtil {

    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> sysMenuTree = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            //递归入口条件是 Parent_id = 0
            if (sysMenu.getParentId().longValue() == 0) {
                //根据第一层去找下层数据，使用递归完成(第一个参数是当前的第一层菜单，第二个参数是所有菜单的集合)
                sysMenuTree.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return sysMenuTree;
    }

    /**
     * 递归查找子节点
     * @param sysMenuList
     * @return
     */
    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        sysMenu.setChildren(new ArrayList<SysMenu>());  //对 SysMenu 的 children 进行初始化，即清空
        for (SysMenu it : sysMenuList) {
            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
                sysMenu.getChildren().add(findChildren(it, sysMenuList));
            }
        }
        return sysMenu;
    }

    public static List<SysMenuVo> menuList2MenuVoList(List<SysMenu> list) {
        List<SysMenuVo> menuVolist = new ArrayList<>();
        for(SysMenu sysMenu : list) {
            menuVolist.add(sysMenu2sysMenuVo(sysMenu));
        }
        return menuVolist;
    }

    public static SysMenuVo sysMenu2sysMenuVo(SysMenu sysMenu) {
        SysMenuVo sysMenuVo = new SysMenuVo();
        sysMenuVo.setTitle(sysMenu.getTitle());
        sysMenuVo.setName(sysMenu.getComponent());
        if(sysMenu.getChildren() != null || sysMenu.getChildren().size() != 0) {
            sysMenuVo.setChildren(menuList2MenuVoList(sysMenu.getChildren()));
        }
        return sysMenuVo;
    }

}