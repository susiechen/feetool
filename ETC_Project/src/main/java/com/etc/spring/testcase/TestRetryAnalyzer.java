package com.etc.spring.testcase;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestRetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private int maxCount = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (count < maxCount) {
                count++;
                result.setStatus(ITestResult.SUCCESS);
                String message = Thread.currentThread().getName()
                        + ": Error in " + result.getTestClass().getName()+" "+result.getName() + " Retrying "
                        + (maxCount + 1 - count) + " more times";
                System.out.println(message);
                Reporter.log(message);
                return true;
            } else {
                result.setStatus(ITestResult.FAILURE);
            }
        }
        return false;
    }
}