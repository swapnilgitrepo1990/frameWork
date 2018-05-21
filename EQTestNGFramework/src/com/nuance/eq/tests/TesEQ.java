package com.nuance.eq.tests;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.nuancce.eq.rest.utility.APIRequests;
import com.nuance.eq.common.functions.CommonTestUtility;
import com.nuance.eq.main.config.Config;
import com.nuance.eq.main.config.Constants;
import com.nuance.eq.main.config.RunTest;
import com.nuance.eq.selenium.utility.Action;
import com.nuance.eq.selenium.utility.SeleniumUtils;
import com.nuance.eq.selenium.utility.javaUtility;
import com.nuance.eq.web.elements.AddUser;

import com.nuance.eq.web.elements.Login;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


public class TesEQ {
	
	final static Logger logger = Logger.getLogger(TesEQ.class.getName());
	
	public WebDriver driver=Config.getDriver(Constants.browser.toLowerCase());
	static ATUReports report;
	@BeforeMethod
	public void setUp() throws Exception 
	{
		report=new ATUReports();
		ATUReports.setWebDriver(driver);
		ATUReports.indexPageDescription = "Test Reports";
	}
			
	@Test(description = "Test Add user",dataProvider="testAddUser",dataProviderClass=TestDataProvider.class)
	public void testAddUser(String srno,String runmode,String username,String email,String pin1WebReset,String pin2WebReset,String tempMsg,String tempSub) throws InterruptedException {
		
		System.out.println("Test Case one with Thread Id:- "+ Thread.currentThread().getId());
		
		//Enable Auto Pin management through API
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("Pin1WebReset",pin1WebReset);
		hashMap.put("Pin2WebReset",pin2WebReset);
		hashMap.put("PinTmpMsg",tempMsg);
		hashMap.put("PinTmpSubj",tempSub);
		APIRequests.put("AutoPinMgmt",hashMap);
		
		//Login to web application and add user 
		driver.get(RunTest.testconfig.get("APP_URL"));
		SeleniumUtils.waitForPageLoad(driver);
		
		String randomUserName=username+"_"+javaUtility.randomNumber();
		logger.info("Client name is:-"+randomUserName);
		
		CommonTestUtility.LoginToESQ(driver,report);
		
		
		TimeUnit.SECONDS.sleep(5);
		SeleniumUtils.waitForPageLoad(driver);
		ATUReports.add("Login successsful in EQ", LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		//TimeUnit.SECONDS.sleep(5);
		Action.click(AddUser.AccountLink(driver),"Account Link",driver);
		//TimeUnit.SECONDS.sleep(5);
		Action.click(AddUser.UsersLink(driver),"Users Link",driver);
		TimeUnit.SECONDS.sleep(5);
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='nu-iframe']")));
		Action.click(AddUser.AddUserButton(driver),"Add User Button",driver);
		driver.switchTo().defaultContent();
				
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='nu-iframe']")));
		
		SeleniumUtils.waitForPageLoad(driver);
		ATUReports.add("Add user page Open", LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Action.type(AddUser.UserId(driver),randomUserName, "User Name",driver);
		
		Action.type(AddUser.UserEmail(driver),email, "User Email",driver);
		//input[@id='EmailAddress']
		ATUReports.add("User Name Entered", LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Action.click(AddUser.SaveButton(driver),"Save button",driver);
		SeleniumUtils.waitForPageLoad(driver);
		ATUReports.add("User Created", LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		
		 driver.switchTo().defaultContent();
		
		//Verify PIN1 and PIN2 generated for user and login with that PIN1 and PIN2
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='nu-iframe']")));
		
		 List<WebElement>allUsers= driver.findElements(By.xpath("//table[@id='ItemList']//tr"));
		 
		 for(int i=3;i<allUsers.size();i++)
		 {
			 String userName=driver.findElement(By.xpath("//table[@id='ItemList']//tr["+i+"]//td[2]//a")).getAttribute("text");
			 if(userName.equalsIgnoreCase(randomUserName))
			 {
				 driver.findElement(By.xpath("//table[@id='ItemList']//tr["+i+"]//td[2]//a")).click(); 
				 SeleniumUtils.waitForPageLoad(driver);
				 break;
			 }
		 }
		 driver.switchTo().defaultContent();
		 
		 driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='nu-iframe']")));
		 
		 WebElement element=driver.findElement(By.xpath("//input[@id='SecondaryPin']"));
		 SeleniumUtils.moveToElement(element, "Secondary PIN", driver);
		 
		 
		String primaryPIN=driver.findElement(By.xpath("//input[@id='PrimaryPin']")).getAttribute("value");
		String seconderyPIN=driver.findElement(By.xpath("//input[@id='SecondaryPin']")).getAttribute("value");
		
		if(primaryPIN!=null && seconderyPIN !=null)
		{
			ATUReports.add("Primary PIN="+primaryPIN+" and secondary PIN="+seconderyPIN+" generated for user", LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
		}else {
			ATUReports.add("Primary or secondary PIN not generated for user", LogAs.INFO, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.fail("Primary or secondary PIN not generated for user");
		}
		 driver.switchTo().defaultContent();
		 
		 
		 
		//logoff code
//			driver.findElement(By.xpath("//span[@id='HeaderUserInfoMenuUserName']")).click();
//			 TimeUnit.SECONDS.sleep(1);
//			 
//			 System.out.println(driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//li/a")).getAttribute("href"));
//			 
//			 driver.findElement(By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//li/a[text()='Logoff']")).click();
//			 SeleniumUtils.waitForPageLoad(driver);
//			 driver.get(RunTest.testconfig.get("APP_URL"));
			//
	
		
	}
	
	@Test(description = "API Autom PIN",dataProvider="testAPI_AutoPIN",dataProviderClass=TestDataProvider.class)
	public void testAPI_AutoPIN(String srno,String runmode,String pin1WebReset,String pin2WebReset,String tempSub,String tempMsg,String clearPinsonLock,String pin1MinLen ,String pin1MaxLen) throws InterruptedException 
		{
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("Pin1WebReset",pin1WebReset);
			hashMap.put("Pin2WebReset",pin2WebReset);
			hashMap.put("PinTmpMsg",tempMsg);
			hashMap.put("PinTmpSubj",tempSub);
			hashMap.put("ClearPinsOnLock",clearPinsonLock);
			hashMap.put("Pin1MaxLen",pin1MaxLen);
			hashMap.put("Pin1MinLen",pin1MinLen);
			
			APIRequests.put("AutoPinMgmt",hashMap);
		}
		
		@Test(description = "Test Active Directory",dataProvider="testActiveDirectory",dataProviderClass=TestDataProvider.class)
		public void testActiveDirectory(String srno,String runmode,String domainController,String follBackDomainController,String userName,String password,String appPartition,String syncAdd,String syncChanges,String syncDelete,String doAutoSync,String Import,String enforceLimit) throws InterruptedException 
		{
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("DomainController",domainController);
			hashMap.put("AllowFallbackToOtherDomainControllers",follBackDomainController);
			hashMap.put("AuthUserName",userName);
			hashMap.put("AuthUserPassword",password);
			hashMap.put("ApplicationPartition",appPartition);
			hashMap.put("SyncAdds",syncAdd);
			hashMap.put("SyncChanges",syncChanges);
			hashMap.put("SyncDeletes",syncDelete);
			hashMap.put("DoAutoSync",doAutoSync);
			hashMap.put("Import",Import);
			hashMap.put("EnforceDeptLimits",enforceLimit);
			
			APIRequests.putWithExternalJson("AD_Server_Properties","DirectoryServicesSync",hashMap);
		
		}
		
}	
		
	
	
	
