package Pages;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class ActionsTesting  {
   WebDriver driver;
	
   @FindBy(xpath="//div[contains(text(),'Add-ons')]")
    WebElement Addons;
	@FindBy(xpath="(//div[contains(text(),'Add-ons')]/..//following-sibling::div)[2]//a") 
    List<WebElement> AddonsSubmenu; 
	//Dropdown xpath
	@FindBy(xpath="//div//p[contains(text(),'Drag me to my target')]")	
	 WebElement Dragme;
	@FindBy(xpath="//div//p[contains(text(),'Drop here')]")	
	 WebElement Dropme;
	
	@FindBy(xpath="//iframe")	
	List<WebElement> frameslist;
	
	
	public ActionsTesting() {
		this.driver=Constant.driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	public  void mouseovertoaddons() {
		//Utilities.moveToElementAction(Addons);
		Utilities.rightClickAction(Addons);
	}
	
	public  void Printallvalues() {
		Utilities.printAllTheValuesFromListWebelement(AddonsSubmenu);

	}
	
	//DragandDrop
	
	public  void dragAndDrop() throws Throwable {
		Utilities.implicitWait(10);
		
		Utilities.switchToFrameByIndex(0);
		Utilities.DragAndDropAction(Dragme, Dropme);
		Utilities.Takescreenshot();
	}
	
	
	
}
