package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ScreenRec.MyScreenRecorder;
import Utilities.Utilities;

public class OTPTesting {
    WebDriver driver;

	@FindBy(xpath="//span[contains(text(),'Hello,')]") WebElement SignIn;
	@FindBy(xpath="(//a[@class='nav-a'])[1]") WebElement startHere;
	@FindBy(xpath="//label[contains(text(),'Your name')]/..//input") WebElement yourName;
	@FindBy(xpath="//input[@id='ap_phone_number']") WebElement MobNbr;
	@FindBy(xpath="//span[@data-action='a-dropdown-button']") WebElement ShowCountry;
	@FindBy(xpath="//ul//li//a") List<WebElement> ShowCountryList;
	@FindBy(xpath="//input[@type='password']") WebElement password;
	@FindBy(xpath="//label[@for='ap_password_check']/..//input[@type='password']") WebElement passwordAgain;
	@FindBy(xpath="//input[@id='continue']") WebElement continueBtn;
	@FindBy(xpath="//input[@type='tel']") WebElement EnterOtp;
	
	public OTPTesting() {
		driver=Constant.driver;
		PageFactory.initElements(driver, this);
	}
	public static  void getInstance() {
	new OTPTesting();
}
	
public  void newCustomerCreation() throws Exception {
	MyScreenRecorder.startRecording("newCustomerCreation");
	Utilities.moveToElementAction(SignIn);
	Utilities.Click(startHere);
}	
	
	public  void CrateAccount() {
		Utilities.sendKeys(yourName, Constant.Testdata.get("YourName"));
		Utilities.sendKeys(MobNbr, Constant.Testdata.get("MobileNumber"));
		Utilities.Click(ShowCountry);
		Utilities.getBootsrapOptions(ShowCountryList);
		Utilities.selectBootstrapDropdown(ShowCountryList, Constant.Testdata.get("Reagion"));
		Utilities.sendKeys(password, Constant.Testdata.get("PassWord"));
	
		Utilities.Click(continueBtn);
	}
	
	
	public  void EnterOtp() throws Exception {
    Utilities.explicitWait(20, EnterOtp);
    Utilities.sendKeys(EnterOtp, Utilities.getOtpNbr());
    MyScreenRecorder.stopRecording();
	}
	
	
	
	
	
	
	
	
}
