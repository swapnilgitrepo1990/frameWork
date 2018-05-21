package com.nuance.eq.page.locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nuance.eq.main.config.RunTest;


public class LoginPageObjects {
	
	public static By LOGIN_USER_ID = By.xpath("//input[@id='UserName']");
    public static By LOGIN_USER_PASSWORD = By.xpath("//input[@id='Password']");
    public static By LOGIN_SUBMIT_BUTTON = By.xpath("//input[@type='submit']"); 
}
