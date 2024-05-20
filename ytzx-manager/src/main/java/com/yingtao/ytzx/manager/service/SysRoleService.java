package com.yingtao.ytzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.system.AssginRoleDto;
import com.yingtao.ytzx.model.dto.system.SysRoleDto;
import com.yingtao.ytzx.model.entity.system.SysRole;

import java.util.Map;

/**
 * @author Adam
 * @create 2024-04-14 20:44
 */
public interface SysRoleService {
    PageInfo<SysRole> findByPage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    Map<String, Object> findAllRoles(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);
}
