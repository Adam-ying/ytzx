package com.yingtao.ytzx.product.controller;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.model.dto.h5.ProductSkuDto;
import com.yingtao.ytzx.model.dto.product.SkuSaleDto;
import com.yingtao.ytzx.model.entity.product.ProductSku;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.h5.ProductItemVo;
import com.yingtao.ytzx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-25 21:08
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("{page}/{limit}")
    public Result findPage(@PathVariable Integer page,
                           @PathVariable Integer limit,
                           ProductSkuDto productSkuDto){
        PageInfo<ProductSku> pageInfo = productService.findPage(page, limit, productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("item/{skuId}")
    public Result item(@PathVariable Long skuId){
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId){
        return productService.getBySkuId(skuId);
    }

    @PostMapping("updateSkuSaleNum")
    public Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList){
        return productService.updateSkuSaleNum(skuSaleDtoList);
    }

}
