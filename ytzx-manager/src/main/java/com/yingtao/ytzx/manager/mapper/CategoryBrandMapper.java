package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.dto.product.CategoryBrandDto;
import com.yingtao.ytzx.model.entity.product.Brand;
import com.yingtao.ytzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-20 20:33
 */
@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
