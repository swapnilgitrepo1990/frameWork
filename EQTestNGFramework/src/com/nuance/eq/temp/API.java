package com.nuance.eq.temp;




import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.nuance.eq.main.config.Constants;
import com.nuance.eq.main.config.RunTest;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Map;
import java.util.Scanner;

import static org.hamcrest.Matchers.*;
public class API {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String text = new String(Files.readAllBytes(Paths.get("C:\\Users\\swapnil.sonawane2\\eclipse-workspace\\EQTestNGFramework\\APIRequests\\AD_Server_Properties.json")), StandardCharsets.UTF_8);

        JSONObject obj = new JSONObject(text);
        System.out.println(obj.toString());
        putWithExternalJson(text,"AD");
        

	}
	
	public static int putWithExternalJson(String fileName,String apiName )
	{
		//JSONParser parser = new JSONParser();
		// TODO Auto-generated method stub
		String text_JSON = null;
		text_JSON = fileName;

        RestAssured.baseURI = RunTest.testconfig.get("API_BASE_URL");
		String responseBody=text_JSON;
				
		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation().header("Accept","application/json").header("Content-Type","application/json");
		JSONObject requestParams = new JSONObject(responseBody);
		 System.out.println(requestParams.get(""));
//		for (Map.Entry<String, String> entry : hashMap.entrySet()) {
//		    String key = entry.getKey();
//		    String value = entry.getValue();
//		    
//		    if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))
//		    {
//		    	Boolean b = Boolean.valueOf(value);
//		    	requestParams.put(key, b);
//		    }else {
//		    requestParams.put(key, value);
//		    }
//		}
		
		httpRequest.body(requestParams.toString());
		Response response1  = httpRequest.put(apiName);
		int statusCode = response1.getStatusCode();
		
		
		return statusCode;	
	}
	
	
			
}
