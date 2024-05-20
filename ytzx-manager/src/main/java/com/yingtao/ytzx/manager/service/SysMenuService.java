package com.yingtao.ytzx.manager.service;

import com.yingtao.ytzx.model.entity.system.SysMenu;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-16 21:32
 */
public interface SysMenuService {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void removeById(Long id);
}
