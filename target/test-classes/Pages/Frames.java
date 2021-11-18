package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class Frames  {
	WebDriver driver;
	
	@FindBy(xpath="//input[@name='username']") WebElement Username;
	@FindBy(xpath="//input[@name='password']") WebElement Password;
	@FindBy(xpath="//input[@type='submit']") WebElement Login_Btn;
	@FindBy(xpath="//a[contains(text(),'Contacts')]") WebElement Contacts_Link;
	
	
public Frames() {
	this.driver=Constant.driver;
	PageFactory.initElements(driver, this);
}


public  void login() {
	Utilities.sendKeys(Username, Constant.Testdata.get("Username"));
	Utilities.sendKeys(Password, Constant.Testdata.get("Password"));
	Utilities.Click(Login_Btn);
}
	
public  void frameHandling() {
	Utilities.switchToFrameByString("mainpanel");
	
}
	
public void selectContact() {
	Utilities.Click(Contacts_Link);

}




}
