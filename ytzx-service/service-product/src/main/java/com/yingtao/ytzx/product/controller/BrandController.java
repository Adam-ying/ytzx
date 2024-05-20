package com.yingtao.ytzx.product.controller;

import com.yingtao.ytzx.model.entity.product.Brand;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-26 21:42
 */
@RestController
@RequestMapping(value = "/api/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("findAll")
    public Result findAll(){
        List<Brand> brandList = brandService.findAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
