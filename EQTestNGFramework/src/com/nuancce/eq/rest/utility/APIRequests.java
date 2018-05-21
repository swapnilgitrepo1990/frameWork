package com.nuancce.eq.rest.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.apache.bcel.classfile.Constant;
import org.json.JSONObject;
import org.testng.Assert;
import com.nuance.eq.main.config.Constants;
import com.nuance.eq.main.config.RunTest;
import com.nuance.eq.tests.TesEQ;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIRequests {

	public static String get(String apiName)
	{
		RestAssured.baseURI = RunTest.testconfig.get("API_BASE_URL");

		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation().header("Accept","application/json");
 		Response response = httpRequest.get(apiName);
 		String responseBody = response.getBody().asString();
 		System.out.println("In GET function status code="+response.getStatusCode());
 		System.out.println("Response="+response);
 		ATUReports.add("After put get response="+response,LogAs.INFO, null);
 		return responseBody;	
	}
	
	public static int put(String apiName , Map<String, String> hashMap)
	{
		RestAssured.baseURI = RunTest.testconfig.get("API_BASE_URL");
		String responseBody=get(apiName);
		
		//RequestSpecification httpRequest = RestAssured.given().auth().basic("jj", "jj").given().get("/uri");
		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation().header("Accept","application/json").header("Content-Type","application/json");
		RequestSpecification httpRequest = RestAssured.given().when().
		JSONObject requestParams = new JSONObject(responseBody);
		
		for (Map.Entry<String, String> entry : hashMap.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))
		    {
		    	Boolean b = Boolean.valueOf(value);
		    	requestParams.put(key, b);
		    }else {
		    requestParams.put(key, value);
		    }
		}
		
		httpRequest.body(requestParams.toString());
		Response response1  = httpRequest.put(apiName);
		
		int statusCode = response1.getStatusCode();
		
		if(statusCode!=200)
		{
			ATUReports.add("Got invalid response code ="+statusCode,LogAs.FAILED, null);	
			Assert.fail("Invalid response code="+statusCode);
		}else {
			ATUReports.add("Status Code is="+statusCode,LogAs.INFO, null);	
			responseBody=get(apiName);
			ATUReports.add("After put get response="+responseBody,LogAs.INFO, null);
		}
		return statusCode;	
	}
	
	public static int putWithExternalJson(String fileName,String apiName , Map<String, String> hashMap)
	{
		//JSONParser parser = new JSONParser();
		// TODO Auto-generated method stub
		String text_JSON = null;
		try {
			text_JSON = new String(Files.readAllBytes(Paths.get(Constants.requests_API+fileName+".json")), StandardCharsets.UTF_8);
		} catch (IOException e) {
			Assert.fail("Not able to read JSON file "+Constants.requests_API+fileName);
			e.printStackTrace();
		}

        RestAssured.baseURI = RunTest.testconfig.get("API_BASE_URL");
		String responseBody=text_JSON;
				
		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation().header("Accept","application/json").header("Content-Type","application/json");
		JSONObject requestParams = new JSONObject(responseBody);
		
		for (Map.Entry<String, String> entry : hashMap.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))
		    {
		    	Boolean b = Boolean.valueOf(value);
		    	requestParams.put(key, b);
		    }else {
		    requestParams.put(key, value);
		    }
		}
		
		httpRequest.body(requestParams.toString());
		Response response1  = httpRequest.put(apiName);
		int statusCode = response1.getStatusCode();
		
		if(statusCode!=200)
		{
			ATUReports.add("Got invalid response code ="+statusCode,LogAs.FAILED, null);	
			Assert.fail("Invalid response code="+statusCode);
		}else {
			ATUReports.add("Status Code is="+statusCode,LogAs.INFO, null);	
			responseBody=get(apiName);
			ATUReports.add("After put get response="+responseBody,LogAs.INFO, null);
		}
		return statusCode;	
	}
	
}
