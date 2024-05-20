package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-22 20:05
 */
@Mapper
public interface ProductSkuMapper {
    void save(ProductSku productSku);

    List<ProductSku> getByProductId(Long id);

    void updateById(ProductSku productSku);

    void deleteByProductId(Long id);
}
