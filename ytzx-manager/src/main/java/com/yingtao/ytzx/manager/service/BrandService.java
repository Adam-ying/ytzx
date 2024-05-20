package com.yingtao.ytzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.entity.product.Brand;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 20:43
 */
public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Long id);

    List<Brand> findAll();
}
