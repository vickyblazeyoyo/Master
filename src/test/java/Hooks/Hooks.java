package Hooks;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import Pages.Browser;
import Pages.Constant;
import Utilities.LoggerUtility;
import Utilities.TestrailUtility;
import Utilities.Utilities;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks  {
	Scenario scenario;
	@Before
	public  void setup(Scenario scenario) throws FileNotFoundException, IOException {
		this.scenario=scenario;
		Constant.Scenario=scenario.getName().toString();

		LoggerUtility.LoggerScenario(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Scenario Execution Started :"+Constant.Scenario);
		Utilities.ReadDataFromExcelPutItInMap();
		System.out.println("Hooks class");
		 new Browser(Constant.driver).BrowserLaunch();
	}
	
	@After
	public void tearDown(Scenario scenario) throws Throwable {
		this.scenario=scenario;
		Constant.Testdata.clear();
		Constant.multipleDataSetup.clear();
		if (scenario.isFailed()) {
			TakesScreenshot tk = (TakesScreenshot)Constant.driver;
			byte[] src=tk.getScreenshotAs(OutputType.BYTES);
			scenario.attach(src, "image/png", "Screenshot"+Utilities.getTimeStamp("MM-dd-yy-HH-mm-ss"));
			String rawFeatureName = scenario.getId().split(";")[0].replace("-"," ");
			Constant.Failiue_Scenarios_Features.put("FeatureName---> "+rawFeatureName,"ScenarioName--->"+ scenario.getName());
			//TestrailUtility.updateTheResultTestrail("5");
		}else {
			//TestrailUtility.updateTheResultTestrail("1");
		}
		new Browser(Constant.driver).BrowserQuit();
		LoggerUtility.LoggerScenario(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Scenario Execution Ended :"+scenario.getName());
		
	}
	

	

}

