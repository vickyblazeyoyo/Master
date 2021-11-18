package Pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class HighlightingWebelementscenarios  {
	WebDriver driver;
	
	@FindBy(xpath="//input[@type='submit']")
	WebElement Login_Btn;
	
	@FindBy(xpath="//a[contains(text(),'Forgot Password?')]")
	WebElement Forgot_link;
	
	@FindBy(xpath="//div//i[@class='fa fa-users skrollable skrollable-after']")
	WebElement ContactManagement_link;
	
	public HighlightingWebelementscenarios() {
		this.driver=Constant.driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public  void highlightElement() throws Throwable {
		Utilities.explicitWait(20, Login_Btn);
	System.out.println(Utilities.printAllTheTextInWebpage(driver));	
	Utilities.ScrollIntoView(driver, Forgot_link);
	Utilities.ScrollIntoView(driver, ContactManagement_link);
	Utilities.ClickbyJs(driver, Login_Btn);
		Utilities.explicitWait(20, Login_Btn);
		
		Utilities.borderHighlightingWebelement("red",Login_Btn);
		
		Utilities.Takescreenshot();
	}
	
	
}
