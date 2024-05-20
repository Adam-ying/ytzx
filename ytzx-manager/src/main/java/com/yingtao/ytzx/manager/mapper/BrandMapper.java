package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 20:44
 */
@Mapper
public interface BrandMapper {
    List<Brand> findByPage();

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Long id);

    List<Brand> findAll();
}
