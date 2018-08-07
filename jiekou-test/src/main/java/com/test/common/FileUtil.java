package com.test.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	
	 private String fileName;


	public FileUtil(String fileName) {
		// TODO Auto-generated constructor stub
		 this.fileName=fileName;
		 //this.readFile(fileName);
	}
	
	
	/**
	 * read txt file
	 * @param fileName
	 * @return
	 */
	public  String readFile(){
		InputStream is=null;
		StringBuffer sb=new StringBuffer();
		
		try {
			is=new FileInputStream(this.fileName);
			
			byte[] byteBuffer=new byte[is.available()];
			int read=0;
			while((read=is.read(byteBuffer))!=-1){
				sb.append(new String(byteBuffer,0,read));
			}	
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
				
		return sb.toString();
	}

}
