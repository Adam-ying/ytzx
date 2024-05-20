package com.yingtao.ytzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.system.LoginDto;
import com.yingtao.ytzx.model.dto.system.SysUserDto;
import com.yingtao.ytzx.model.entity.system.SysRole;
import com.yingtao.ytzx.model.entity.system.SysUser;
import com.yingtao.ytzx.model.vo.system.LoginVo;
import com.yingtao.ytzx.model.vo.system.SysMenuVo;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-13 19:25
 */
public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysRole> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);

    List<SysMenuVo> findUserMenuList();
}
