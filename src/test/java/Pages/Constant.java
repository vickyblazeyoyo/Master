package Pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.springframework.util.LinkedCaseInsensitiveMap;

import ApiUtilities.Response;

public class Constant {
	//Scenario constants:
public static Scenario scenario;
public static String Scenario=null,SplitedFeature;

//Common Constants
public static String Envirionment;



//Testrail Constants:
public static String TestRailUrl,TestRail_AUTHKEY,TestSuit_ID,TestRun_ID,TestCase_ID;

//Jira Constants
public static String JiraDefectID;

//Twilio Api Constant:
public static final String Acct_SSID= "AC2d5c48e9195f539cad6ffb46247c88ed";
public static final String Auth_Token= "50d83f00be7c5c95a40345562a2ee122";

//UI Execution Constants:
public 	static WebDriver driver=null;
public static LinkedHashMap<String, String> Testdata=new LinkedHashMap<String, String>();
public static LinkedHashMap<String, String> Failiue_Scenarios_Features=new LinkedHashMap<String, String>();
public static LinkedHashMap<String, String[]> multipleDataSetup=new LinkedHashMap<String, String[]>(); ;


//API Execution Constants:
public static Map<String,String> Api_Testdata=new LinkedCaseInsensitiveMap<String>();
public static String apiName,respCode,apiMethodName,SetUpTearDownAPIName,setUpTearDownAPIURI,stAPIMethodName,nameAPIURI,fromTime,toTime,CURL,responseBody,execTime,fromTimeBeforeScenario;
public static Map<String,String> requestHeader=new HashMap<String, String>();
public static Response res=null,setUpTearDownresp;
public static String setUpReq="setUpRequired", testDownReq="tearDownRequired";
public static Integer totalCount=0;

}
   