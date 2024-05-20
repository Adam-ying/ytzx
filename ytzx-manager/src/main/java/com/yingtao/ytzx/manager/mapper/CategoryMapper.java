package com.yingtao.ytzx.manager.mapper;

import com.yingtao.ytzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 19:27
 */
@Mapper
public interface CategoryMapper {
    List<Category> findByParentId(Long parentId);

    int countByParentId(Long id);

    List<Category> selectAll();

    void saveData(List cacheDataList);
}
