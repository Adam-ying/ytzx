package com.yingtao.ytzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.manager.service.ProductService;
import com.yingtao.ytzx.model.entity.product.Product;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adam
 * @create 2024-04-21 20:07
 */
@RestController
@RequestMapping("/admin/product/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer limit){
        PageInfo<Product> productPageInfo = productService.findByPage(page, limit);
        return Result.build(productPageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("save")
    public Result save(@RequestBody Product product){
        productService.save(product);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id){
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Product product){
        productService.updateById(product);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        productService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result updateAuditStatus(@PathVariable Long id,
                                    @PathVariable Integer auditStatus){
        productService.updateAuditStatus(id, auditStatus);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,
                               @PathVariable Integer status){
        productService.updateStatus(id, status);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
