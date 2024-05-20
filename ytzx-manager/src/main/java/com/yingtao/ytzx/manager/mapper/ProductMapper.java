package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-21 20:08
 */
@Mapper
public interface ProductMapper {
    List<Product> findByPage();

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);

}
