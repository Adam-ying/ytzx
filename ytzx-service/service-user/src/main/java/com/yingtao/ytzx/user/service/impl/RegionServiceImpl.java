package com.yingtao.ytzx.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yingtao.ytzx.model.entity.base.Region;
import com.yingtao.ytzx.user.mapper.UserRegionMapper;
import com.yingtao.ytzx.user.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Adam
 * @create 2024-05-15 21:20
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private UserRegionMapper userRegionMapper;

    @Override
    public List<Region> findByParentCode(String parentCode) {
        List<Region> regionList = userRegionMapper.
                selectList(new QueryWrapper<Region>().eq("code", parentCode));
        return regionList;
    }
}
