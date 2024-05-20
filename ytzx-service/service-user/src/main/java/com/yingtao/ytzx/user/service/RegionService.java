package com.yingtao.ytzx.user.service;

import com.yingtao.ytzx.model.entity.base.Region;

import java.util.List;

/**
 * @author Adam
 * @create 2024-05-15 21:20
 */
public interface RegionService {
    List<Region> findByParentCode(String parentCode);
}
