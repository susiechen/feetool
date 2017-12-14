package com.ppdai.TestCases;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.Interface.utils.RegexHelper;

public class Main {
	
   public static void main(String[] args) {
//	   String requestBody="/v1/trade/test?userid=#{userId}";
//	   RegexHelper rHelper = new RegexHelper();
//	   int index = rHelper.getIndex("#\\{.*?\\}", requestBody);
//	   if (index != -1) {
//	         requestBody = rHelper.replaceString(requestBody);
//	     }
//	   System.out.println(requestBody);
	   
	   RegexHelper regexHelper=new RegexHelper();
       String str = regexHelper.getFirstMatch("\"tradeId\":\"(.*?)\""
    		   ,"{\"code\":\"00000000\",\"message\":\"鎴愬姛\",\"data\":{\"tradeId\":\"933609073894059008\"}}");
//	   String str = regexHelper.getFirstMatch("a", "ab123");
        System.out.println(str);
       String[] data = str.split(":");
       Map<String, String> hashMap =new HashMap<String, String>();
       if(data.length >= 2){
    	   hashMap.put(data[0].replaceAll("\"", ""), data[1].replaceAll("\"", ""));  
       }
//       System.out.println(Arrays.toString(data)); 
       System.out.println("hashmap:"+hashMap);
       RegexHelper rHelper = new RegexHelper();
       String input="{\"tradeId\":\"${tradeId}\",\"listingId\":\"${listingId}\"}";
	Map<String, String> hashMap1=new HashMap<>();
	hashMap1.put("tradeId", "1244454333");
	hashMap1.put("listingId", "1244454333");
	System.out.println("hashmap1:"+hashMap1);
	String result = rHelper.replaceString(input, hashMap1);
       
       System.out.println("result:"+result);
   }
   
   
//   public String replaceString(String input, Map<String, String> hashMap) {
//       this.pattern = Pattern.compile(this.regex);
//    this.matcher = this.pattern.matcher(input);
//       StringBuffer sBuffer = new StringBuffer();    String string;
//     while (this.matcher.find()) {
//      string = this.matcher.group().substring(2, this.matcher.group().length() - 1);
//      for (Map.Entry entry : hashMap.entrySet()) {
//        if (((String)entry.getKey()).equalsIgnoreCase(string)) {
//          this.matcher.appendReplacement(sBuffer, (String)entry.getValue());
//         }
//      }
//       }
//      this.matcher.appendTail(sBuffer);
//     return sBuffer.toString();
//   }
//  
}
