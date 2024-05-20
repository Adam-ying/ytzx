package com.yingtao.ytzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.manager.service.ProductSpecService;
import com.yingtao.ytzx.model.entity.product.ProductSpec;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-21 19:23
 */
@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;


    @Operation(summary = "列表查询")
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer limit){
        PageInfo<ProductSpec> productSpecPageInfo = productSpecService.findByPage(page, limit);
        return Result.build(productSpecPageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("save")
    public Result save(@RequestBody ProductSpec productSpec){
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PutMapping("updateById")
    public Result updateById(@RequestBody ProductSpec productSpec){
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("findAll")
    public Result findAll(){
        List<ProductSpec> productSpecList = productSpecService.findAll();
        return Result.build(productSpecList, ResultCodeEnum.SUCCESS);
    }
}
