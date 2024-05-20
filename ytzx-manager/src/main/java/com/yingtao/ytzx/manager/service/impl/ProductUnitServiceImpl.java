package com.yingtao.ytzx.manager.service.impl;

import com.yingtao.ytzx.manager.mapper.ProductUnitMapper;
import com.yingtao.ytzx.manager.service.ProductUnitService;
import com.yingtao.ytzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-22 19:25
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper;

    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll();
    }
}
