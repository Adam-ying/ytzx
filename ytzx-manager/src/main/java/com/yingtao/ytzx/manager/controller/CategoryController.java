package com.yingtao.ytzx.manager.controller;

import com.yingtao.ytzx.manager.service.CategoryService;
import com.yingtao.ytzx.model.entity.product.Category;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 19:24
 */
@RestController
@RequestMapping(value = "/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "根据parentId获取下级节点")
    @GetMapping(value = "/findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Long parentId){
        List<Category> categoryList = categoryService.findByParentId(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "导出数据功能")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        categoryService.exportData(response);
    }

    @Operation(summary = "导入数据功能")
    @PostMapping("importData")
    public Result importData(MultipartFile multipartFile){
        categoryService.importData(multipartFile);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
