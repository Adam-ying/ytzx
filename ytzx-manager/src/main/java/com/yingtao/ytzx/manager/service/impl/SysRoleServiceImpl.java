package com.yingtao.ytzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.common.log.annotation.Log;
import com.yingtao.ytzx.manager.mapper.SysRoleMapper;
import com.yingtao.ytzx.manager.mapper.SysRoleUserMapper;
import com.yingtao.ytzx.manager.service.SysRoleService;
import com.yingtao.ytzx.model.dto.system.AssginRoleDto;
import com.yingtao.ytzx.model.dto.system.SysRoleDto;
import com.yingtao.ytzx.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adam
 * @create 2024-04-14 20:45
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public PageInfo<SysRole> findByPage(Integer pageNum, Integer pageSize, SysRoleDto sysRoleDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo pageInfo = new PageInfo(sysRoleList);
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles();

        List<Long> sysRoleIds = sysRoleUserMapper.findSysUserRoleByUserId(userId);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sysUserRoles", sysRoleIds);
        hashMap.put("allRolesList", sysRoleList);
        return hashMap;
    }

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        Long userId = assginRoleDto.getUserId();

        sysRoleUserMapper.deleteByUserId(userId);

        for(Long roleId: assginRoleDto.getRoleIdList()){
            sysRoleUserMapper.doAssign(userId, roleId);
        }
    }
}
