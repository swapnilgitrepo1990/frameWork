package com.nuance.eq.tests;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.nuance.eq.main.config.Constants;
import com.nuance.eq.main.config.GetData;
import com.nuance.eq.main.config.RunTest;

/**
 * @author Swapnil Sonawane
 * @Description This class contains data providers of all methods
 *
 */
public class TestDataProvider {
	static GetData testData = new GetData();
	public final static Logger logger = Logger.getLogger(TesEQ.class.getName());
	
	@DataProvider(name = "Test Add User")
	public static Object[][] testAddUser(ITestContext context) {
		 Object[][] arrayObject = null;
		if(RunTest.testconfig.get("parallel_Mode").equalsIgnoreCase("On"))
		{
		String rowNumber = context.getCurrentXmlTest().getParameter("test_param");
	    logger.info("Row number set in the parameter is :-"+rowNumber);
	   
		arrayObject = testData.getDataFromExcelByRow(Constants.testDataFile, "testAddUser", Integer.parseInt(rowNumber));
				//.getDataWithYesRunMode(testData.getDataFromExcel(Constants.testDataFile, "testWorkFrontJobs"));
	}else{		
		arrayObject = testData
				.getDataWithYesRunMode(testData.getDataFromExcel(Constants.testDataFile, "testAddUser"));
	}
		return arrayObject;
	}

	@DataProvider(name = "API auto PIN management")
	public static Object[][] testAPI_AutoPIN(ITestContext context) {
		 Object[][] arrayObject = null;
		if(RunTest.testconfig.get("parallel_Mode").equalsIgnoreCase("On"))
		{
		String rowNumber = context.getCurrentXmlTest().getParameter("test_param");
	    logger.info("Row number set in the parameter is :-"+rowNumber);
	   
		arrayObject = testData.getDataFromExcelByRow(Constants.testDataFile, "testAPI_AutoPIN", Integer.parseInt(rowNumber));
				//.getDataWithYesRunMode(testData.getDataFromExcel(Constants.testDataFile, "testWorkFrontJobs"));
	}else{		
		arrayObject = testData
				.getDataWithYesRunMode(testData.getDataFromExcel(Constants.testDataFile, "testAPI_AutoPIN"));
	}
		return arrayObject;
	}
	
	
	@DataProvider(name = "Active Directory")
	public static Object[][] testActiveDirectory(ITestContext context) {
		 Object[][] arrayObject = null;
		if(RunTest.testconfig.get("parallel_Mode").equalsIgnoreCase("On"))
		{
		String rowNumber = context.getCurrentXmlTest().getParameter("test_param");
	    logger.info("Row number set in the parameter is :-"+rowNumber);
	   
		arrayObject = testData.getDataFromExcelByRow(Constants.testDataFile, "testActiveDirectory", Integer.parseInt(rowNumber));
				//.getDataWithYesRunMode(testData.getDataFromExcel(Constants.testDataFile, "testWorkFrontJobs"));
	}else{		
		arrayObject = testData
				.getDataWithYesRunMode(testData.getDataFromExcel(Constants.testDataFile, "testActiveDirectory"));
	}
		return arrayObject;
	}

}
