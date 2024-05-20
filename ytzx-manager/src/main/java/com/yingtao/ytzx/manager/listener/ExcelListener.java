package com.yingtao.ytzx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.yingtao.ytzx.manager.mapper.CategoryMapper;
import com.yingtao.ytzx.model.vo.product.CategoryExcelVo;

import javax.swing.*;
import java.util.List;

/**
 * @author Adam
 * @create 2024-04-18 20:21
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {

    private static final int BATCH_COUNT = 100;

    private List cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private CategoryMapper categoryMapper;
    public ExcelListener(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }


    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        CategoryExcelVo excelVo = (CategoryExcelVo) t;
        cacheDataList.add(excelVo);
        if(cacheDataList.size() >= BATCH_COUNT){
            saveData();
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    private void saveData(){
        categoryMapper.saveData(cacheDataList);
    }
}
