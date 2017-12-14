package com.Interface.TestData;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.testng.annotations.DataProvider;

import com.Interface.service.ExcelDataReader;
import com.Interface.service.Service;

public class TestData {
    private static ExcelDataReader excelDataReader = Service.getInstance().getExcelDataReader();

    @DataProvider(name = "creatDataList")
    public static Iterator<Object[]> creatDataList(Method method) {
        return excelDataReader.getTestData(method.getName(), true);
    }

    @DataProvider(name = "creatDataSingle")
    public static Iterator<Object[]> creatDataSingle(Method method) {
        return excelDataReader.getTestData(method.getName(), false);
    }
}
