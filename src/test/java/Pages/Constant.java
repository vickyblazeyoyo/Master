package Pages;

import java.util.LinkedHashMap;
import java.util.Map;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.springframework.util.LinkedCaseInsensitiveMap;

public class Constant {
public static Scenario scenario;
public 	static WebDriver driver=null;
public static LinkedHashMap<String, String> Testdata=new LinkedHashMap<String, String>();
public static LinkedHashMap<String, String> Failiue_Scenarios_Features=new LinkedHashMap<String, String>();
public static Map<String,String> Api_Testdata=new LinkedCaseInsensitiveMap<String>();
public static String Scenario=null,SplitedFeature;
public static LinkedHashMap<String, String[]> multipleDataSetup=new LinkedHashMap<String, String[]>(); ;
public static final String Acct_SSID= "AC2d5c48e9195f539cad6ffb46247c88ed";
public static final String Auth_Token= "50d83f00be7c5c95a40345562a2ee122";
public static String TestRailUrl,TestRail_AUTHKEY,TestSuit_ID,TestRun_ID,TestCase_ID;
public static String JiraDefectID;

}
   