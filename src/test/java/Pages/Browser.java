package Pages;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import Utilities.Utilities;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser extends Utilities {
	
	 static WebDriver driver;	
	 static By search = By.xpath("//ytd-searchbox[@id='search']");
	 public Browser(WebDriver driver) {
		this.driver=driver;
	}

	public static  void BrowserLaunch() throws FileNotFoundException, IOException {
		
		//System.setProperty("webdriver.chrome.driver", Utilities.ReadFromPropertyFile("ChromeDriverPath"));
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		if (Utilities.ReadFromPropertyFile("Chromeoptions").equalsIgnoreCase("incognito")) {
    	   Utilities.incognitoBrowser(options);
    	   driver = new ChromeDriver(options);
	}else if (Utilities.ReadFromPropertyFile("Chromeoptions").equalsIgnoreCase("headless")) {
		Utilities.headlessChromeoption(options);
		driver = new ChromeDriver(options);
	}else if (Utilities.ReadFromPropertyFile("Chromeoptions").equalsIgnoreCase("disableImage")) {
		 Utilities.disableImagesOnSite(options);
		 driver = new ChromeDriver(options);
	}else if (Utilities.ReadFromPropertyFile("Chromeoptions").equalsIgnoreCase("disablePopup")) {
		Utilities.disableWindowPopups(options);
		 driver = new ChromeDriver(options);
	}
	else 
       {
		driver = new ChromeDriver();
	}
         //driver=new HtmlUnitDriver();
		
         Constant.driver=driver;
         Constant.driver.manage().window().maximize();
         Constant.driver.manage().deleteAllCookies();
         Utilities.certificateCapabilities();
         
         Constant.driver.get(Utilities.ReadFromPropertyFile("URL"));
	    Utilities.stopPageLoading();
	    Utilities.implicitWait(5);
	   
	}
	
	public static void BrowserQuit() {
		 Constant.driver.close();
		 Constant.driver.quit();

	}
	
	
}
