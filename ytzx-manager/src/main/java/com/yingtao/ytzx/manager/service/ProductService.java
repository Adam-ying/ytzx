package com.yingtao.ytzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.entity.product.Product;

/**
 * @author Adam
 * @create 2024-04-21 20:08
 */
public interface ProductService {
    PageInfo<Product> findByPage(Integer page, Integer limit);

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);
}
