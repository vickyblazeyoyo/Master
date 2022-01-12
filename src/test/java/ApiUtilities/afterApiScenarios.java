package ApiUtilities;

import Pages.Constant;
import io.cucumber.java.Scenario;

public class afterApiScenarios {
	public static void afterScenarioConfig(Scenario scenario) throws Throwable {
		if (Constant.Api_Testdata.containsKey(Constant.testDownReq) && Constant.Api_Testdata.get(Constant.testDownReq).contains("true")) {
			
			RestUtility.restCallTearDownMethod(Constant.Scenario, Constant.Api_Testdata);
		}
	}
	
	
	
	
}
