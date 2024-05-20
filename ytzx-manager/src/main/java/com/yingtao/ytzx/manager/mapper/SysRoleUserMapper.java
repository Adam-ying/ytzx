package com.yingtao.ytzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-16 20:00
 */
@Mapper
public interface SysRoleUserMapper {
    List<Long> findSysUserRoleByUserId(Long userId);

    void doAssign(Long userId, Long roleId);

    void deleteByUserId(Long userId);
}
