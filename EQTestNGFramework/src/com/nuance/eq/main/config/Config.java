package com.nuance.eq.main.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nuance.eq.selenium.utility.WebEventListener;
import com.nuance.eq.tests.TesEQ;

/**
 * @author Swapnil Sonawane
 * @Description This class initially do all framework related configurations
 *
 */

public class Config {
	public final static Logger logger = Logger.getLogger(TesEQ.class.getName());
	static WebDriver driver;
	public static WebEventListener eventListener;
	public static EventFiringWebDriver e_driver;
	
	
	/**
	 * @return driver
	 * @param driverName
	 * @Description This method returns driver
	 */
	public static WebDriver getDriver(String browser) {
		eventListener = new WebEventListener();

		switch (browser) {
		case "chrome":
			try {
				System.setProperty("webdriver.chrome.driver", Constants.chromeDriverPath);
				driver = new ChromeDriver();
				
				e_driver = new EventFiringWebDriver(driver);
				e_driver.register(eventListener);

				logger.info("Chrome Driver initialization successful");
			} catch (Exception e) {
				logger.info("Something went wrong while driver initialization " + e.getMessage());
				Assert.fail("Something went wrong while driver initialization " + e.getMessage());
			}
			break;

		case "firefox":
			try {
				System.setProperty("webdriver.gecko.driver", Constants.gickoDriverPath);
				driver = new FirefoxDriver();
				logger.info("Firefox Driver initialization successful");
			} catch (Exception e) {
				logger.info("Something went wrong while driver initialization " + e.getMessage());
				Assert.fail("Something went wrong while driver initialization " + e.getMessage());
			}
			break;

		case "ie":
			try {
				System.setProperty("webdriver.ie.driver", Constants.ieDriverPath);
				driver = new InternetExplorerDriver();
				logger.info("ie Driver initialization successful");
			} catch (Exception e) {
				logger.info("Something went wrong while driver initialization " + e.getMessage());
				Assert.fail("Something went wrong while driver initialization " + e.getMessage());
			}
			break;
		default:
			logger.info("Driver Name not given properly please check configuration in Test Configuration " + browser);
			Assert.fail("Driver Name not given properly please check configuration in Test Configuration ");

		}

		driver.manage().window().maximize();
		return e_driver;
	}

	/**
	 * @Description THis method set logger for the project
	 */
	public static void setLogger() {
		Properties properties = new Properties();
		properties.setProperty("log4j.rootLogger", "info,stdout,MyFile");
		properties.setProperty("log4j.rootCategory", "TRACE");

		properties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		properties.setProperty("log4j.appender.Target", "System.out");
		properties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		properties.setProperty("log4j.appender.stdout.layout.ConversionPattern",
				"%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");

		properties.setProperty("log4j.appender.MyFile", "org.apache.log4j.RollingFileAppender");
		properties.setProperty("log4j.appender.MyFile.File", "logs/execution.log");
		properties.setProperty("log4j.appender.MyFile.MaxFileSize", "100KB");
		properties.setProperty("log4j.appender.MyFile.MaxBackupIndex", "1");
		properties.setProperty("log4j.appender.MyFile.layout", "org.apache.log4j.PatternLayout");
		properties.setProperty("log4j.appender.MyFile.layout.ConversionPattern",
				"%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");

		PropertyConfigurator.configure(properties);
		logger.info("logger configuration successful.");
	}

	/**
	 * @param language
	 * @return Locators as per the language
	 * @Description This method returns locators as per the given language
	 */

	public static Map<String, String> setLanguage(String language) {
		Map<String, String> locators = null;
		switch (language.toLowerCase()) {
		case "english":
			locators = GetData.getProperty(Constants.english_properties_path);
			logger.info("Locators loaded successfully for " + language + " language");
			break;
		case "japanese":
			locators = GetData.getProperty(Constants.japanese_properties_path);
			logger.info("Locators loaded successfully for " + language + " language");
			break;
		case "chinese":
			locators = GetData.getProperty(Constants.chinese_properties_path);
			logger.info("Locators loaded successfully for " + language + " language");
			break;
		case "spanish":
			locators = GetData.getProperty(Constants.spanish_properties_path);
			logger.info("Locators loaded successfully for " + language + " language");
			break;
		case "arabic":
			locators = GetData.getProperty(Constants.arabic_properties_path);
			logger.info("Locators loaded successfully for " + language + " language");
			break;
		default:
			logger.info("Script not supported for given " + language + " language");
			Assert.fail("Test not supported for specified language ");
		}

		return locators;
	}

	/**
	 * @Description This method clear all the open browsers
	 */

	public static void tearDown() {
		logger.info("Execution completed");

		driver.quit();

		logger.info("Driver closed successfully");
	}

	/**
	 * @param mainExcel
	 * @Description This method update xml file as per execution data
	 */
	public static void updateXML(String[][] mainExcel) {
		GetData testData = new GetData();
		if (RunTest.testconfig.get("parallel_Mode").equalsIgnoreCase("On")) {
			
			try {
				ArrayList<String> testCaseForExecution = new ArrayList<>();
				for (int i = 0; i < mainExcel.length; i++) {
					testCaseForExecution.add(mainExcel[i][2]);
				}
				
				 String workingDir = System.getProperty("user.dir");
		    	  final String parellelTestngXML = workingDir + "\\config\\TestNGExecution\\test.xml";
		         DocumentBuilderFactory dbFactory =
		         DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         Document doc = dBuilder.newDocument();
		         
		         // root element
		         Element rootElement = doc.createElement("suite");
		         Attr rootattr = doc.createAttribute("name");
		         rootattr.setValue("Automation Test");
		         rootElement.setAttributeNode(rootattr);
		         
		         rootattr = doc.createAttribute("allow-return-values");
		         rootattr.setValue("false");
		         rootElement.setAttributeNode(rootattr);
		         
		         rootattr = doc.createAttribute("configfailurepolicy");
		         rootattr.setValue("skip");
		         rootElement.setAttributeNode(rootattr);
		         
		         rootattr = doc.createAttribute("data-provider-thread-count");
		         rootattr.setValue("2");
		         rootElement.setAttributeNode(rootattr);
		        
		         
		         rootattr = doc.createAttribute("group-by-instances");
		         rootattr.setValue("false");
		         rootElement.setAttributeNode(rootattr);
		        
		         
		         rootattr = doc.createAttribute("guice-stage");
		         rootattr.setValue("DEVELOPMENT");
		         rootElement.setAttributeNode(rootattr);
		        
		         
		         rootattr = doc.createAttribute("junit");
		         rootattr.setValue("false");
		         rootElement.setAttributeNode(rootattr);
		        
		         
		         rootattr = doc.createAttribute("name");
		         rootattr.setValue("Automation Test Suite");
		         rootElement.setAttributeNode(rootattr);
		        
		         

		         rootattr = doc.createAttribute("parallel");
		         rootattr.setValue("tests");
		         rootElement.setAttributeNode(rootattr);
		        
		         
		         rootattr = doc.createAttribute("preserve-order");
		         rootattr.setValue("true");
		         rootElement.setAttributeNode(rootattr);
		        
		         
		         rootattr = doc.createAttribute("skipfailedinvocationcounts");
		         rootattr.setValue("false");
		         rootElement.setAttributeNode(rootattr);
		        
		         
		         
		         rootattr = doc.createAttribute("thread-count");
		         rootattr.setValue("2");
		         rootElement.setAttributeNode(rootattr);
		        
		         
		         rootattr = doc.createAttribute("verbose");
		         rootattr.setValue("10");
		         rootElement.setAttributeNode(rootattr);
		         doc.appendChild(rootElement);
		         
		         //Listeners
		         Element listeners = doc.createElement("listeners");
		         rootElement.appendChild(listeners);
		         
		         Element listener = doc.createElement("listener");
		         listeners.appendChild(listener);
		         
		         rootattr = doc.createAttribute("class-name");
		         rootattr.setValue("atu.testng.reports.listeners.ATUReportsListener");
		         listener.setAttributeNode(rootattr);
		         
		         listener = doc.createElement("listener");
		         listeners.appendChild(listener);
		         
		         rootattr = doc.createAttribute("class-name");
		         rootattr.setValue("atu.testng.reports.listeners.ConfigurationListener");
		         listener.setAttributeNode(rootattr);
		         
		         listener = doc.createElement("listener");
		         listeners.appendChild(listener);
		         
		         rootattr = doc.createAttribute("class-name");
		         rootattr.setValue("atu.testng.reports.listeners.MethodListener");
		         listener.setAttributeNode(rootattr);
		         

		        for (int i=0;i<testCaseForExecution.size();i++)
		        {
		        	String[][] testDataWithYesOption=testData.getDataWithYesRunMode(testData.getDataFromExcel(Constants.testDataFile, testCaseForExecution.get(i)));
		        	System.out.println(testDataWithYesOption.length);
		        	for (int j=0;j<testDataWithYesOption.length;j++)
			        {
		        		System.out.println(testDataWithYesOption[j][0]);
		        		 Attr attr;
		        		/*// parameter element
		        		Element parameter = doc.createElement("parameter");
		                rootElement.appendChild(parameter);

		                // setting attribute to parameter element
		                Attr attr = doc.createAttribute("name");
		                attr.setValue("test_param");
		                parameter.setAttributeNode(attr);
		                
		                attr = doc.createAttribute("value");
		                attr.setValue(testDataWithYesOption[j][0]);
		                parameter.setAttributeNode(attr);*/

		                // test tag element
		                Element test = doc.createElement("test");
		                rootElement.appendChild(test);

		                // setting attribute to test tag element
		                attr = doc.createAttribute("allow-return-values");
		                attr.setValue("false");
		                test.setAttributeNode(attr);
		                
		                attr = doc.createAttribute("group-by-instances");
		                attr.setValue("false");
		                test.setAttributeNode(attr);
		                
		                attr = doc.createAttribute("junit");
		                attr.setValue("false");
		                test.setAttributeNode(attr);
		                
		                attr = doc.createAttribute("name");
		                attr.setValue(testCaseForExecution.get(i)+"_"+j);
		                test.setAttributeNode(attr);
		                
		                attr = doc.createAttribute("preserve-order");
		                attr.setValue("true");
		                test.setAttributeNode(attr);
		                
		                attr = doc.createAttribute("skipfailedinvocationcounts");
		                attr.setValue("false");
		                test.setAttributeNode(attr);
		                
		                
		                
		             // parameter element
		        		Element parameter = doc.createElement("parameter");
		        		test.appendChild(parameter);

		                // setting attribute to parameter element
		                attr = doc.createAttribute("name");
		                attr.setValue("test_param");
		                parameter.setAttributeNode(attr);
		                
		                attr = doc.createAttribute("value");
		                attr.setValue(testDataWithYesOption[j][0]);
		                parameter.setAttributeNode(attr);
		                
		                // test classes element
		                Element classes = doc.createElement("classes");
		                test.appendChild(classes);

		             // test class element
		                Element classss = doc.createElement("class");
		                classes.appendChild(classss);
		                
		                attr = doc.createAttribute("name");
		                attr.setValue("com.adp.bi.tests.TestWorkFrontJobs");
		                classss.setAttributeNode(attr);
		              

		             // test method element
		                Element methods = doc.createElement("methods");
		                classes.appendChild(methods);
		                
		                // test include element
		                Element include = doc.createElement("include");
		                methods.appendChild(include);
		                
		                attr = doc.createAttribute("name");
		                attr.setValue(testCaseForExecution.get(i));
		                include.setAttributeNode(attr);

		        		
			        }
		         
		        }
		         
		         // carname element
		         /*Element carname = doc.createElement("carname");
		         Attr attrType = doc.createAttribute("type");
		         attrType.setValue("formula one");
		         carname.setAttributeNode(attrType);
		         carname.appendChild(doc.createTextNode("Ferrari 101"));
		         supercar.appendChild(carname);

		         Element carname1 = doc.createElement("carname");
		         Attr attrType1 = doc.createAttribute("type");
		         attrType1.setValue("sports");
		         carname1.setAttributeNode(attrType1);
		         carname1.appendChild(doc.createTextNode("Ferrari 202"));
		         supercar.appendChild(carname1);
		*/
		         // write the content into xml file
		         TransformerFactory transformerFactory = TransformerFactory.newInstance();
		         Transformer transformer = transformerFactory.newTransformer();
		         DOMSource source = new DOMSource(doc);
		         StreamResult result = new StreamResult(new File(parellelTestngXML));
		         transformer.transform(source, result);
		         
		         // Output to console for testing
		         StreamResult consoleResult = new StreamResult(System.out);
		         transformer.transform(source, consoleResult);
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   
			//Old Code
			/*try {

				// Get test case name with 'Yes' Options and store in to test
				// execution array
				ArrayList<String> testCaseForExecution = new ArrayList<>();
				for (int i = 0; i < mainExcel.length; i++) {
					testCaseForExecution.add(mainExcel[i][2]);
				}

				// Read Test Ng XML file

				File inputFile = new File(Constants.parellelTestngXML);
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(inputFile);

				// Update Parallel Mode
				NodeList nodeList = doc.getElementsByTagName("methods");

				for (int i = 0; i < nodeList.getLength(); i++) {
					Element element = (Element) nodeList.item(i);
					NodeList list1 = element.getChildNodes();
					Node node1 = list1.item(1);
					if (node1.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) node1;
						if (testCaseForExecution.contains(eElement.getAttribute("name"))) {
							doc.renameNode(list1.item(1), "", "include");
						} else {
							doc.renameNode(list1.item(1), "", "exclude");
						}
					}
				}

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult consoleResult = new StreamResult(new File(Constants.parellelTestngXML));
				transformer.transform(source, consoleResult);
				logger.info("Execution XML file updated successfully...");
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}

		// If execution is sequential
		else if (RunTest.testconfig.get("parallel_Mode").equalsIgnoreCase("off")) {
			try {

				// Get test case name with 'Yes' Options and store in to test
				// execution array
				ArrayList<String> testCaseForExecution = new ArrayList<>();
				for (int i = 0; i < mainExcel.length; i++) {
					testCaseForExecution.add(mainExcel[i][2]);
				}

				// Read Test Ng XML file

				File inputFile = new File(Constants.testngXML);
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(inputFile);

				// Update test case for execution Mode
				NodeList nodeList = doc.getElementsByTagName("methods");

				for (int i = 0; i < nodeList.getLength(); i++) {
					Element element = (Element) nodeList.item(i);
					NodeList list1 = element.getChildNodes();

					for (int j = 0; j < list1.getLength(); j++) {
						Node node1 = list1.item(j);
						if (node1.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) node1;
							if (testCaseForExecution.contains(eElement.getAttribute("name"))) {
								doc.renameNode(list1.item(j), "", "include");
							} else {
								doc.renameNode(list1.item(j), "", "exclude");
							}
						}
					}
				}

				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult consoleResult = new StreamResult(new File(Constants.testngXML));
				transformer.transform(source, consoleResult);
				logger.info("Execution XML file updated successfully...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void configReport() {
		logger.info("Setting report properties");
		{
			System.setProperty("atu.reporter.config", Constants.atuReportProperties);
		}
		logger.info("Report Properties set..");

	}
}
