package com.nuance.eq.page.locators;

import org.openqa.selenium.By;

public class AddUserPageObject {
	
	public static By Accounts_Link = By.xpath("//span[text()='Accounts']");
	public static By Users_Link = By.xpath("//span[text()='Users']");
	public static By ADD_User_Button = By.xpath("//a[@id='AddUserButton']");
	public static By User_Id = By.xpath("//input[@id='Name']");
	public static By User_Email = By.xpath("//input[@id='EmailAddress']");
	public static By SAVE_User_Button = By.xpath("//input[@id='SaveButton1']");
	


}
