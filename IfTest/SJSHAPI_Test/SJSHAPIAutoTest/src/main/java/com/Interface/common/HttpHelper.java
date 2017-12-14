package com.Interface.common;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class HttpHelper {

 private CloseableHttpClient client = HttpClients.createDefault();
 private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout('\uea60').setConnectTimeout('\uea60').setConnectionRequestTimeout('\uea60').build();
 private static HttpHelper instance = null;

 private HttpHelper() {
 }

 public static HttpHelper getInstance() {
     if (instance == null) {
         instance = new HttpHelper();
     }

     return instance;
 }

 public String doGet(String url, HashMap<String, String> map) {
     map.remove("Content-Type");
     Header[] headers = new Header[map.size()];
     int i = 0;

     for (Iterator var6 = map.entrySet().iterator(); var6.hasNext(); ++i) {
         Entry httpGet = (Entry) var6.next();
         headers[i] = new BasicHeader((String) httpGet.getKey(), (String) httpGet.getValue());
     }

     HttpGet var7 = new HttpGet(url);
     var7.setHeaders(headers);
     return this.doGet(var7);
 }

 public String doGet(HttpGet httpGet) {
     CloseableHttpResponse httpResponse = null;
     HttpEntity httpEntity = null;
     String responseContext = null;

     try {
         httpGet.setConfig(this.requestConfig);
         httpResponse = this.client.execute(httpGet);
         httpEntity = httpResponse.getEntity();
         responseContext = EntityUtils.toString(httpEntity, "UTF-8");
     } catch (Exception var14) {
         responseContext = var14.toString();
     } finally {
         try {
             if (httpResponse != null) {
                 httpResponse.close();
             }
         } catch (Exception var13) {
             responseContext = var13.toString();
         }

     }

     return responseContext;
 }

 public String doPost(String uri, HashMap<String, String> map, String params) {
     String contentType = (String) map.get("Content-Type");
     HttpPost httpPost = new HttpPost(uri);
     Header[] headers = new Header[map.size()];
     int i = 0;

     try {
         for (Iterator var9 = map.entrySet().iterator(); var9.hasNext(); ++i) {
             Entry e = (Entry) var9.next();
             headers[i] = new BasicHeader((String) e.getKey(), (String) e.getValue());
         }

         StringEntity var11 = new StringEntity(params, "UTF-8");
         var11.setContentType(contentType);
         httpPost.setEntity(var11);
         httpPost.setHeaders(headers);
     } catch (Exception var10) {
         var10.printStackTrace();
     }

     return this.doPost(httpPost);
 }

 public String doPost(HttpPost httpPost) {
     CloseableHttpResponse httpResponse = null;
     HttpEntity httpEntity = null;
     String responseContext = null;

     String var7;
     try {
         httpPost.setConfig(this.requestConfig);
         httpResponse = this.client.execute(httpPost);
         httpEntity = httpResponse.getEntity();
         if (httpEntity == null) {
             responseContext = "";
             var7 = responseContext;
             return var7;
         }

         responseContext = EntityUtils.toString(httpEntity, "utf-8");
         if (responseContext.contains("&lt;") && responseContext.contains("&gt;")) {
             if (responseContext.contains("xml version=\"1.0\"")) {
                 responseContext = StringEscapeUtils.unescapeXml(responseContext);
             } else {
                 responseContext = StringEscapeUtils.unescapeHtml4(responseContext);
             }
         }

         var7 = responseContext;
     } catch (Exception var16) {
         responseContext = var16.toString();
         var7 = responseContext;
         return var7;
     } finally {
         try {
             if (httpResponse != null) {
                 httpResponse.close();
             }
         } catch (Exception var15) {
             responseContext = var15.toString();
         }

     }

     return var7;
 }



 public String doPut(String url, HashMap<String, String> map,String params) {
     String contentType=(String)map.get("Content-Type");
     HttpPut var7 = new HttpPut(url);
     Header[] headers = new Header[map.size()];
     int i = 0;
     for (Iterator var6 = map.entrySet().iterator(); var6.hasNext(); ++i) {
         Entry e = (Entry) var6.next();
         headers[i] = new BasicHeader((String) e.getKey(), (String) e.getValue());
     }
     StringEntity var11 = new StringEntity(params, "UTF-8");
     var11.setContentType(contentType);
     var7.setEntity(var11);
     var7.setHeaders(headers);
     return this.doPut(var7);
 }
 public String doPut(HttpPut httpPut) {
     CloseableHttpResponse httpResponse = null;
     HttpEntity httpEntity = null;
     String responseContext = null;

     String var7;
     try {
         httpPut.setConfig(this.requestConfig);
         httpResponse = this.client.execute(httpPut);
         httpEntity = httpResponse.getEntity();
         if (httpEntity == null) {
             responseContext = "";
             var7 = responseContext;
             return var7;
         }

         responseContext = EntityUtils.toString(httpEntity, "utf-8");
         if (responseContext.contains("&lt;") && responseContext.contains("&gt;")) {
             if (responseContext.contains("xml version=\"1.0\"")) {
                 responseContext = StringEscapeUtils.unescapeXml(responseContext);
             } else {
                 responseContext = StringEscapeUtils.unescapeHtml4(responseContext);
             }
         }

         var7 = responseContext;
     } catch (Exception var16) {
         responseContext = var16.toString();
         var7 = responseContext;
         return var7;
     } finally {
         try {
             if (httpResponse != null) {
                 httpResponse.close();
             }
         } catch (Exception var15) {
             responseContext = var15.toString();
         }

     }

     return var7;
 }



}
