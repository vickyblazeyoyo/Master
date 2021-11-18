package Pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class BrokenLinksTesting {

WebDriver driver;
	
	@FindBy(xpath="//input[@name='username']") WebElement Username;
	@FindBy(xpath="//input[@name='password']") WebElement Password;
	@FindBy(xpath="//input[@type='submit']") WebElement Login_Btn;
	
	
	public BrokenLinksTesting() {
		this.driver=Constant.driver;
		PageFactory.initElements(driver, this);
	}
	public  void login() {
		Utilities.sendKeys(Username, Constant.Testdata.get("Username"));
		Utilities.sendKeys(Password, Constant.Testdata.get("Password"));
		Utilities.Click(Login_Btn);
		
	}
	
	public  void Links() throws IOException {
		Utilities.implicitWait(20);
		List<WebElement> Links=driver.findElements(By.xpath("//a"));
		System.out.println(Links.size());
		Links.addAll(driver.findElements(By.xpath("//img")));
		System.out.println(Links.size());
		List<WebElement> validurl=new ArrayList<WebElement>();
		for (int i = 0; i < Links.size(); i++) {
			System.out.println(Links.get(i));
			System.out.println(Links.get(i).getText());
			if (!Links.get(i).getAttribute("href").equals(null)) {
				validurl.add(Links.get(i));
			}
			
		}
		
		Utilities.brokenLinksCount(validurl);
	}
	 
}
