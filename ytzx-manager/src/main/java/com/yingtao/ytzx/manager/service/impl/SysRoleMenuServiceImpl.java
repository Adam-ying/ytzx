package com.yingtao.ytzx.manager.service.impl;

import com.yingtao.ytzx.manager.mapper.SysMenuMapper;
import com.yingtao.ytzx.manager.mapper.SysRoleMenuMapper;
import com.yingtao.ytzx.manager.service.SysRoleMenuService;
import com.yingtao.ytzx.model.dto.system.AssginMenuDto;
import com.yingtao.ytzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adam
 * @create 2024-04-17 19:38
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        List<SysMenu> nodes = sysMenuMapper.findNodes();

        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);

        Map<String , Object> result = new HashMap<>();

        result.put("sysMenuList",nodes);
        result.put("roleMenuIds", roleMenuIds);

        return result;
    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        Long roleId = assginMenuDto.getRoleId();
        sysRoleMenuMapper.deletedByRoleId(roleId);

        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
        if(menuIdList != null && menuIdList.size() > 0){
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }
    }
}
