package com.nuance.eq.web.elements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.nuance.eq.page.locators.AddUserPageObject;
import com.nuance.eq.page.locators.LoginPageObjects;

public class AddUser {

	public static WebElement AccountLink(WebDriver driver)
    {
    	WebElement element=driver.findElement(AddUserPageObject.Accounts_Link);
    	return element;
    }
	
	public static WebElement UsersLink(WebDriver driver)
    {
    	WebElement element=driver.findElement(AddUserPageObject.Users_Link);
    	return element;
    }
	
	public static WebElement AddUserButton(WebDriver driver)
    {
    	WebElement element=driver.findElement(AddUserPageObject.ADD_User_Button);
    	return element;
    }
	
	public static WebElement UserId(WebDriver driver)
    {
    	WebElement element=driver.findElement(AddUserPageObject.User_Id);
    	return element;
    }
	
	public static WebElement UserEmail(WebDriver driver)
    {
    	WebElement element=driver.findElement(AddUserPageObject.User_Email);
    	return element;
    }
	
	public static WebElement SaveButton(WebDriver driver)
    {
    	WebElement element=driver.findElement(AddUserPageObject.SAVE_User_Button);
    	return element;
    }
	
 
}
