package com.etc.spring.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.testng.TestNG;

public class ParentClassLoad extends ClassLoader {

	private static final Class<?>[] name = null;
	private byte[] results;

	public ParentClassLoad(String pathName) {
		super(ParentClassLoad.class.getClassLoader());
		results = loadClassFile(pathName);
	}

	private byte[] loadClassFile(String classPath) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			FileInputStream fi = new FileInputStream(classPath);
			BufferedInputStream bis = new BufferedInputStream(fi);
			byte[] data = new byte[1024 * 256];
			int ch = 0;
			while ((ch = bis.read(data, 0, data.length)) != -1) {
				bos.write(data, 0, ch);
				//System.out.print(ch);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.print(bos.toByteArray());
		return bos.toByteArray();

	}

	@Override
	protected Class<?> loadClass(String arg0, boolean arg1)
			throws ClassNotFoundException {
		Class<?> clazz = findLoadedClass(arg0);
		
		if (clazz == null) {
			if (getParent() != null) {
				try {
					clazz = getParent().loadClass(arg0);
				} catch (Exception e) {
					System.out.println("getParent : " + getParent());
					System.out.println("parentClass load failed");
				}
				
			}

			if (clazz == null) {
				clazz = defineClass(arg0, results, 0, results.length);
			}
		}
		return clazz;
	}
	
}
