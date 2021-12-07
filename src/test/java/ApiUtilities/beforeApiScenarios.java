package ApiUtilities;

import java.io.IOException;

import Pages.Constant;

public class beforeApiScenarios {

	public static  void beforeScenarioConfig() throws IOException {
		Constant.Api_Testdata=ExcelHelper.getData();
		
	}
	
}
