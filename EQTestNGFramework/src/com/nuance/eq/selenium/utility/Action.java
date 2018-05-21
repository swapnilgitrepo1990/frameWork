package com.nuance.eq.selenium.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.nuance.eq.main.config.RunTest;
import com.nuance.eq.tests.TesEQ;

public class Action {
	
	final static Logger logger = Logger.getLogger(TesEQ.class.getName());
		
	public static void getUrl(String url,WebDriver driver)
	{
		driver.get(url);
		SeleniumUtils.waitForPageLoad(driver);
		
	}
	
	public static void click(WebElement element,String elementName,WebDriver driver)
	{
		element.click();
		logger.info("Clicked on element "+elementName);
	}
	
	public static void type(WebElement element,String textToType,String elementName,WebDriver driver)
	{
		element.sendKeys(textToType);
		logger.info("Type in "+elementName);
	}
	
	public static void selectFromList(WebElement element,String text,String elementName,WebDriver driver)
	{
		Select select=new Select(element);
		select.selectByVisibleText(text);
	}
	
	public static void clear(WebElement element,String elementName,WebDriver driver)
	{
		element.clear();
		logger.info("Element text cleared:-"+elementName);
	}
}
