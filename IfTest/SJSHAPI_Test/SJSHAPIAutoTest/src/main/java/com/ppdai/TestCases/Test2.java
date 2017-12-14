package com.ppdai.TestCases;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

public class Test2 {
	private Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	@Test
	public void test1(){
		map.put(1, 1);
		System.out.println(map);
		System.out.println("------------------------");
	}
	
	
	@Test
	public void test2(){
		map.put(2, 2);
		System.out.println(map);
		System.out.println("------------------------");
	}
	
	
	
}
