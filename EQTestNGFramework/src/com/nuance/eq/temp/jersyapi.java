package com.nuance.eq.temp;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
 

public class jersyapi {
	public static void main(String[] args)
	{
		 
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = "https://10.66.38.156/EQWebClient/SystemManager/API/";
		
		// specified in the above step.
		RequestSpecification httpRequest = RestAssured.given().relaxedHTTPSValidation().header("Accept","application/json");
 
		// Make a request to the server by specifying the method Type and the method URL.
		// This will return the Response from the server. Store the response in a variable.
		Response response = httpRequest.get("AutoPinMgmt");
 
		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody.toString());
		
		
		//POST
		RequestSpecification httpRequest1 = RestAssured.given().relaxedHTTPSValidation().header("Accept","application/json").header("Content-Type","application/json");
		JSONObject requestParams = new JSONObject(responseBody);
		
		requestParams.remove("Pin1PcManager");
		requestParams.put("Pin1PcManager",true);
		requestParams.put("Pin2PcManager",true);
		requestParams.put("PinTmpSubj","Test MSG");
		requestParams.put("PinTmpMsg","account: (%1, %2)");
		
		System.out.println(requestParams.get("Pin2PcManager"));
		System.out.println(requestParams);
		//requestParams= "{"Pin1PcManager":false,"Pin1WebReset":true,"Pin2PcManager":false,"Pin2WebReset":true,"PinTmpMsg":"A temporary primary and secondary PIN has been issued to your account: (%1, %2)","PinTmpSubj":"Temporary PIN(s)","ClearPinsOnLock":true,"Pin1MaxLen":20,"Pin1MinLen":1,"RequireSecondaryId":true}";
		
		httpRequest1.body(requestParams.toString());
		
		Response response1  = httpRequest1.put("/AutoPinMgmt");
	 
		int statusCode = response1.getStatusCode();
		System.out.println(statusCode);
		System.out.println(response1.contentType());
		System.out.println(response1.getBody());
		 response = httpRequest1.get("AutoPinMgmt");
		 
		// Now let us print the body of the message to see what response
		// we have recieved from the server
		 responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody.toString());
		
  }

}
