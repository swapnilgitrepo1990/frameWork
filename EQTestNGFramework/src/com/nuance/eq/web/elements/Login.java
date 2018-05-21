package com.nuance.eq.web.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nuance.eq.main.config.RunTest;
import com.nuance.eq.page.locators.LoginPageObjects;
import com.nuance.eq.tests.TesEQ;

public class Login {
		
	public static WebElement UserName(WebDriver driver)
	    {
	    	WebElement element=driver.findElement(LoginPageObjects.LOGIN_USER_ID);
	    	return element;
	    }
	 
	 public  static WebElement Password(WebDriver driver)
	    {
	    	WebElement element=driver.findElement(LoginPageObjects.LOGIN_USER_PASSWORD);
	    	return element;
	    }
	 
	 public static  WebElement SubmitButton(WebDriver driver)
	    {
	    	WebElement element=driver.findElement(LoginPageObjects.LOGIN_SUBMIT_BUTTON);
	    	return element;
	    }
}
