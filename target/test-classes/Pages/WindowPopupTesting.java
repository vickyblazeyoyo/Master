package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class WindowPopupTesting  {
	WebDriver driver;
	@FindBy(xpath="//a[contains(text(),'Follow On Twitter')]")
	WebElement followOnTwitter_Btn;
	
	@FindBy(xpath="//span[contains(text(),'No, thanks')]")
	WebElement NoThanks_Btn;
	
	public WindowPopupTesting() {
		driver=Constant.driver;
		PageFactory.initElements(driver, this);
	}
	
	public  void clickonfollowOnTwitterBtn() {
		Utilities.Click(followOnTwitter_Btn);
		Utilities.implicitWait(10);
		

	}
	
	public  void switchTowindow() {
		Utilities.SwitchToWindow(driver, 2);
         driver.manage().window().maximize();
         Utilities.Click(NoThanks_Btn);
         System.out.println(driver.getTitle());
         driver.close();
         Utilities.implicitWait(5);
         Utilities.SwitchToWindow(driver, 1);
         Utilities.implicitWait(15);
         System.out.println(driver.getTitle());
   }

}
