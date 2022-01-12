package ApiUtilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.bcel.Constants;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import Pages.Constant;
import Utilities.Utilities;
import net.minidev.json.JSONArray;

public class RestUtility {
	
	//This method will execute for setupTeardown
	public static void restCallSetupMethod(String ScenarioName, Map<String, String> testData) throws Throwable {

	for (int i = 1; i < 6; i++) {
		if (testData.containsKey("setUp"+i+"Prefix_"+"V1")) {
			String[] temp = testData.get("setUp"+i+"Prefix"+"_V1").trim().split(" ");
			Constant.SetUpTearDownAPIName = temp[1];
			Constant.setUpTearDownAPIURI = getURLSuffix() + Constant.SetUpTearDownAPIName.toString().trim();
			Constant.stAPIMethodName = temp[0].trim();
			//This helps to take path parameter fields
			String[] setupTearDwURLFields = StringUtils.substringsBetween(Constant.setUpTearDownAPIURI, "{", "}");
           if (setupTearDwURLFields!=null) {
    	  for(int k=0;k<setupTearDwURLFields.length;k++){
				if (!Constant.Api_Testdata.containsKey(setupTearDwURLFields[k])){
					//If the Field is not inthe Map then this will execute for quary parameter replacement
					OtherUtility.getResponseAndUpdateTestData(setupTearDwURLFields[k]);
					System.out.println(Constant.Api_Testdata.get(setupTearDwURLFields[k]));
					
				}
				
			}
    	  //If the String is in the map this method will execute for Quary parameter replacement
    	  Constant.setUpTearDownAPIURI = OtherUtility.updateFieldInAPIURI(setupTearDwURLFields, Constant.setUpTearDownAPIURI, "{}");
	     }
			executeSetUpTearDownAPI(Integer.toString(i), Constant.setUpTearDownAPIURI, "setUp");

		}
	}
	
	}
	
	//This Method will execute TearDown same like Setup
	public static void restCallTearDownMethod(String ScenarioName, Map<String, String> testData) throws Throwable {
		for (int i=1; i<6; i++){
			if (testData.containsKey("tearDown"+i+"Prefix_V1")){
				
				String[] temp = testData.get("tearDown"+i+"Prefix_V1").trim().split(" ");
			    Constant.SetUpTearDownAPIName = temp[1];
				Constant.setUpTearDownAPIURI = getURLSuffix() + Constant.SetUpTearDownAPIName.trim();
				Constant.stAPIMethodName = temp[0].trim();
				String[] setupTearDwURLFields = StringUtils.substringsBetween(Constant.setUpTearDownAPIURI, "{", "}");
				if (setupTearDwURLFields!=null){
					for(int k=0;k<setupTearDwURLFields.length;k++){
						if (!Constant.Api_Testdata.containsKey(setupTearDwURLFields[k])){
							OtherUtility.getResponseAndUpdateTestData(setupTearDwURLFields[k]);
						} 
					}
					Constant.setUpTearDownAPIURI = OtherUtility.updateFieldInAPIURI(setupTearDwURLFields, Constant.setUpTearDownAPIURI, "{}");
				
				}
				executeSetUpTearDownAPI(Integer.toString(i), testData.get("tearDown"+i+"Prefix"), "tearDown");
			}
		}

	}
	
	
	
	
	
	//This method enters only with Quary parameter updated if it has
	//json Update and execution will happen in this method
	public static void executeSetUpTearDownAPI(String itr, String url, String flag) throws Throwable {
		String jsonField = null;
		Set <String> jsonBodyArr = new HashSet<String>();
		addHeaderToAPISetupTearDown();
		System.out.println("Update URI : " + Constant.setUpTearDownAPIURI);
		System.out.println("apiMethodName : " + Constant.stAPIMethodName);
        if (flag.equals("setUp")) {
        	jsonField = "setUp" + itr + "Json_V1";
       } else if (flag.equals("tearDown")){
			jsonField = "tearDown" + itr + "Json_V1";
		}
        
        try {
        	Constant.fromTime = new SimpleDateFormat("ss S").format(new Date());
        	//This condision checks wheather Api has json or not if it has json then this will execute
        	//or else will execute
        	if (Constant.Api_Testdata.containsKey(jsonField) && jsonField!=null) {
				//TearDown Execution for json parameter
        		if (flag.equals("tearDown")) {
					 String[] tearDwJsonFields = StringUtils.substringsBetween(Constant.Api_Testdata.get(jsonField), "<", ">");
					 if (tearDwJsonFields!=null) {
						for (int k=0;k<tearDwJsonFields.length;k++) {
							if (!Constant.Api_Testdata.containsKey(tearDwJsonFields[k])) {
								OtherUtility.getAPIResponseAndUpdateTestData(tearDwJsonFields[k]);
								System.out.println(tearDwJsonFields[k] + " : " + Constant.Api_Testdata.get(tearDwJsonFields[k]));
							}
						}
					}
				}
			//passing map key of json field in the map eg=setUp1Json_V1,APIJson_V1
        	 updateAPIJSONData(jsonField);
			 String data =  Constant.Api_Testdata.get(jsonField).toString().trim();
			 System.out.println("API Json :");
			 System.out.println(data);
			 System.out.println("Update URI : " + Constant.setUpTearDownAPIURI);
			 System.out.println(flag + itr + " CurL command : " + constructCURLCommand(Constant.stAPIMethodName, Constant.setUpTearDownAPIURI, Constant.requestHeader, data));
			 Constant.setUpTearDownresp = RestConnector.doHttp(Constant.stAPIMethodName, Constant.setUpTearDownAPIURI, null, data.getBytes(), Constant.requestHeader, null);
        	}else {
				 System.out.println(flag + itr + " CurL command : " + constructCURLCommand(Constant.stAPIMethodName, Constant.setUpTearDownAPIURI, Constant.requestHeader));
				 Constant.setUpTearDownresp = RestConnector.doHttp(Constant.stAPIMethodName, Constant.setUpTearDownAPIURI, null, null, Constant.requestHeader, null);	 

			}
        	Constant.toTime = new SimpleDateFormat("ss S").format(new Date());
        	Constant.execTime = Utilities.getTimeDiff(Constant.fromTime, Constant.toTime);
        	String responseBody = new String(Constant.setUpTearDownresp.getResponseData());
    		responseBody = OtherUtility.toPrettyFormat(responseBody);
            if (flag.equals("setUp")) {
            	jsonBodyArr.add("APIJson_V1");
            	//This condition Checks wheather setup2 is having json or not
            	if (Constant.Api_Testdata.containsKey("setUp"+Integer.toString(Integer.parseInt(itr)+1)+"Json_V1")) {
            		jsonBodyArr.add("setUp"+Integer.toString(Integer.parseInt(itr)+1)+"Json_V1");
            	}
            		
            		System.out.println("Response code in setUp : " + Constant.setUpTearDownresp.getStatusCode());
        			System.out.println("Response header in setUp: " + Constant.setUpTearDownresp.getResponseHeaders());
        			System.out.println("Response body in setUp: " +responseBody);
        			
        			//Get value from the response if needed and update in testdata map and also the json parameter for Main Api
        			updateTestDataFields();
        			//This Method will Check only if there is parameter value is there in the map or not
        			//If not Then it will execute getResponseFromResponse Method flow
        			updateMainAPIURIFields();
        			//This method will only Update setup2 Json parameter
        			updateSetUpJsonFields(jsonBodyArr);
            	}else {
        			
            		//Get value from the response if needed and update in testdata map and also the json parameter for Main Api
                    updateTestDataFields();
        			updateMainAPIURIFields();
        			updateSetUpJsonFields(jsonBodyArr);
        			System.out.println("Response body in tearDown: " +responseBody);
        			System.out.println("Response code in tearDown : " + Constant.setUpTearDownresp.getStatusCode());
				}
			}catch (Exception e) {
	        	e.printStackTrace();
			}
         } 
        
	
	//Adding Header to the api
	public static void addHeaderToAPISetupTearDown() {
		for (String key : Constant.Api_Testdata.keySet()) {
			if (key.startsWith("H_")) {
				Constant.requestHeader.put(key.substring(2, key.length()), Constant.Api_Testdata.get(key).toString().trim());
			}
		}

	}
	
	public static  String getURLSuffix() {
		String urlstring=Utilities.ReadFromPropertyFile("Environment")+"_API_URL";
		return urlstring;
		
	}
	//Argument we are passing like json key word:eg=setUp1Json_V1,APIJson_V1
	//Action:Taking json from the map using key as (jsonName) and also specifying the pattern of the parameter <>
	public static  void updateAPIJSONData(String jsonName) throws UnsupportedEncodingException {
		String data;
		if (Constant.Api_Testdata.containsKey(jsonName) && !Constant.Api_Testdata.get(jsonName).trim().equals("<Entity>")) {
			//taking json from the map
			data = Constant.Api_Testdata.get(jsonName).toString().trim();
			String[] bodyFields = StringUtils.substringsBetween(data, "<", ">");
			if (bodyFields!=null) {
				//This method will replace this field <DpAcct>  with actual data in the json request
				data = OtherUtility.updateFieldInAPIURI(bodyFields, data,"<>");
			}
		}

	}
	//Update Field parameter in the API_testdata map eg:plnTs=<plnTs> This field will be updated in the map
	//And also it will Update json Parameter in the main Api
	public static  void updateTestDataFields() throws ParserConfigurationException, SAXException, IOException {
		Boolean jsonReplace;
		//Taking Keyset from API_Testdata and put it in array
		String[] testDataArr = Constant.Api_Testdata.keySet().toArray(new String[Constant.Api_Testdata.keySet().size()]);
	    for (String key : testDataArr) {
			if (!key.contains("Json")) {
				//This will take excel parameter fields eg:plnTs=<plnTs>
				String[] jsonBodyFields = StringUtils.substringsBetween(Constant.Api_Testdata.get(key), "<", ">");
				if (jsonBodyFields!=null) {
					//Taking <> parameter field from the previous response and update the map
					jsonReplace = OtherUtility.getResponseAndUpdateTestData(jsonBodyFields[0]);
					if (jsonReplace) {
						Constant.Api_Testdata.put(key, OtherUtility.updateFieldInAPIURI(jsonBodyFields, Constant.Api_Testdata.get(key), "<>"));
					}
				}
			}
		}
	}
	
	//This Method will take all the Quary Parameter and Checks wheather it has value for the key in Api_TestData Map
	//If it is there it will come out or else it will call (getResponseAndUpdateTestData) this flow
	public static  void updateMainAPIURIFields() throws ParserConfigurationException, SAXException, IOException {

		String[] URIFields = StringUtils.substringsBetween(Constant.nameAPIURI, "{", "}");
		if (URIFields!=null) {
			for (int k = 0; k < URIFields.length; k++) {
				if (!Constant.Api_Testdata.containsKey(URIFields[k])) {
					OtherUtility.getResponseAndUpdateTestData(URIFields[k]);
				}
			}
		}
		
		}
	
	//Arguments:we are passing parameter Values in the Set like =<AcctNbr>,<PlnNbr> 
	//This Method will only update the Json Parameter for Setup2Json and put it in a map
	public static  void updateSetUpJsonFields(Set<String> jsonBodyArr) throws ParserConfigurationException, SAXException, IOException {
		Boolean jsonReplace = false;
		for (String item : jsonBodyArr) {
			String[] jsonBodyFields = StringUtils.substringsBetween(Constant.Api_Testdata.get(item), "<", ">");
			if (jsonBodyFields!=null) {
				for (int k=0;k<jsonBodyFields.length;k++) {
					if (!Constant.Api_Testdata.containsKey(jsonBodyFields[k])){
						jsonReplace = OtherUtility.getResponseAndUpdateTestData(jsonBodyFields[k]);
						
					}
				}
				if (jsonReplace && !item.equals("APIJson")){
					Constant.Api_Testdata.put(item, OtherUtility.updateFieldInAPIURI(jsonBodyFields, Constant.Api_Testdata.get(item), "<>"));
				}
			}
		}

	}
	
	//Construct Curl on data along with the header (Part-2)
	public static String constructCURLCommand(String apiMethodName, String nameAPIURI,	Map<String, String> requestHeader, String data) {
		StringBuilder builder = new StringBuilder();
		String curl = constructCURLCommand(apiMethodName, nameAPIURI, requestHeader);
		curl = curl + " -d \"" + data.replaceAll("\\r\\n|\\r|\\n", "").replaceAll("\"", "\\\\\"").replaceAll("\\s+", " ") + "\"";
		return curl;

	}
	
	//Construct curl on Header part (Part-1)
	public static String constructCURLCommand(String apiMethodName, String nameAPIURI,	Map<String, String> requestHeader) {
		String curl;
		String header = ""; 
		for (Map.Entry<String,String> entry : requestHeader.entrySet()) {
			header = header + " -H \"" + entry.getKey() + ": " + entry.getValue() + "\"";
		}
		curl = "curl -X " + Constant.apiMethodName.trim().toUpperCase() + " \"" + Constant.nameAPIURI+ "\"" + header;
		return curl;
	}
	
	//Main Api execution hit the aoi and get the response
	public static  void executeAPIAndGetResponse(String apiMethod) throws Throwable {
		String data;
		ArrayList<String> keyList = new ArrayList<String>();
		//Adding Header to the main api
		addHeaderToAPI();
		//Taking Quarry parameter of main Api
		String[] URIFields = StringUtils.substringsBetween(Constant.nameAPIURI, "{", "}");
         if (URIFields!=null) {
        	 Constant.nameAPIURI = OtherUtility.updateFieldInAPIURI(URIFields, Constant.nameAPIURI, "{}");
 			Constant.nameAPIURI = Constant.nameAPIURI.trim().replaceAll(" ", "%20");
		}
         
         for (String key : Constant.Api_Testdata.keySet()) {
			if (!key.contains("Json_V1")) {
				if (key.contains("_V") && key.contains("V1")) {
					System.out.println(key + ": " + Constant.Api_Testdata.get(key));
				}
			}
		}
         
        try {
			if (Constant.Api_Testdata.containsKey("APIJson_V1")) {
				if (!Constant.Api_Testdata.containsKey("setUpRequired")) {
					updateAPIJSONData("APIJson_V1");	
					
				}
				data = Constant.Api_Testdata.get("APIJson_V1");
				 if (data.equals("<Entity>")) {
					 String respBody = new String(Constant.setUpTearDownresp.getResponseData()).trim();
					 DocumentContext jsonContext = JsonPath.parse(respBody);	
					 ArrayList<String> jsonpathObjectLocation = new ArrayList<String>();
					 jsonpathObjectLocation = jsonContext.read("$..Entity");
					 JSONArray val1=jsonContext.read("$..Entity");
					 System.out.println(val1.toString());
					 data = val1.toString().substring(1, val1.toString().length()-1);
					 //System.out.println(val1.toJSONString().substring(1, val1.toJSONString().length()-1));
					 System.out.println(data);
				 }
				 printRequestHeader();
				 PrintURIAndAPIMethod();
				 Constant.CURL = constructCURLCommand(Constant.apiMethodName, Constant.nameAPIURI, Constant.requestHeader, data);
				 Constant.fromTime = new SimpleDateFormat("ss S").format(new Date());
				 Constant.res = RestConnector.doHttp(Constant.apiMethodName, Constant.nameAPIURI, null, data.getBytes(), Constant.requestHeader, null);
			}else {
				 printRequestHeader();
				 PrintURIAndAPIMethod();
				 Constant.CURL = constructCURLCommand(Constant.apiMethodName, Constant.nameAPIURI, Constant.requestHeader);
				 Constant.fromTime = new SimpleDateFormat("ss S").format(new Date());
				 Constant.res = RestConnector.doHttp(Constant.apiMethodName, Constant.nameAPIURI, null, null, Constant.requestHeader, null);	
			}
			Constant.toTime = new SimpleDateFormat("ss S").format(new Date());
			Constant.execTime = Utilities.getTimeDiff(Constant.fromTime, Constant.toTime);
			System.out.println("Response code : " + Constant.res.getStatusCode());
			System.out.println("Response header : " + Constant.res.getResponseHeaders());
			System.out.println("CURL Command : " + Constant.CURL);
			if (Constant.res.getResponseData().toString()==null || Constant.res.getResponseData().toString().isEmpty() || Constant.res.getStatusCode()==204) {
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				System.out.println("$$$$$$$$$$$$ Response body NOT applicable to this API $$$$$$$$$$$$");
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			}else {
				System.out.println("Response body : " + Constant.res.getResponseData().toString());
				Constant.responseBody = OtherUtility.toPrettyFormat(Constant.res.getResponseData().toString());
			System.out.println("Response body : " + Constant.responseBody);
			}
			if (Constant.res.getStatusCode()==200 && Constant.responseBody!=null){
				
				updateTestDataFields();
                updateTearDownAPIURIFields();
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		} 
         
         
         
         
         
	}
	
	//This Method will add all the required headers to the header map for main API
	public static  void addHeaderToAPI() {
		Constant.requestHeader.clear();
		for (String key : Constant.Api_Testdata.keySet()) {
			if (key.startsWith("H_")){
				Constant.requestHeader.put(key.substring(2, key.length()), Constant.Api_Testdata.get(key).toString().trim());
			}
		}
		Constant.requestHeader.put("uuid", Constant.Api_Testdata.get("uuid").toString().trim());

	}
	
	//This Method will print all the headers
	public static  void printRequestHeader() {
		for (String key : Constant.requestHeader.keySet()) {
			System.out.println(key + " : " + Constant.requestHeader.get(key));
		}

	}
	
	//This method will print Method Name and URI
	public static  void PrintURIAndAPIMethod() {
		System.out.println("Updated URI : " + Constant.nameAPIURI);
		System.out.println("apiMethodName : " + Constant.apiMethodName);

	}
	
	//This Method will Update Teardown URI parameter in the map
	public static void updateTearDownAPIURIFields() throws ParserConfigurationException, SAXException, IOException {
		if (Constant.Api_Testdata.containsKey("tearDown1Prefix")) {
			String tearDownURI = Constant.Api_Testdata.get("tearDown1Prefix");
			String[] URIFields = StringUtils.substringsBetween(tearDownURI, "{", "}");
			if (URIFields!=null) {
				for (int k=0;k<URIFields.length;k++) {
					if (!Constant.Api_Testdata.containsKey(URIFields[k])) {
						OtherUtility.getResponseAndUpdateTestData(URIFields[k]);
						System.out.println(URIFields[k] + " : " + Constant.Api_Testdata.get(URIFields[k]));
					}
				}
			}
		}

	}
	
	
}//Final class close
