package com.nuance.eq.main.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class CustomReporter implements IReporter {
	private PrintWriter mOut;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		new File(outputDirectory).mkdirs();
		try {
			mOut = new PrintWriter(new BufferedWriter(new FileWriter(new File(
					outputDirectory, "custom-report.html"))));
		} catch (IOException e) {
			System.out.println("Error in creating writer: " + e);
		}
		startHtml();
		
		
		print("Suites run: " + suites.size());
		for (ISuite suite : suites) {
		//	print("Suite>" + suite.getName());
			
			System.out.println("Suite Name:-"+suite.getName());
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (String testName : suiteResults.keySet()) {
				//print("    Test>" + testName);
				System.out.println("Test Name:-"+testName);
				
				ISuiteResult suiteResult = suiteResults.get(testName);
				ITestContext testContext = suiteResult.getTestContext();
				//print("        Failed>" + testContext.getFailedTests().size());
				System.out.println("No of Test Case Failed:-"+testContext.getFailedTests().size());
				System.out.println("Below are Failed test case");
				
				IResultMap failedResult = testContext.getFailedTests();
				Set<ITestResult> testsFailed = failedResult.getAllResults();
				for (ITestResult testResult : testsFailed) {
					/*print("            " + testResult.getName());
					print("                " + testResult.getThrowable());*/
					System.out.println("Test Case Name:-"+testResult.getName()+" Reason is"+testResult.getThrowable().getMessage());
				}
				
				System.out.println("Below are Passed test case");
				IResultMap passResult = testContext.getPassedTests();
				Set<ITestResult> testsPassed = passResult.getAllResults();
			//	print("        Passed>" + testsPassed.size());
				for (ITestResult testResult : testsPassed) {
				/*	print("            "
							+ testResult.getName()
							+ ">took "
							+ (testResult.getEndMillis() - testResult
									.getStartMillis()) + "ms");*/
					System.out.println("Test Case Name:-"+testResult.getName()+ ">took "+ "start="+testResult.getStartMillis()+"End="+testResult.getEndMillis()+"Total="+(testResult.getEndMillis() - testResult.getStartMillis()) + "ms");
				}
				IResultMap skippedResult = testContext.getSkippedTests();
				Set<ITestResult> testsSkipped = skippedResult.getAllResults();
                   System.out.println("Below are skipped test case");
				//print("        Skipped>" + testsSkipped.size());
				for (ITestResult testResult : testsSkipped) {
					System.out.println("            " + testResult.getName());
				}

			}
		}
		endHtml();
	    mOut.flush();
	    mOut.close();
	}

	private void print(String text) {
		System.out.println(text);
		mOut.println(text + " ");
	}

	private void startHtml() {
		mOut.println("");
		mOut.println("");
		mOut.println("TestNG Html Report Example");		
		mOut.println("");
		mOut.println("");
	}
	
	private void endHtml() {
		mOut.println("");
	}

	/*@Override
	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}*/
}