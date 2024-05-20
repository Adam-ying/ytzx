package com.yingtao.ytzx.manager.controller;

import com.yingtao.ytzx.manager.service.ProductUnitService;
import com.yingtao.ytzx.model.entity.base.ProductUnit;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-22 19:25
 */
@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {
    @Autowired
    private ProductUnitService productUnitService;

    @GetMapping("findAll")
    public Result findAll(){
        List<ProductUnit> productUnitList = productUnitService.findAll();
        return Result.build(productUnitList, ResultCodeEnum.SUCCESS);
    }
}
