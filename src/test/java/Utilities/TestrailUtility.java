package Utilities;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.JsonArray;
import com.gurock.testrail.APIClient;
import com.gurock.testrail.APIException;

import Pages.Constant;


public class TestrailUtility   {

	
	
	public static void updateTheResultTestrail(String StatusID ) throws MalformedURLException, IOException, APIException {
	getSuitId();
	getRunID();
	getTestcaseID();
	APIClient cl=new APIClient(Constant.TestRailUrl);
	cl.setUser("Vickyyamini1995@gmail.com");
	cl.setPassword("Vickyyamini1995@");
		Map<String,String> data=new HashMap<String,String>();
		data.put("status_id", StatusID);
		if (StatusID.equals("1")) {
			data.put("comment", Constant.Scenario+" Automation Execution:PASSED");	
		}else {
			data.put("comment", Constant.Scenario+" Automation Execution:FAILED JiraDefect_ID:JR11425");
	
		}
		cl.sendPost("add_result_for_case/"+Constant.TestRun_ID+"/"+Constant.TestCase_ID+"",data);
		}	

public static void getSuitId() throws MalformedURLException, IOException, APIException {
	APIClient cl=new APIClient(Constant.TestRailUrl);
	cl.setUser("Vickyyamini1995@gmail.com");
	cl.setPassword("Vickyyamini1995@");
	
	String obj=cl.sendGet("get_suites/1").toString();
	JSONArray suitarray=new JSONArray(obj);
	 for (Object object : suitarray) {
		   JSONObject splitedJson = new JSONObject(object.toString());
			String name=splitedJson.get("name").toString();
			if (name.equals(Constant.SplitedFeature)) {
				Constant.TestSuit_ID=splitedJson.get("id").toString();
                break;
			}
	}
}
	public static  void getRunID() throws MalformedURLException, IOException, APIException {
		APIClient cl=new APIClient(Constant.TestRailUrl);
		cl.setUser("Vickyyamini1995@gmail.com");
		cl.setPassword("Vickyyamini1995@");
		String obj=cl.sendGet("get_runs/1").toString();
	    JSONObject objJson = new JSONObject(obj);
	   JSONArray arrayJsonObj= objJson.getJSONArray("runs");
	   for (Object object : arrayJsonObj) {
		   JSONObject splitedJson = new JSONObject(object.toString());
			String name=splitedJson.get("name").toString();
			if (name.equals(Constant.SplitedFeature)) {
				Constant.TestRun_ID=splitedJson.get("id").toString();

			}
	}
	  

	}
	
	public static  void getTestcaseID() throws MalformedURLException, IOException, APIException {
		APIClient cl=new APIClient(Constant.TestRailUrl);
		cl.setUser("Vickyyamini1995@gmail.com");
		cl.setPassword("Vickyyamini1995@");
		String obj=cl.sendGet("get_cases/1&suite_id=1").toString();
		JSONObject objJson = new JSONObject(obj);
		   JSONArray arrayJsonObj= objJson.getJSONArray("cases");
		   for (Object object : arrayJsonObj) {
			   JSONObject splitedJson = new JSONObject(object.toString());
				String Title=splitedJson.get("title").toString();
				if (Title.equals(Constant.Scenario)) {
					Constant.TestCase_ID=splitedJson.get("id").toString();
                    break;
				}
		}
		  
	}
}
