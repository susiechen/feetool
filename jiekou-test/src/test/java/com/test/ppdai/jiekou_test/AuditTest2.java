package com.test.ppdai.jiekou_test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.common.JsonParseUtil;
import com.test.common.PropertiesUtil;
import com.test.constance.Constance;
import com.test.okhttp.OkHttpUtil;

public class AuditTest2 {
		//获取请求的URL
      private String url=Constance.test_host+PropertiesUtil.getProperties("test-url");
	  private String json=PropertiesUtil.getProperties("json");
      
	  /**
	   * 1.从文件中读取json格式的用例
	   * 2.因读取的信息为多个testcase，需拆分成多个case
	   * 3.执行testcase
	   */
	  public static String readTxt(String filePath){
		  StringBuffer sb = new StringBuffer();

		  try {
			  FileReader reader=new FileReader(new File(filePath));
			  char[] byt=new char[1024];
			  int len = 0;
				while( (len = reader.read(byt)) != -1){
					  sb.append(new String(byt,0,len));
				  } 			  
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
		     return sb.toString();     
		     
	  }
	  
      
	  public static void main(String[] args) {
		  String[] data =  parseStr(readTxt("C:\\newWorkSpace\\jiekou-test\\test-data\\AuditTest.txt"),";");
		  for(String testCase:data){
			  System.out.println(testCase);
		}
	  }
	  
	  
	  /**
	   * 根据字符C，拆分字符串
	   */
	  public static String[] parseStr(String str,String c){
		 return str.split(c);  	
	  }
	  
	  @Test
	  public void audit(){
			 /**
			  * 把字符串转出json格式，并获取到相应的字段信息
			  */
		 String[] data = parseStr(readTxt("C:\\newWorkSpace\\jiekou-test\\test-data\\AuditTest.txt"),";");
		 for(String testCase:data){
			  Map<String, Object> dataList = JsonParseUtil.getJsonData(testCase,"request");
			   //依次调用每个请求
			  // for(Map<String, Object> item:dataList){
			   OkHttpUtil.postJson(Constance.test_host+dataList.get("url").toString(),dataList.get("body").toString());
			   
		   }
		  
	  }
	
	  @DataProvider
	   public Object[][] getData(){
		  String[] data = parseStr(readTxt("C:\\newWorkSpace\\jiekou\\test-data\\AuditTest.txt"),";");
		  Object[][] testData = new Object[data.length][];
			 for(int i=0;i<data.length;i++){
				 
				 ObjectMapper mapper=new ObjectMapper();
				 try {
					Map map = mapper.readValue(data[i], Map.class);
					testData[i]=new Object[]{(Map)map.get("request")}; 
					
					
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}	 
			 }	 
			 return testData;
		   
	   }
	  
	  /**
	   * 根据不同的testcase调用不同的testcase
	   * @param method
	   * @return
	   */
	  @DataProvider(name="test2")
	    public Object[][] providerMethod(Method method){        
	        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
	        String[] data = parseStr(readTxt("C:\\newWorkSpace\\jiekou\\test-data\\AuditTest.txt"),";");
	        
	        for (int i = 0; i < data.length; i++) {
	        	//把testcase转换为map格式
	        	Map m = (Map) JsonParseUtil.getData1(data[i]);
	           // if(m.containsKey(method.getName()))
	            if(m.get("name").equals(method.getName()))
	            {                            
	                //Map<String, String> dm = (Map<String, String>) m.get(method.getName());
	                Map<String,String> dm=(Map<String, String>)m.get("request");
	                result.add(dm);    
	            }
	        }  
	        Object[][] files = new Object[result.size()][];
	        for(int i=0; i<result.size(); i++){
	            files[i] = new Object[]{result.get(i)};
	        }        
	        return files;
	    }
	  
	  
	  
	  
	  @Test(dataProvider="getData")
	  public void auditDriver(Map param){
		  
			 /**
			  * 把字符串转出json格式，并获取到相应的字段信息
			  */
//		 String[] data = parseStr(readTxt("C:\\newWorkSpace\\jiekou\\test-data\\AuditTest.txt"),";");
//		 for(String testCase:data){
//			  Map<String, Object> dataList = JsonParseUtil.getJsonData(testCase,"request");
//			   //依次调用每个请求
//			  // for(Map<String, Object> item:dataList){
//			   OkHttpUtil.postJson(Constance.test_host+dataList.get("url").toString(),dataList.get("body").toString());
//			   
//		   }
		 
		 //OkHttpUtil.postJson(Constance.test_host+url,body);
		  System.out.println("url="+param.get("url")+",json="+param.get("body"));
		  ObjectMapper mapper=new ObjectMapper();
		  
		  String result = null;
		try {
			//map转成json 
			result = OkHttpUtil.postJson(Constance.test_host+param.get("url").toString(),mapper.writeValueAsString(param.get("body")));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		  System.out.println("----->"+result);
		  
	  }
	  
	  
	  @Test(dataProvider="test2")
	  public void auditV2(Map<?,?> param){
		  
		  String result = OkHttpUtil.postJson(Constance.test_host+param.get("url").toString(),param.get("body").toString());
		  System.out.println(result);
		  
	  }
	  
	  @Test(dataProvider="test2")
	  public void auditV1(Map<?,?> param){
		  
		  String result = OkHttpUtil.postJson(Constance.test_host+param.get("url").toString(),param.get("body").toString());
		  System.out.println(result); 
	  }
	  
      @Test
      public void auditTest(){
    	  String result = OkHttpUtil.postJson(url, json);
    	  List<Map<String, Object>> dataMap = JsonParseUtil.getJsonObjectList(result, "data");
    	    
    	  System.out.println("url="+url+",json="+json);
    	  System.out.println(result);
    	  if(dataMap!=null){
	    	  for(int i=0;i<dataMap.size();i++){
		    	  System.out.println("data:"+dataMap.get(i).get("rates"));
		    	  System.out.println("creditLevel:"+dataMap.get(i).get("creditLevel"));
	    	  }
	    	  System.out.println("dataMap:"+dataMap.size());
    	  }	  
      }
      

    	  
    	  

      

}
