package com.yingtao.ytzx.manager.service.impl;

import com.yingtao.ytzx.common.exception.YtException;
import com.yingtao.ytzx.manager.helper.MenuHelper;
import com.yingtao.ytzx.manager.mapper.SysMenuMapper;
import com.yingtao.ytzx.manager.service.SysMenuService;
import com.yingtao.ytzx.model.entity.system.SysMenu;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam
 * @create 2024-04-16 21:32
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenuList = sysMenuMapper.findNodes();
        List<SysMenu> resMenuList = new ArrayList<>();
        for(SysMenu sysMenu: sysMenuList){
            if(sysMenu.getId() == 0){
                if (sysMenu.getChildren() == null) {
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(MenuHelper.buildTree(sysMenu, sysMenuList));
                resMenuList.add(sysMenu);
            }
        }
        return resMenuList;
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);

        updateSysRoleMenuIsHalf(sysMenu);
    }

    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {
        Long parentId = sysMenu.getParentId();
        SysMenu sysMenuParent = sysMenuMapper.selectById(parentId);
        if(sysMenuParent != null){
            sysMenuMapper.updateSysRoleMenuIsHalf(sysMenuParent.getId());
            updateSysRoleMenuIsHalf(sysMenuParent);
        }
    }

    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        int count = sysMenuMapper.countByParentId(id);
        if(count > 0){
            throw  new YtException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.removeById(id);
    }
}
