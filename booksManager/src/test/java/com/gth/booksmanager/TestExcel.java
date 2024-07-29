package com.gth.booksmanager;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.gth.booksmanager.pojo.ExcelData;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestExcel implements ReadListener<ExcelData> {

    //每隔100条存储数据库，然后清理list
    private static final int BATCH_COUNT = 100;
    //缓存的数据
    private List<ExcelData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private ExcelData excelData;

//    public ExcelData Listener(ExcelData excelData) {
//        this.demoDAO = demoDAO;
//    }


    @Override
    public void invoke(ExcelData data, AnalysisContext context) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
