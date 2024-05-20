package com.yingtao.ytzx.manager.helper;

import com.yingtao.ytzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam
 * @create 2024-04-16 22:43
 */
public class MenuHelper {
    public static SysMenu buildTree(SysMenu sysMenu, List<SysMenu> sysMenuList){
        for(SysMenu sysMenu1: sysMenuList){
            if(sysMenu.getId().longValue() == sysMenu1.getParentId().longValue()){
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(buildTree(sysMenu1, sysMenuList));
            }
        }
        return sysMenu;
    }
}
