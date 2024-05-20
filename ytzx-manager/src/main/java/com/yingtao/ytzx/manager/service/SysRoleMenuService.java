package com.yingtao.ytzx.manager.service;

import com.yingtao.ytzx.model.dto.system.AssginMenuDto;

import java.util.Map;

/**
 * @author Adam
 * @create 2024-04-17 19:38
 */
public interface SysRoleMenuService {
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);
}
