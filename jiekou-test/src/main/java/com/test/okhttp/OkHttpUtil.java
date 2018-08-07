package com.test.okhttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.test.common.JsonParseUtil;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * request 封装
 * @author chenwenhua
 *
 */
public class OkHttpUtil {
	private static OkHttpClient client=new OkHttpClient();
	/**
	 * get request
	 * @param url
	 * @return
	 */
    public static String get(String url){
    	Request request=new Request.Builder().url(url).build();
    	try {
			Response response = client.newCall(request).execute();
			if(response.isSuccessful())
				 return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * 
     * @param url
     * @param json
     * @return
     */
    public static String postJson(String url,String json){
    	MediaType JSON=MediaType.parse("application/json;charset=utf-8");
		RequestBody body=RequestBody.create(JSON, json);
    	Request request=new Request.Builder().url(url).post(body).build();
    	try {
			Response response = client.newCall(request).execute();
			//if(response.isSuccessful())
				return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 使用post进行表单(键值对)上传
     * @param url
     * @param params
     * @return
     */
    public static String postNameValuePair(String url, Map<String,String> params){
    	//MediaType JSON=MediaType.parse("application/json;charset=utf-8");
    	RequestBody body = null;
    	for(String pKey:params.keySet()){
    		 body=new FormBody.Builder().add(pKey, params.get(pKey)).build();
		}
		
    	Request request=new Request.Builder().url(url).post(body).build();
    	try {
			Response response = client.newCall(request).execute();
			if(response.isSuccessful())
				return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    
    
    /**
     * 这里使用了HttpClinet的API。只是为了方便
     * @param params
     * @return
     */
    public static String formatParams(List<BasicNameValuePair> params){
        return URLEncodedUtils.format(params, "utf-8");
    }
    /**
     * 为HttpGet 的 url 方便的添加多个name value 参数。
     * @param url
     * @param params
     * @return
     */
    public static String attachHttpGetParams(String url, List<BasicNameValuePair> params){
        return url + "?" + formatParams(params);
    }
    /**
     * 为HttpGet 的 url 方便的添加1个name value 参数。
     * @param url
     * @param name
     * @param value
     * @return
     */
    public static String attachHttpGetParam(String url, String name, String value){
        return url + "?" + name + "=" + value;
    }
    
    
    public static void main(String[] args) {
		String str="{"+
			   " \"platformCode\": \"PPD\","+
			   " \"productCode\": \"SJSH\"," +
			    "\"userId\": \"24739321\"}";
		
		List<Map<String, Object>> data = JsonParseUtil.getData(str);
		Map<String, Object> data1 = JsonParseUtil.getData1(str);
		System.out.println(data);
		System.out.println(data1);
		
		List<BasicNameValuePair> params=new ArrayList<BasicNameValuePair>();
		for(String key: data1.keySet()){
			params.add(new BasicNameValuePair(key,(String) data1.get(key)));
		}
		
		String url = OkHttpUtil.attachHttpGetParams("http://www.test.com", params);
		System.out.println(url);

	}
}
