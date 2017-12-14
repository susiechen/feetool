package com.ppdai.TestCases;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Interface.entity.ExpectResponse;
import com.Interface.service.Assertion;
import com.Interface.service.ExcelDataReader;
import com.Interface.service.HttpRequestGen;
import com.Interface.service.Service;
import com.Interface.service.SqlDataReader;




public class FirstCase {
    private ExcelDataReader excelDataReader;
    private HttpRequestGen httpRequestGen;
    private SqlDataReader sqlDataReader;
    

    @BeforeClass(alwaysRun = true)
    public void classSetup() {
        System.out.println("classSetup!!!!!!!!!!!");
        excelDataReader = Service.getInstance().getExcelDataReader();
        sqlDataReader = Service.getInstance().getSqlDataReader();
        httpRequestGen = new HttpRequestGen();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        // 保存excel
        excelDataReader.saveExcel();
        System.out.println("Save Excel Success");
    }



	@Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
	public void QueryAccountBalance(String ID, String caseName) {
		System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
		String response = httpRequestGen.runCase(ID);
		ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
		Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
	}

	@Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
	public void CreateAccount(String ID, String caseName) {
		System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
		String response = httpRequestGen.runCase(ID);
		ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
		Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
	}

    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void QueryAccountInfoByAccountId(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }


    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void QueryUserAccountInfo(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }

    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void CreateAccountTemplate(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }

    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void QueryAccountTemplate(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }


    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void CreateAccountByTemplate(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }

    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void AddOneAccountTransactionLog(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }

    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void QueryAccountTransactionLogById(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }

//    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.Test.TestData.class, groups = {"offline"})
//    public void TestApi(String ID, String caseName) {
//        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
//        String response = httpRequestGen.runCase(ID);
//        System.out.println(response);
//        String sqlResult = sqlDataReader.queryForString("select p.user_id as id from fc_account p where p.id = 2");
//        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
//        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
//    }

}