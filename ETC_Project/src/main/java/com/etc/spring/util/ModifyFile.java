package com.etc.spring.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class ModifyFile {
	public static void modifyString(String fileName,String srcStr,String replaceStr)
	{
		File file = new File(fileName);
		try {
			BufferedReader bufIn = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line=null;
			StringBuffer buf= new StringBuffer();
			while ((line = bufIn.readLine()) != null) {
				line = line.replaceAll(srcStr, replaceStr);
				buf.append(line);
				buf.append(System.getProperty("line.separator"));
			}
			bufIn.close();
			BufferedWriter bufOut =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			bufOut.write(buf.toString().toCharArray());
			bufOut.close();
			System.out.print("modify success!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void deleteLine(String fileName,String srcStr)
	{
		File file = new File(fileName);
		try {
			BufferedReader bufIn = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line=null;
			StringBuffer buf= new StringBuffer();
			while ((line = bufIn.readLine()) != null) {
				if(line.contains(srcStr))
				{}else {
					buf.append(line);
					buf.append(System.getProperty("line.separator"));
				}
			}
			bufIn.close();
			BufferedWriter bufOut =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			bufOut.write(buf.toString().toCharArray());
			bufOut.close();
			System.out.print("delete line  success!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void addLine(String fileName,String srcStr,String desStr)
	{
		File file = new File(fileName);
		try {
			BufferedReader bufIn = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line=null;
			StringBuffer buf= new StringBuffer();
			while ((line = bufIn.readLine()) != null) {
				if(line.contains(srcStr))
				{
					buf.append(line);
					buf.append(System.getProperty("line.separator"));
					buf.append(desStr);
					buf.append(System.getProperty("line.separator"));
				}else {
					buf.append(line);
					buf.append(System.getProperty("line.separator"));
				}
			}
			bufIn.close();
			BufferedWriter bufOut =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			bufOut.write(buf.toString().toCharArray());
			bufOut.close();
			System.out.print("add line success!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static List<String> getFileList(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		List<String> filelist=new ArrayList<String>();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
				} else if (fileName.endsWith("class")) { // 判断文件名是否以.class结尾

					String strFileName = files[i].getName().substring(0,files[i].getName().indexOf("."));

					filelist.add(strFileName);
				} else {
					continue;
				}
			}

		}
		return filelist;
	}

}

