package com.yingtao.ytzx.product.controller;

import com.yingtao.ytzx.model.entity.product.Category;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-25 20:37
 */
@RestController
@RequestMapping("/api/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("findCategoryTree")
    public Result findCategoryTree(){
        List<Category> categoryList = categoryService.findCategoryTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
