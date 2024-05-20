package com.yingtao.ytzx.product.service.Impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yingtao.ytzx.model.entity.product.Category;
import com.yingtao.ytzx.product.mapper.CategoryMapper;
import com.yingtao.ytzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Adam
 * @create 2024-04-25 19:52
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<Category> findOneCagegory() {

        String categoryListJSON = redisTemplate.opsForValue().get("category:one");
        if(!StrUtil.isEmpty(categoryListJSON)){
            List<Category> categoryList = JSON.parseArray(categoryListJSON, Category.class);
            return categoryList;
        }

        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0).eq("status", 1).eq("is_deleted", 0);
        wrapper.orderByDesc("order_num");
        List<Category> categoryList = categoryMapper.selectList(wrapper);

        redisTemplate.opsForValue().set("category:one", JSON.toJSONString(categoryList), 7, TimeUnit.DAYS);
        return categoryList;
    }

    @Cacheable(value = "category", key = "'all'")
    @Override
    public List<Category> findCategoryTree() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", 0).eq("status", 1).orderByAsc("order_num");
        List<Category> categoryList = categoryMapper.selectList(wrapper);
        List<Category> finalList = new ArrayList<>();
        for(Category category: categoryList){
            if(category.getParentId().longValue() == 0){
                category.setChildren(dfs(category, categoryList););
                finalList.add(category);
            }
        }
        return finalList;
    }

    private List<Category> dfs(Category category, List<Category> categoryList) {
        List<Category> childList = new ArrayList<>();
        for(Category category1: categoryList){
            if(category1.getParentId().longValue() == category.getId().longValue()){
                category.setChildren(dfs(category1, categoryList));
                childList.add(category1);
            }
        }
        return childList;
    }
}
