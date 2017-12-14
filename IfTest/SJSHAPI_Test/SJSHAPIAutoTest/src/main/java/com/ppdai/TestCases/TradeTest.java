package com.ppdai.TestCases;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Interface.entity.ExpectResponse;
import com.Interface.entity.MapType;
import com.Interface.service.Assertion;
import com.Interface.service.ExcelDataReader;
import com.Interface.service.HttpRequestGen;
import com.Interface.service.Service;
import com.Interface.service.SqlDataReader;
import com.Interface.utils.RegexHelper;




public class TradeTest {
    private ExcelDataReader excelDataReader;
    private HttpRequestGen httpRequestGen;
    private SqlDataReader sqlDataReader;
	//存储要使用的case的结果
    private Map<String,String> hashMap=new HashMap<String,String>();
    
    
    @BeforeClass(alwaysRun = true)
    public void classSetup() {
        System.out.println("TestSetup!!!!!!!!!!!");
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


    /**
     * 贷前审核接口
     * @param ID
     * @param caseName
     */
	@Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
	public void auditTest(String ID, String caseName) {
		System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
		String response = httpRequestGen.runCase(ID);
		System.out.println(response);
		ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
		Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
	}


	/**
	 * 发标接口
	 * @param ID
	 * @param caseName
	 */
    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void tradeTest(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        System.out.println(expectResponse.getContant());
        //获取返回值，把caseName,与对应的response
        hashMap.put(caseName, response);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }

    /**
     * 查询借款记录
     * @param ID
     * @param caseName
     */
    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void subscriptionTest(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }
    
    
    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void unrepaymentPlanTest(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }
    
    
    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void unrepaymentTest(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }
    
    
    
    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void currentTest(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        String response = httpRequestGen.runCase(ID);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }
    
    /**
     * 撤标接口
     * @param ID
     * @param caseName
     */
    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"})
    public void cancelTest(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        
        Map<String, String> paraMap =new HashMap<String, String>();
        paraMap.put("tradeId", "912201567492509696");
        String response = httpRequestGen.runCase(ID,paraMap,MapType.Head);
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }
    
    
    /**
     * 满标接口
     * @param ID
     * @param caseName
     */
    @Test(dataProvider = "creatDataList", dataProviderClass = com.Interface.TestData.TestData.class, groups = {"offline"},dependsOnMethods={"tradeTest"})
    public void fullTest(String ID, String caseName) {
        System.out.println("测试用例:ID=" + ID + "&&caseName=" + caseName);
        //获取发标接口返回的orderId
         String  tradeResponse = hashMap.get("tradeTest");
        //用正则提取
        RegexHelper regexHelper=new RegexHelper();
        String str = regexHelper.getFirstMatch("\"tradeId\":\"(.*?)\"", tradeResponse);
        System.out.println("content:"+tradeResponse);
        String[] data = str.split(":");
        Map<String, String> paraMap =new HashMap<String, String>();
        if(data.length >= 2){
        	//需要去掉双引号
        	paraMap.put(data[0].replaceAll("\"", ""), data[1].replaceAll("\"", ""));
        }
        //
        paraMap.put("listingId", paraMap.get("tradeId"));
        //执行
        String response =httpRequestGen.runCase(ID, paraMap, MapType.Body);
        
        ExpectResponse expectResponse = httpRequestGen.getExpectResult(caseName);
        Assertion.autoAssert(caseName, response, expectResponse.getContant(), expectResponse.getAssertType());
    }





}