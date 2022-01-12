package ApiUtilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.bcel.classfile.ConstantNameAndType;

import Pages.Constant;
import Utilities.Utilities;

public class beforeApiScenarios {

	public static  void beforeScenarioConfig() throws Throwable {
		Constant.Api_Testdata=ExcelHelper.getData();
		//Generate UUID and put it in a map
		OtherUtility.addAdditionalTestData();
		if (Constant.Api_Testdata.containsKey("APIName_V1")) {
			String[] temp = Constant.Api_Testdata.get("APIName_V1").split(" ");
			String apiName = temp[1];
			Constant.apiName = temp[1].substring(temp[1].indexOf("/v")+3, temp[1].length());
		      if (apiName.contains("?")) {
		    	  Constant.apiName = Constant.apiName.substring(0, Constant.apiName.indexOf('?'));
			}
		
		      Constant.nameAPIURI=  constructAPIURI(temp[1].trim());
		      Constant.apiMethodName = temp[0].trim();
			}
		//Adding Today(MM:dd:yyyy) or 3digiteand# this parameter values to the Testdata map
		for (String key : Constant.Api_Testdata.keySet()) {
			OtherUtility.updateTestdata(key);
		}
		
		if (Constant.Api_Testdata.containsKey("setUpRequired") && Constant.Api_Testdata.get("setUpRequired").contains("true")) {
			RestUtility.restCallSetupMethod(Constant.Scenario, Constant.Api_Testdata);
		}
		Constant.fromTimeBeforeScenario = new SimpleDateFormat("ss S").format(new Date());
		
		
		
	}
	
	
	
	
	
	
	
	private static String constructAPIURI(String apiName) {
		
 String completeUrlSuffix=Utilities.ReadFromPropertyFile(Constant.Envirionment+"_API_URL")+apiName;
return completeUrlSuffix;
	}
}






