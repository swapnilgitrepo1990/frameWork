package com.nuance.eq.common.functions;

import org.openqa.selenium.WebDriver;

import com.nuance.eq.main.config.RunTest;
import com.nuance.eq.selenium.utility.Action;
import com.nuance.eq.selenium.utility.SeleniumUtils;
import com.nuance.eq.tests.TesEQ;
import com.nuance.eq.web.elements.Login;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class CommonTestUtility {
	TesEQ twfj =new TesEQ();
	
	//WebDriver driver=twfj.driver;
	 //Action action=new Action();
	//Login login=new Login();
	public static void LoginToESQ(WebDriver driver,ATUReports report)
	{
			
			
			Action.type(Login.UserName(driver),RunTest.testconfig.get("ESQ_User_Name"), "User Name",driver);
			
			Action.type(Login.Password(driver),RunTest.testconfig.get("ESQ_User_Password"), "Password",driver);
			Action.click(Login.SubmitButton(driver),"Password Submit button",driver);
			SeleniumUtils.waitForPageLoad(driver);
			
			
			
		}
}
