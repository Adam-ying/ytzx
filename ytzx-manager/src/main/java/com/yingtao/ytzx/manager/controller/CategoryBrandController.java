package com.yingtao.ytzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.yingtao.ytzx.manager.service.CategoryBrandService;
import com.yingtao.ytzx.manager.service.CategoryService;
import com.yingtao.ytzx.model.dto.product.CategoryBrandDto;
import com.yingtao.ytzx.model.entity.product.Brand;
import com.yingtao.ytzx.model.entity.product.Category;
import com.yingtao.ytzx.model.entity.product.CategoryBrand;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-20 20:30
 */
@RestController
@RequestMapping("/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    private CategoryBrandService categoryBrandService;

    @Operation(summary = "分类品牌")
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer limit,
                             @RequestBody CategoryBrandDto categoryBrandDto){
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page, limit, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加功能")
    @PostMapping("save")
    public Result save(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改功能")
    @PutMapping("updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "删除功能")
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        categoryBrandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId){
        List<Brand> brandList = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
