package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-16 21:33
 */
@Mapper
public interface SysMenuMapper {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void removeById(Long id);

    int countByParentId(Long id);

    List<SysMenu> selectListByUserId(Long userId);

    SysMenu selectById(Long parentId);

    void updateSysRoleMenuIsHalf(Long menuId);
}
