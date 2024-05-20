package com.yingtao.ytzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.manager.mapper.CategoryBrandMapper;
import com.yingtao.ytzx.manager.service.CategoryBrandService;
import com.yingtao.ytzx.model.dto.product.CategoryBrandDto;
import com.yingtao.ytzx.model.entity.product.Brand;
import com.yingtao.ytzx.model.entity.product.CategoryBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-20 20:33
 */
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page, limit);
        List<CategoryBrand> categoryBrandList = categoryBrandMapper.findByPage(categoryBrandDto);
        return new PageInfo<>(categoryBrandList);
    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public void updateById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateById(categoryBrand);
    }

    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}
