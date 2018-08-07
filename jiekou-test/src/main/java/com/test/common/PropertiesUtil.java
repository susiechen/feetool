package com.test.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/*
 * 属性文件操作类
 */
public class PropertiesUtil {
   private static Properties p=null;
   
//   private static String path="src/main/resources/config.properties";
   
   public static Properties getProperties() {
	return p;
}




private static String path="config.properties";
   
   //加载文件
   static
    {
	    p=new Properties();
	    File file=new File(path);
	    InputStream in = null;
	    try {
//			    in=new FileInputStream(file);
			    in=PropertiesUtil.class.getClassLoader().getResourceAsStream(path);	
				p.load(in);
//				System.out.println("test:"+PropertiesUtil.class.getClassLoader().getClass().getResourceAsStream(path));
			}catch (FileNotFoundException e ) {
				e.printStackTrace();
			}catch(IOException e) {
			e.printStackTrace();
		}finally{	
				try {
					if(in!=null)
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	    
   }
   
   public static String getProperties(String key){
	    return p.getProperty(key);
   }
   
   public static String getProperties(String key,String defaultValue){
	    return p.getProperty(key,defaultValue);
	    
  }
   
   public static Map<String,Object> getPropertiesData(){
	    return  new HashMap<String ,Object>((Map)p);
	    
 }
   
   
   public static void main(String[] args) {
	 
	System.out.println(PropertiesUtil.getProperties("base.url"));
	
//	PropertiesUtil.class.getClass().getResourceAsStream("config.properties");
	
}
   
}
