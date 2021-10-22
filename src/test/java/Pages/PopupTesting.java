package Pages;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class PopupTesting   {
	WebDriver driver;
	@FindBy(xpath="//a[contains(text(),'Sign in')]")
	WebElement signIn_Link; 
	
	@FindBy(xpath="//input[@type='submit']")
	WebElement signIn_Btn; 
	  
	  
	 
	  public PopupTesting() {
	   this.driver=Constant.driver;
	   PageFactory.initElements(driver, this);
	}
	
	public  void  ClickonSignIn_Link()  {

		Utilities.Click(signIn_Link);
}
	
	public  void  ClickonSignIn_Btn()  {

		Utilities.Click(signIn_Btn);
}
   public void popupHandling() {
	   Utilities.implicitWait(10);
	  System.out.println(Utilities.alertHandlingGetText());
	   Utilities.alertHandlingAccept();

}
	
	
	
	
}

