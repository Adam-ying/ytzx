package com.yingtao.ytzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.product.CategoryBrandDto;
import com.yingtao.ytzx.model.entity.product.Brand;
import com.yingtao.ytzx.model.entity.product.CategoryBrand;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-20 20:33
 */
public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
