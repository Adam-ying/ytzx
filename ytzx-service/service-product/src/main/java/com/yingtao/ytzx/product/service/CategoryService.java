package com.yingtao.ytzx.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yingtao.ytzx.model.entity.product.Category;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-25 19:50
 */
public interface CategoryService {
    List<Category> findOneCagegory();

    List<Category> findCategoryTree();
}
