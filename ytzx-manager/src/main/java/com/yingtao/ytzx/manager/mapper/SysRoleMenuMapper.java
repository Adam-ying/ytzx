package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-17 19:39
 */
@Mapper
public interface SysRoleMenuMapper {
    List<Long> findSysRoleMenuByRoleId(Long roleId);

    void deletedByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);
}
