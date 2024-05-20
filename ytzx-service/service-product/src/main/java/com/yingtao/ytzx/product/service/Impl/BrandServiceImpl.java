package com.yingtao.ytzx.product.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yingtao.ytzx.model.entity.product.Brand;
import com.yingtao.ytzx.product.mapper.BrandMapper;
import com.yingtao.ytzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-26 21:44
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Cacheable(value = "brandList", unless="#result.size() == 0")
    @Override
    public List<Brand> findAll() {
        QueryWrapper<Brand> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", 0).orderByDesc("id");
        List<Brand> brandList = brandMapper.selectList(wrapper);
        return brandList;
    }
}
