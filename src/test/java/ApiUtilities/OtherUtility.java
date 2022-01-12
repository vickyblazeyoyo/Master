package ApiUtilities;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.net.URLEncoder;
import Pages.Constant;
import Utilities.Utilities;

public class OtherUtility {

	public static void addAdditionalTestData() {

		TimeBasedGenerator gen = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		UUID uuid = gen.generate();
		Constant.Api_Testdata.put("uuid", uuid.toString());
		
	}
	
	
	
	
	
	
	
	public static  void updateTestdata(String key) throws Throwable {
		String date;
		if (Constant.Api_Testdata.get(key).contains("Today(")) {
			String dateformat = Constant.Api_Testdata.get(key).trim().substring(Constant.Api_Testdata.get(key).indexOf('(')+1,Constant.Api_Testdata.get(key).indexOf(')'));
            if (Constant.Api_Testdata.get(key).startsWith("Today(") && Constant.Api_Testdata.get(key).endsWith(")")) {
	        date=Utilities.getCurrentCSTDateWithArgFormat(dateformat, 0);
	        Constant.Api_Testdata.put(key, date);
            }else {
					String days = Constant.Api_Testdata.get(key).toString().trim().substring(Constant.Api_Testdata.get(key).indexOf(')')+1, Constant.Api_Testdata.get(key).length());
					date = Utilities.getCurrentCSTDateWithArgFormat(dateformat,Integer.parseInt(days));
					  Constant.Api_Testdata.put(key, date);
			}
		}else if (Constant.Api_Testdata.get(key).contains("CurMonth()")) {
			Constant.Api_Testdata.put(key, Utilities.getCSTSpecificComponentInDate("MM").trim());
			
		}else if (Constant.Api_Testdata.get(key).contains("CurYear()")) {
			Constant.Api_Testdata.put(key, Utilities.getCSTSpecificComponentInDate("yyyy").trim());
		}else if (Constant.Api_Testdata.get(key).contains("CurDay()")) {
			Constant.Api_Testdata.put(key, Utilities.getCSTSpecificComponentInDate("dd").trim());
		}else if (Constant.Api_Testdata.get(key).contains("TimeNow()")) {
			Constant.Api_Testdata.put(key, Utilities.getTellerTimeStamp().trim());		
		}else if (Constant.Api_Testdata.get(key).contains("5digitrandNum#")){
			String fivedigrepl = Utilities.fiveDigitRandomNum().trim();
			Constant.Api_Testdata.put(key, fivedigrepl);
		}else if (Constant.Api_Testdata.get(key).contains("3digitrandNum#")){
			String fivedigrepl = Utilities.threeDigitRandomNum().trim();
			Constant.Api_Testdata.put(key, fivedigrepl);
		} else if (Constant.Api_Testdata.get(key).contains("9digitrandNum#")){
			String fivedigrepl = Utilities.get9Digit().trim();
			Constant.Api_Testdata.put(key, fivedigrepl);
		} else if (Constant.Api_Testdata.get(key).contains("ranSSN#")){
			String fivedigrepl = Utilities.genSSN().trim();
			Constant.Api_Testdata.put(key, fivedigrepl);
		} else if (Constant.Api_Testdata.get(key).contains("10digitrandNum#")){
			String tendigitnum = Utilities.get10Digit().trim();
			Constant.Api_Testdata.put(key, tendigitnum);
		} 
		
	}
	
	
	//This method will replace parameter having ({},<>) if the field is in the API_tesdtdata map
	public static String updateFieldInAPIURI(String[] uRIFields, String URLOrBoody, String pattern) throws UnsupportedEncodingException {
		Pattern SUBST_Patt = null;
		if (pattern.equals("{}")){
			SUBST_Patt = Pattern.compile("\\{(\\w+)\\}");	
		} else if (pattern.equals("<>")){
			SUBST_Patt = Pattern.compile("\\<(\\w+)\\>");
		}
		StringBuilder sb = new StringBuilder(URLOrBoody);
		Matcher m = SUBST_Patt.matcher(sb);
		int index = 0, i = 0;
		while (m.find(index)) {
			index = m.start();
			String replacement = Constant.Api_Testdata.get(uRIFields[i]);
			if (replacement==null) {
				System.out.println("Replacement value for the path/query/request body parameter : " + uRIFields[i] + " is NULL");
			}
			if (pattern.equals("{}")){
				replacement = URLEncoder.encode(replacement, "UTF-8");
				
			}
			//replacing Using Index
			sb.replace(index, m.end(), replacement);
			index = index + replacement.length();//Url Index+replacement string length 
			i++;
		}
		return(sb.toString());
	}
	
	
	public static String toPrettyFormat(String jsonString) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonString).getAsJsonObject();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(json);

		return prettyJson;
	}
	
	//********************************* Execution 1*************************************
	//If there is multiple parameter or one parameter field this method will iterate and Update using response from setup1
	//It there is no parameter then this will returen false or it will return true since it has boolean return type
	//Arguments :Individual parameter field not from json or quary parameter
	public static Boolean getResponseAndUpdateTestData(String requiredFields) throws ParserConfigurationException, SAXException, IOException {
		Boolean retrieve_val = false;
		if (requiredFields!=null) {
			if (requiredFields.contains(",")) {
				String [] reqFields = requiredFields.split(",");
				for (String field : reqFields) {
					//This method will get parameter value from the previous response
					retrieve_val = updateTestDataFromResponseField(field);
				}
			}else {
				//This method will get parameter value from the previous response
				retrieve_val = updateTestDataFromResponseField(requiredFields);
			}
			
		}
		return retrieve_val;
	}
	
	//********************************* Execution 2*************************************
	
	//This method will put updated data in the Api_Testdata map
	public static  Boolean updateTestDataFromResponseField(String field) throws ParserConfigurationException, SAXException, IOException {
		Boolean retval = false;
		//This method will get the value from the previous response 
		String fieldData = getFieldFromSetUpTearDownResponse(field);
		if (!fieldData.equals("nodata")) {
			retval = true;
			Constant.Api_Testdata.put(field, fieldData);
		}
		return retval;
	}
	
	//********************************* Execution 3*************************************

		//Argument:Field name that has parameter in the excel sheet 
		//This method will return the extracted value from the response
	public static  String getFieldFromSetUpTearDownResponse(String fieldName) throws ParserConfigurationException, SAXException, IOException {
		String retval = null;
		String responseBody;
		if ((Constant.Api_Testdata.containsKey("setUpRequired") && Constant.Api_Testdata.get("setUpRequired").contains("true"))) {
			responseBody = new String(Constant.setUpTearDownresp.getResponseData());
			retval = extractfieldValueByJsonPath(responseBody, fieldName);
			if ((retval.equals("nodata") && Constant.res != null)){
				responseBody = new String(Constant.res.getResponseData());
				retval = extractFieldValueFromXML(responseBody, fieldName);
			} 
		}else {
			responseBody = new String(Constant.res.getResponseData());
			retval = extractfieldValueByJsonPath(responseBody, fieldName);
			if ((retval.equals("nodata") && Constant.setUpTearDownresp.getResponseData() != null)) {
				responseBody = new String(Constant.setUpTearDownresp.getResponseData());
				retval = extractFieldValueFromXML(responseBody, fieldName);
			}
			
		}
		return retval;
	}
	
	//********************************* Execution 4*************************************
	
	//This is the method which will extract field value from the response body
	//Arguments:response body and field name which we want take value from the response body
	public static String extractfieldValueByJsonPath(String responseBody, String fieldName) {
		List<Object> jsonpathObjectList = null;
		String retval = "nodata";
		DocumentContext jsonContext = JsonPath.parse(responseBody.trim());
		jsonpathObjectList = jsonContext.read("$.." + fieldName);
		if (jsonpathObjectList.size()>0) {
			retval = String.valueOf(jsonpathObjectList.get(0));
		}
		return retval;
	}
	
	//********************************* Execution 4(Optional)*************************************
	
	//This method will extract the value from the response body the json will converted in to XML 
	//Extracting field Value using XML
	public static  String extractFieldValueFromXML(String responseBody, String fieldName) throws SAXException, IOException, ParserConfigurationException {

		String retval = "nodata";
		final JSONObject obj = new JSONObject(responseBody);
		String extracted_xml = XML.toString(obj);
		if (extracted_xml.contains("Entity")){
			extracted_xml = extracted_xml.substring(extracted_xml.indexOf("<Entity"), extracted_xml.indexOf("/Entity>"));
			extracted_xml = extracted_xml + "/Entity>";
		} else {
			extracted_xml = extracted_xml.substring(0, extracted_xml.indexOf("Metadata")-1);
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(extracted_xml)));
		document.getDocumentElement().normalize();
		if (document.getElementsByTagName(fieldName).getLength()>0){
			NodeList nList = document.getElementsByTagName(fieldName);
			retval = nList.item(0).getTextContent().trim();
		} 
		return retval;
	
	}

	//This method will iterate parameter fields for TEARDOWN Json parameter if the value is not in the testdata map
	//Argumet:Parameter field of teardown Jason
	public static  void getAPIResponseAndUpdateTestData(String requiredFields) {
		if (requiredFields!=null) {
			String [] reqFields = requiredFields.split(",");
			for (String field : reqFields) {
				//Getting parameter field for teardown execution from the main api execution
				String fieldData = extractfieldValueByJsonPath(Constant.res.getResponseData().toString(), field);
				Constant.Api_Testdata.put(field, fieldData);
				System.out.println(field + " : " + fieldData);
				
			}
		}

	}
	
	
	
	
	
}//Final class Clossing
