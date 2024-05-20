package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.dto.system.SysRoleDto;
import com.yingtao.ytzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-14 20:45
 */
@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void insert(SysRole sysRole);

    void updateById(SysRole sysRole);

    void deleteById(Long roleId);

    List<SysRole> findAllRoles();


}
