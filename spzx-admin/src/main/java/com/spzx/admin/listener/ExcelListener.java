package com.spzx.admin.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.spzx.admin.mapper.CategoryMapper;
import com.spzx.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 * @description: ExcelListener
 * @author: yck
 * @create: 2024-02-26
 */

public class ExcelListener<T> implements ReadListener<T> {

    private static final int BATCH_COUNT = 100; // 每隔100条存储数据库，然后清理list ，方便内存回收
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);   //生成初始容量为 100 的 List
    private CategoryMapper categoryMapper;

    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        cachedDataList.add(t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);   // 清空 List
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    private void saveData() {
        categoryMapper.batchInsert((List<CategoryExcelVo>) cachedDataList);
    }
}
