package com.yingtao.ytzx.manager.service;

import com.yingtao.ytzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 19:26
 */
public interface CategoryService {
    List<Category> findByParentId(Long parentId);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile multipartFile);
}
