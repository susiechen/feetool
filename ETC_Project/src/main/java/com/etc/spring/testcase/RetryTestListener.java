package com.etc.spring.testcase;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

/**
 */
public class RetryTestListener implements IAnnotationTransformer,ITestListener {

    /* (non-Javadoc)
     * @see org.testng.IAnnotationTransformer#transform(org.testng.annotations.ITestAnnotation, java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Method)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
            Constructor testConstructor, Method testMethod) {

        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            // annotation.setRetryAnalyzer(RetryAnalyzer.class);
            //annotation.setRetryAnalyzer(RetryFail.class);
            annotation.setRetryAnalyzer(TestRetryAnalyzer.class);
        }
    }

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {

	}

	@Override
	public void onTestFailure(ITestResult result) {

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onFinish(ITestContext testContext) {
		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) 
		{			
			passedTestIds.add(getId(passedTest));		
		}		
		Set<Integer> failedTestIds = new HashSet<Integer>();		
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
			int failedTestId = getId(failedTest);
			if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
				testsToBeRemoved.add(failedTest);
				} else {
				failedTestIds.add(failedTestId);
			}
		}
		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator();iterator.hasNext();) {
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) {
				iterator.remove();			
			}		
		}	
	}


	private int getId(ITestResult result) {		
		int id = result.getTestClass().getName().hashCode();		
		id = id + result.getMethod().getMethodName().hashCode();		
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;	
	}

}