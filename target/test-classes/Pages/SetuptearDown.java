package Pages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebDriver;

import com.google.common.collect.Multiset.Entry;
import com.gurock.testrail.APIException;

import Utilities.LoggerUtility;
import Utilities.TestrailUtility;
import Utilities.Utilities;

public class SetuptearDown   {

	static WebDriver driver;
	
	public static  void setup() throws FileNotFoundException, IOException, APIException {
        Utilities.DeleteFiles(System.getProperty("user.dir")+"\\DownloadedImage");
		Utilities.DeleteFiles(System.getProperty("user.dir")+"\\ParticularElementScreenshot");
		Utilities.DeleteFiles(System.getProperty("user.dir")+"\\recordings");
		Utilities.DeleteFiles(System.getProperty("user.dir")+"\\Screenshots");
		Utilities.DeleteParticularFile(System.getProperty("user.dir")+"\\target", "png");
		LoggerUtility.LoggerSuite(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Suite Execution Started");
            
	Constant.TestRailUrl=Utilities.ReadFromPropertyFile("TestRailUrl");
	Constant.TestRail_AUTHKEY=Utilities.ReadFromPropertyFile("TestRail_AUTHKEY");
	
	}
	
	public static  void teardown() {
		
		LoggerUtility.LoggerSuite(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Falied Scenarios :");

		
		for (Map.Entry<String, String> failed : Constant.Failiue_Scenarios_Features.entrySet()) {
			LoggerUtility.LoggerSuite(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), failed.getKey()+" "+failed.getValue());


		}
		
		Constant.Failiue_Scenarios_Features.clear();
		
		LoggerUtility.LoggerSuite(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Suite Execution Ended");

	}
}
