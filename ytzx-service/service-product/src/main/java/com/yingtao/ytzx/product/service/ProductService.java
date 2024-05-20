package com.yingtao.ytzx.product.service;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.h5.ProductSkuDto;
import com.yingtao.ytzx.model.dto.product.SkuSaleDto;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import com.yingtao.ytzx.model.vo.h5.ProductItemVo;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-25 20:00
 */
public interface ProductService {
    List<ProductSku> findHotSku();

    PageInfo<ProductSku> findPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);

    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}
