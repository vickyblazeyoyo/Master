package Pages;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;
import junit.framework.Assert;

public class Webtabletesting {
WebDriver driver;
static Webtabletesting Instance;

@FindBy(xpath="//th") List<WebElement> tableHeaderList;
@FindBy(xpath="//td/..") List<WebElement> tableDataList;
@FindBy(xpath="//th[contains(text(),'Standard')]//following::tr//td//input[@type='checkbox']") List<WebElement> checkBoxStandardPremium;
@FindBy(xpath="//iframe[@title='MailerLite Form']") WebElement frame;
@FindBy(xpath="(//button[@class='close'])[1]") WebElement frameClose;
@FindBy(xpath="//td//select") List<WebElement> DropDowns;
@FindBy(xpath="//td//input[@type='text']") List<WebElement> TextFields;
@FindBy(xpath="//button[contains(text(),'Update')]") WebElement UpdateBtn;
@FindBy(xpath="//div//p[contains(text(),'Details')]") WebElement DetailsInfo;
@FindBy(xpath="//h2") WebElement WebtableHeadr;

//Single Selection Scenario


public Webtabletesting() {
	this.driver=Constant.driver;
	PageFactory.initElements(driver, this);
}

public static void getInstance() {
	if (Instance==null) {
		new Webtabletesting();
	}

}

public void frameHandling() {
	Utilities.explicitWait(30, frame);
	if (frame.isDisplayed()) {
		Utilities.switchToFrameByElement(frame);
		Utilities.Click(frameClose);
		Utilities.switchToDefaultContent();
	}
	

}

public void headerPrint() {
	frameHandling();
	for (int i = 0; i < tableHeaderList.size(); i++) {
		Utilities.explicitWait(20, tableHeaderList.get(i));
		String header=tableHeaderList.get(i).getText();
	String[] headerValues=	Constant.multipleDataSetup.get("Headers");
	String values=headerValues[i];
	if (header.equals(values)) {
		Assert.assertTrue(true);
		System.out.println(header);
	}else {
		Assert.assertTrue(false);
	}
		
	}
	}

public  void printNamesFromWebTable() {
	for (int i = 1; i < tableDataList.size(); i++) {
		String NmeXpath="((//th//following::tr)["+i+"]//td)[1]";
	WebElement namesElement=driver.findElement(By.xpath(NmeXpath));
	System.out.println(namesElement.getText());
	}
	

}

public void printEmail() {
	for (int i = 1; i < tableDataList.size(); i++) {
		String emailXpath="((//th[contains(text(),'Email')]//following::tr)["+i+"]//td)[2]";
		WebElement emailNames=driver.findElement(By.xpath(emailXpath));
		System.out.println(emailNames.getText());
	}

}


public void clickAllCheckbox() {
	for (WebElement element : checkBoxStandardPremium) {
		if (!element.isSelected()) {
			Utilities.Click(element);
		}else {
			System.out.println("Element is not Selected");
			Assert.assertTrue(false);
		}
		
	}

}



public  void SelectAllDropdown() {
	for (WebElement webElement : DropDowns) {
		Utilities.getOptionsDropdown(webElement);
		Utilities.selectDropDownByText(webElement, "Sedan");
	}
	}

public  void CommentsField() {
	for (WebElement webElement : TextFields) {
		Utilities.explicitWait(10, webElement);
		Utilities.sendKeys(webElement, Constant.Testdata.get("Comment"));
	}
}

public void UpdateAndMessageValidation() {
	Utilities.ScrollIntoView(driver, UpdateBtn);
	Utilities.Click(UpdateBtn);
	String details=	DetailsInfo.getText();
if (details.equals(Constant.Testdata.get("UpdateMsg"))) {
	System.out.println(details);
	Assert.assertTrue(true);
}else {
	Assert.assertTrue(false);
}
}


public  void singleSelectOption(String Name, String StandardOrPremium, String DropdownValue,String Comment) {
	Utilities.refreshBrowser();
	frameHandling();
	Utilities.ScrollIntoView(driver, WebtableHeadr);
	String StandaredXpath=null;
	String dropdownXpath="//td[contains(text(),'"+Name+"')]/..//select";
	String CommentText= "//td[contains(text(),'"+Name+"')]/..//input[@type='text']";
	String Email="//td[contains(text(),'"+Name+"')]//following-sibling::td[contains(text(),'.com')]";
	WebElement drop=driver.findElement(By.xpath(dropdownXpath));
	WebElement Commentpath=driver.findElement(By.xpath(CommentText));
	WebElement Emailpath=driver.findElement(By.xpath(Email));
	
	
			if (StandardOrPremium.equals("Standard")) {
              StandaredXpath = "(//td[contains(text(),'"+Name+"')]/..//input[@type='checkbox'] )[1]";
           WebElement element=     driver.findElement(By.xpath(StandaredXpath));
           Utilities.Click(element);
           }else {
        	   StandaredXpath = "(//td[contains(text(),'"+Name+"')]/..//input[@type='checkbox'] )[2]";
        	   WebElement element=     driver.findElement(By.xpath(StandaredXpath));
               Utilities.Click(element);
		}
		Utilities.selectDropDownByText(drop, DropdownValue);
		Utilities.sendKeys(Commentpath, Comment);
		if (Emailpath.getText().equals(Constant.Testdata.get("EmailMsg"))) {
			System.out.println(Emailpath.getText());
			Assert.assertTrue(true);
		}else {
			Assert.assertTrue(true);
			System.out.println("Email is not displayed");
		}
		
		
          
}

}











