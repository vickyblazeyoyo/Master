package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;

public class ModelPopupTesting   {

	WebDriver driver;
	
	@FindBy(xpath="//div[@aria-label='Open Intercom Messenger']")
	WebElement Chatbox;
	@FindBy(xpath="//a[@class='view-all-conversations']")
	WebElement SeeAllYourCoversations;
	@FindBy(xpath="//span[contains(text(),'Send us a message')]")
	WebElement SendUsMessage;
	@FindBy(xpath="//textarea[@name='message']")
	WebElement SendUsMessageTextArea;
	@FindBy(xpath="//div[@aria-label='Back']")
	WebElement backtn;
	@FindBy(xpath="//div[contains(text(),'Welcome to Free CRM, please let us know how we can help.')]")
	WebElement welcomeTag;
	@FindBy(xpath="//div[@aria-label='Dismiss']")
	WebElement dismissBtn;
	@FindBy(xpath="//input[@aria-label='Search']")
	WebElement articleSearch;
	@FindBy(xpath="//button[@aria-label='Submit search']")
	WebElement articleSearch_Btn;
	@FindBy(xpath="//div[@class='intercom-messenger-card-list-item intercom-wlajoz e1b3yklj0']//div//div")
	List<WebElement> ListOfSuggession;
	
	public ModelPopupTesting() {
		this.driver=Constant.driver;
		PageFactory.initElements(driver, this);
	}
	
	public  void openChatBox() {
		Utilities.implicitWait(30);
		Utilities.switchToFrameByString("intercom-borderless-frame");
		Utilities.explicitWait(40, welcomeTag);
		Utilities.moveToElementAction(welcomeTag);
		Utilities.Click(dismissBtn);
		Utilities.switchToFrameByString("intercom-launcher-frame");
		Utilities.Click(Chatbox);
		Utilities.switchToDefaultContent();
}
	
	
	public  void seeAllYourCoversations() {
		Utilities.switchToFrameByString("intercom-messenger-frame");
		Utilities.ScrollIntoView(driver, SendUsMessage);
		Utilities.Click(SendUsMessage);
		Utilities.sendKeys(SendUsMessageTextArea,Constant.Testdata.get("Message"));
		Utilities.Click(backtn);
	
	}
	
	public  void findAnswer() {
		Utilities.ScrollIntoView(driver, articleSearch_Btn);
		Utilities.explicitWait(40, articleSearch);
		Utilities.sendKeys(articleSearch, Constant.Testdata.get("FindArticle"));
		Utilities.Click(articleSearch_Btn);
		
	}
	
	public  void searchSuggessions() {
		
		
		for (WebElement webElement : ListOfSuggession) {
			Utilities.explicitWait(30, webElement);
			String text=webElement.getText();
			if (text.equals(null)) {
				List<WebElement> subElementText	= driver.findElements(By.xpath("//div[@class='intercom-messenger-card-list-item intercom-wlajoz e1b3yklj0']//div//div//span"));
			for (WebElement element : subElementText) {
			
				String subelementText=	element.getText();
			System.out.println(subelementText);
			}
			
			
			}
			System.out.println(text);
		}
		Utilities.switchToDefaultContent();
		Utilities.switchToFrameByString("intercom-launcher-frame");
		Utilities.Click(Chatbox);
	}
	
}