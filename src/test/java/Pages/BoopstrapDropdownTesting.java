package Pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class BoopstrapDropdownTesting  {
WebDriver driver;

@FindBy(xpath="//button[@title='HTML, CSS']")
WebElement element;
	
@FindBy(xpath="//button[@title='HTML, CSS']//following-sibling::ul//li//a//label")
List<WebElement> OptionsElement;	

public BoopstrapDropdownTesting() {
	this.driver=Constant.driver;
	PageFactory.initElements(driver, this);
}

public void selectdropdownBtn() {
	
	Utilities.Click(element);

}

public  void selectAlloptions() {
	
	Utilities.getBootsrapOptions(OptionsElement);

}

public  void selectOneOption(String option) {
	
	Utilities.selectBootstrapDropdown(OptionsElement, option);
	
	Utilities.Click(element);
}
	
}
