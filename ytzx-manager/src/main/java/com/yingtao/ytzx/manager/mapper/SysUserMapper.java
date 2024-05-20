package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.dto.system.SysUserDto;
import com.yingtao.ytzx.model.entity.system.SysRole;
import com.yingtao.ytzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-13 19:32
 */
@Mapper
public interface SysUserMapper {
    SysUser selectByUserName(String userName);

    List<SysRole> findByPage(SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);
}
