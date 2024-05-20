package com.yingtao.ytzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.manager.mapper.BrandMapper;
import com.yingtao.ytzx.manager.service.BrandService;
import com.yingtao.ytzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 20:43
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Brand> brandList = brandMapper.findByPage();
        return new PageInfo<>(brandList);
    }

    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    @Override
    public void updateById(Brand brand) {
        brandMapper.updateById(brand);
    }

    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findAll() {
        List<Brand> brandList = brandMapper.findAll();
        return brandList;
    }
}
