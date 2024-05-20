package com.yingtao.ytzx.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yingtao.ytzx.model.dto.h5.ProductSkuDto;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-25 20:01
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {
    List<ProductSku> findHotSku();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);
}
