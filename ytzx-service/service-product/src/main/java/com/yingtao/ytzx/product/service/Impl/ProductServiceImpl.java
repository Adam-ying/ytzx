package com.yingtao.ytzx.product.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.h5.ProductSkuDto;
import com.yingtao.ytzx.model.dto.product.SkuSaleDto;
import com.yingtao.ytzx.model.entity.product.Product;
import com.yingtao.ytzx.model.entity.product.ProductDetails;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import com.yingtao.ytzx.model.vo.h5.ProductItemVo;
import com.yingtao.ytzx.product.mapper.ProductDetailsMapper;
import com.yingtao.ytzx.product.mapper.ProductMapper;
import com.yingtao.ytzx.product.mapper.ProductSkuMapper;
import com.yingtao.ytzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Adam
 * @create 2024-04-25 20:00
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public List<ProductSku> findHotSku() {
        List<ProductSku> productSkuList = productSkuMapper.findHotSku();
        return productSkuList;
    }

    @Override
    public PageInfo<ProductSku> findPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page, limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(productSkuList);
    }

    @Override
    public ProductItemVo item(Long skuId) {

        ProductSku productSku = productSkuMapper.selectById(skuId);

        Product product = productMapper.selectById(productSku.getProductId());

        QueryWrapper<ProductDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id", product.getId());
        ProductDetails productDetails = productDetailsMapper.selectOne(wrapper);

        QueryWrapper<ProductSku> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("product_id", product.getId());
        List<ProductSku> productSkuList = productSkuMapper.selectList(wrapper1);
        Map<String, Object> skuSpecValueMap = new HashMap<>();
        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });

        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        return productItemVo;
    }

    @Override
    public ProductSku getBySkuId(Long skuId) {
        ProductSku productSku = productSkuMapper.selectById(skuId);
        return productSku;
    }

    @Override
    public Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList) {
        skuSaleDtoList.stream().forEach(skuSaleDto -> {
            ProductSku productSku = productSkuMapper.selectById(skuSaleDto.getSkuId());
            productSku.setSaleNum(productSku.getSaleNum() + skuSaleDto.getNum());
            productSkuMapper.updateById(productSku);
        });
        return true;
    }
}
