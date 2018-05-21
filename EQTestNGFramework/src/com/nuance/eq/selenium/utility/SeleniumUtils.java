package com.nuance.eq.selenium.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.nuance.eq.main.config.Constants;
import com.nuance.eq.main.config.RunTest;
import com.nuance.eq.tests.TesEQ;

/**
 * @author Swapnil Sonawane
 * @Description This class contains all the methods and utilities to handle web
 *              driver operations
 *
 */
public class SeleniumUtils {
	public final static Logger logger = Logger.getLogger(TesEQ.class.getName());
	//static WebDriver driver=RunTest.driver;
	/**
	 * @param driver
	 * @Description This method wait for page to load
	 */
	public static void waitForPageLoad(WebDriver driver) {
		logger.info("Waiting for page to load...");
		ExpectedCondition<Boolean> pageLoadCondition = new

		ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				Boolean pageReady = ((JavascriptExecutor) driver).executeScript("return document.readyState")
						.equals("complete");
				if (pageReady) {
					try {
						Thread.sleep(2000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				
				return pageReady;
			}
		};
		WebDriverWait wait = new WebDriverWait(driver,  Constants.maxTimeOut);
		wait.until(pageLoadCondition);
		logger.info("Page loaded successfully");
	}

	/**
	 * @param driver
	 * @param by
	 * @param timeout
	 * @param element
	 * @Description This method waits for given element for given time
	 */
	public static void explicitWaitForElement(By by, int timeout,WebDriver driver) {
		try {

			WebDriverWait wait = new WebDriverWait(driver, 120);
			logger.info("In explicitWaitForElement Function waiting for element " + timeout + " Seconds");
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.info("Not able to find element "+by.toString());
			logger.info(e.getMessage());
			Assert.fail("Not able to find element "+by.toString());

		}
	}
	
	/**
	 * @param driver
	 * @param by
	 * @param timeout
	 * @param element
	 * @Description This method waits for given element for given time
	 */
	public static void waitForVisibilityOfElement(WebElement element,String elementName,WebDriver driver) {
		try {

			WebDriverWait wait = new WebDriverWait(driver,  Constants.defaultTimeOut);
			logger.info("Waiting for visibility of element "+elementName+" " + Constants.defaultTimeOut + " Seconds");
			wait.until(ExpectedConditions.visibilityOf(element));
			logger.info("Element "+elementName + " is Visible");
		} catch (Exception e) {
			logger.info("Not able to find element " + elementName);
			logger.info(e.getMessage());
			Assert.fail("Not able to find element " + elementName);

		}
	}
	
	public static void waitForPresenceOfElement(WebElement element,String elementName,WebDriver driver) {
		try {

			WebDriverWait wait = new WebDriverWait(driver,  Constants.defaultTimeOut);
			logger.info("Waiting for presence of element "+elementName+" " + Constants.defaultTimeOut + " Seconds");
			wait.until(ExpectedConditions.elementToBeClickable(element));
			logger.info("Element "+elementName + " is Present");
		} catch (Exception e) {
			logger.info("Not able to find element " + elementName);
			logger.info(e.getMessage());
			Assert.fail("Not able to find element " + elementName);

		}
	}
	
	
	public static void waitForElementToBeClickable(WebElement element,String elementName,WebDriver driver) {
		try {

			WebDriverWait wait = new WebDriverWait(driver,  Constants.defaultTimeOut);
			logger.info("Waiting for element to be clickable  "+elementName+" " + Constants.defaultTimeOut + " Seconds");
			wait.until(ExpectedConditions.elementToBeClickable(element));
			logger.info("Element "+elementName + " is Visible");
		} catch (Exception e) {
			logger.info("Not able to find element " + elementName);
			logger.info(e.getMessage());
			Assert.fail("Not able to find element " + elementName);

		}
	}
	
	public static void moveToElementAndClick(WebElement element,String elementName,WebDriver driver) {
		try {

			Actions action = new Actions(driver); 
			action.moveToElement(element).click().perform();
			logger.info("Move and Clicked on element "+elementName);
		} catch (Exception e) {
			logger.info("Not able to find element " + elementName);
			logger.info(e.getMessage());
			Assert.fail("Not able to find element " + elementName);

		}
	}
	
	public static void moveToElement(WebElement element,String elementName,WebDriver driver) {
		try {

			Actions action = new Actions(driver); 
			action.moveToElement(element).build().perform();
			logger.info("Move and Clicked on element "+elementName);
		} catch (Exception e) {
			logger.info("Not able to find element " + elementName);
			logger.info(e.getMessage());
			Assert.fail("Not able to find element " + elementName);

		}
	}
	
	public static void scrollToElement(WebDriver driver,WebElement element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		//Scroll By Pixel
		js.executeScript("window.scrollBy(0,1000)");
		//Scroll to element
		js.executeScript("arguments[0].scrollIntoView();", element);
		   //This will scroll the web page till end.		
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        //This will scroll the page Horizontally till the element is found		
        js.executeScript("arguments[0].scrollIntoView();", element);
		
		js.executeScript("arguments[0].scrollIntoView();", Element);
		
	}


}
