package com.test.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class JsonParseUtil {
	
	 /**
	  * 
	  * @param json
	  * @return list
	  */
	  public static List<Map<String, Object>>  getData(String data) {
		  //先将data数据转换成jsonObject
		  //String json="{\"code\": \"00000000\",\"message\": \"成功\"} {\"code\": \"00000011\",\"message\": \"fail\"}]";
		  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		  
		  Map<String, Object> data1 = JSON.parseObject(data, new TypeReference<Map<String,Object>>(){});
		  list.add(data1);
		  return list;
	  }
	  
		 /**
		  * 
		  * @param json
		  * @return list
		  */
		  public static Map<String, Object>  getData1(String data) {
			  //先将data数据转换成jsonObject
			  //String json="{\"code\": \"00000000\",\"message\": \"成功\"} {\"code\": \"00000011\",\"message\": \"fail\"}]";
//			  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			  
			  Map<String, Object> data1 = JSON.parseObject(data, new TypeReference<Map<String,Object>>(){});
//			  list.add(data1);
			  return data1;
		  } 
	
	
	/**
		  * 
		  * @param json
		  * @return list
		  */
		  public static List<Map<String, Object>>  getJsonObject(String json) {
			  //先将data数据转换成jsonObject
			  //String json="[{\"code\": \"00000000\",\"message\": \"成功\"},{\"code\": \"00000011\",\"message\": \"fail\"}]";
			   List<Map<String, Object>> data = JSON.parseObject(json, new TypeReference<List<Map<String,Object>>>(){});
			   //System.out.println(data.get(1).get("message"));
			   return data;
		  }
		  
		  
		  
	  
	  
		 /**
		  * 
		  * @param json
		  * @param data,数据结构为array
		  * @return list
		  */
		  public static List<Map<String, Object>>  getJsonObjectList(String json,String data) {
			  //先将data数据转换成jsonObject，在转换成list的数据结构
			  //String json="{\"code\": \"00000000\",\"message\": \"成功\",\"data\": [{\"code\": \"00000000\",\"message\": \"成功\"},{\"code\": \"00000011\",\"message\": \"fail\"}]}";
			  JSONArray data1 = JSON.parseObject(json).getJSONArray(data);
			  if(data1!=null){
				  List<Map<String, Object>> d = JSON.parseObject(data1.toJSONString(), new TypeReference<List<Map<String,Object>>>(){});
				  return d;
			  }
			  else  
				  return null;
		  }
	  
	  /**
	   * 
	   * @param json
	   * @param data
	   * @return
	   */
	  public static Map<String ,Object> getJsonData(String json,String data){
	  
//	  String json1="{\"code\": \"00000000\",\"message\": \"成功\",\"data\":  {\"totalLoanPrincipal\": 210000,\"totalUnpaidPrincipal\": 176416,\"loanNum\": 1,\"overdueDays\": -22}}";
      //先将data数据转换成jsonObject，在转换成map的数据结构
	  JSONObject data1 = JSON.parseObject(json).getJSONObject(data);
	  Map<String, Object> d = JSON.parseObject(data1.toJSONString(), new TypeReference<Map<String,Object>>(){});
	  //System.out.println(d.get("overdueDays"));
	  return d;
	  
	  }
}
