package com.yingtao.ytzx.manager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.yingtao.ytzx.common.exception.YtException;
import com.yingtao.ytzx.manager.listener.ExcelListener;
import com.yingtao.ytzx.manager.mapper.CategoryMapper;
import com.yingtao.ytzx.manager.service.CategoryService;
import com.yingtao.ytzx.model.entity.product.Category;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import com.yingtao.ytzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 19:26
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findByParentId(Long parentId) {
        List<Category> categoryList = categoryMapper.findByParentId(parentId);

        if(!CollectionUtil.isEmpty(categoryList)){
            categoryList.forEach(item ->{
                int count = categoryMapper.countByParentId(item.getId());
                if(count > 0){
                    item.setHasChildren(true);
                }else{
                    item.setHasChildren(false);
                }
            });
        }
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            List<Category> categoryList = categoryMapper.selectAll();
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());

            categoryList.forEach(item -> {
                categoryExcelVoList.add(BeanUtil.copyProperties(item, CategoryExcelVo.class));
            });

            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(categoryExcelVoList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void importData(MultipartFile multipartFile) {
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
        try{
            EasyExcel.read(multipartFile.getInputStream(), CategoryExcelVo.class, excelListener).sheet().doRead();
        }catch (Exception e){
            throw new YtException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
