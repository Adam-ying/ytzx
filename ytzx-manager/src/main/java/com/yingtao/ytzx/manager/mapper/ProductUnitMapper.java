package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-22 19:26
 */
@Mapper
public interface ProductUnitMapper {
    List<ProductUnit> findAll();
}
