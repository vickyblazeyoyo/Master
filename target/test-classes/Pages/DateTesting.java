package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class DateTesting  {
	WebDriver driver;
	
	@FindBy(xpath="((//label[contains(text(),'Depart date')])[1]/../..//input)[2]") 
	WebElement DepartDate;
	@FindBy(xpath="((//label[contains(text(),'Return date')])[1]/../..//input)[2]") 
	WebElement ReturnDate;
	
	
	public DateTesting() {
		this.driver=Constant.driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public  void selectDepartDate() {
		Utilities.selectDatebyJs(driver, DepartDate, "20-09-2021");

	}
	
	public  void selectReturnDate() {
		
		
		Utilities.selectDatebyJs(driver, ReturnDate, "25-09-2021");
		Utilities.implicitWait(60);
	}
	
}
