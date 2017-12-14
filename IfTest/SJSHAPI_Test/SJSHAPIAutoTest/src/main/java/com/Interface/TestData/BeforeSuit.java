package com.Interface.TestData;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.Interface.service.Service;

public class BeforeSuit {
	private Service service;

	@Parameters("envId")
	@BeforeSuite(alwaysRun=true)
	public void firstSetup(int envId) {
		System.out.println("SuitSetup!!!!!!!!");
		service = Service.getInstance();
		service.init(envId);
	}

}
