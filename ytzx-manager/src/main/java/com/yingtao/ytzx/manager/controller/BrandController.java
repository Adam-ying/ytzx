package com.yingtao.ytzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.manager.service.BrandService;
import com.yingtao.ytzx.model.entity.product.Brand;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 20:42
 */
@RequestMapping(value = "/admin/product/brand")
@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "列表查询")
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer limit){
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌增加")
    @PostMapping("save")
    public Result save(@RequestBody Brand brand){
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌修改")
    @PutMapping("updateById")
    public Result updateById(@RequestBody Brand brand){
        brandService.updateById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "品牌删除")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        brandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "查询所有品牌")
    @GetMapping("findAll")
    public Result findAll(){
        List<Brand> brandList = brandService.findAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
