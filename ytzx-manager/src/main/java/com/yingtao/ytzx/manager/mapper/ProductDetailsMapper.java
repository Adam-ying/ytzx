package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Adam
 * @create 2024-04-22 20:10
 */
@Mapper
public interface ProductDetailsMapper {
    void save(ProductDetails productDetails);

    ProductDetails getByProductId(Long id);

    void updateById(ProductDetails byProductId);

    void deleteByProductId(Long id);
}
