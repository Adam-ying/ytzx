package com.yingtao.ytzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.common.exception.YtException;
import com.yingtao.ytzx.manager.mapper.ProductDetailsMapper;
import com.yingtao.ytzx.manager.mapper.ProductMapper;
import com.yingtao.ytzx.manager.mapper.ProductSkuMapper;
import com.yingtao.ytzx.manager.service.ProductService;
import com.yingtao.ytzx.model.entity.product.Product;
import com.yingtao.ytzx.model.entity.product.ProductDetails;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-21 20:08
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Product> productList = productMapper.findByPage();
        return new PageInfo<>(productList);
    }
    
    @Transactional
    @Override
    public void save(Product product) {
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);

        List<ProductSku> productSkuList = product.getProductSkuList();
        for(int i =0, size = productSkuList.size(); i < size; i++){
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "_" + i);
            productSku.setProductId(product.getId());
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            productSkuMapper.save(productSku);
        }

        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(productDetails.getImageUrls());
        productDetailsMapper.save(productDetails);
    }

    @Override
    public Product getById(Long id) {
        Product product = productMapper.getById(id);

        List<ProductSku> productSkuList = productSkuMapper.getByProductId(id);
        product.setProductSkuList(productSkuList);

        ProductDetails productDetails = productDetailsMapper.getByProductId(id);
        product.setDetailsImageUrls(productDetails.getImageUrls());

        return product;
    }

    @Transactional
    @Override
    public void updateById(Product product) {
        productMapper.updateById(product);
        
        for(ProductSku productSku: product.getProductSkuList()){
            productSkuMapper.updateById(productSku);
        }

        ProductDetails byProductId = productDetailsMapper.getByProductId(product.getId());
        byProductId.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.updateById(byProductId);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);
        productSkuMapper.deleteByProductId(id);
        productDetailsMapper.deleteByProductId(id);
    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if(auditStatus == 1){
            product.setAuditStatus(auditStatus);
            product.setAuditMessage("审批通过");
        }else if(auditStatus == -1){
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }else{
            throw new YtException(ResultCodeEnum.DATA_ERROR);
        }
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1){
            product.setStatus(1);
        }else{
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}
