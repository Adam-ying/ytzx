package com.yingtao.ytzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.manager.mapper.ProductSpecMapper;
import com.yingtao.ytzx.manager.service.ProductSpecService;
import com.yingtao.ytzx.model.entity.product.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-21 19:24
 */
@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProductSpec> productSpecList = productSpecMapper.findByPage();
        return new PageInfo<>(productSpecList);
    }

    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    @Override
    public void deleteById(Long id) {
        productSpecMapper.deleteById(id);
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }
}
