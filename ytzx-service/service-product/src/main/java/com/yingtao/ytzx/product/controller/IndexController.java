package com.yingtao.ytzx.product.controller;

import com.yingtao.ytzx.model.entity.product.Category;
import com.yingtao.ytzx.model.entity.product.Product;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.h5.IndexVo;
import com.yingtao.ytzx.product.service.CategoryService;
import com.yingtao.ytzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-25 19:47
 */
@RestController
@RequestMapping(value="/api/product/index")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result findIndexData(){
        IndexVo indexVo = new IndexVo();
        List<Category> categoryList = categoryService.findOneCagegory();
        List<ProductSku> productSkuList = productService.findHotSku();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }
}
