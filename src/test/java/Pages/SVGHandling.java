package Pages;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Utilities.Utilities;
import io.reactivex.rxjava3.functions.Action;
import net.sourceforge.tess4j.TesseractException;

public class SVGHandling {
	WebDriver driver;

	@FindBy(xpath = "//*[name()='svg']//*[local-name()='g' and @class='highcharts-series-group']//*[local-name()='rect']")
	List<WebElement> barGraph;
	@FindBy(xpath = "//*[name()='svg']//*[local-name()='g' and @class='highcharts-label highcharts-tooltip highcharts-color-undefined']//*[local-name()='text']")
	WebElement barGraphText;
	@FindBy(xpath = "//input[@id='startmonthyear']")
	WebElement Calender;
	@FindBy(xpath = "//img[@alt='Google']")
	WebElement GoogleImage;
	@FindBy(xpath = "//input[@title='Search']")
	WebElement GoogleSearch;
	@FindBy(xpath = "(//input[@value='Google Search'])[2]")
	WebElement SubmitBtn;
	@FindBy(xpath = "//span[contains(text(),'selenium')]")
	List<WebElement> SuggessionList;
	
	public SVGHandling() {
		this.driver = Constant.driver;
		PageFactory.initElements(driver, this);
	}

	public void getInstance() {
		new SVGHandling();

	}

	public void barGraphHandling() throws Throwable {
		System.out.println("BarGraph Total size Both Green and Yellow :" + barGraph.size());
		Utilities.ScrollIntoView(driver, Calender);
		for (WebElement webElement : barGraph) {
			Utilities.implicitWait(5);
			System.out.println(Utilities.getTomorrowDate("dd-MM-yyyy"));
			Utilities.navigateTo("https://www.google.com/");
			Utilities.sendKeys(GoogleSearch, "covid cases in india");
			SubmitBtn.sendKeys(Keys.ENTER);
			//ComplexSvgHandling();
			Utilities.printAllTheValuesFromListWebelement(SuggessionList);
			Utilities.TakeParticularElementScreenShot(GoogleImage,"Google1");
			Utilities.TakeParticularElementScreenShot(GoogleImage,"Google2");
			Utilities.verifyTwoImagesDifference("Google", System.getProperty("user.dir")+"\\ParticularElementScreenshot\\Google1.png", System.getProperty("user.dir")+"\\ParticularElementScreenshot\\Google2.png");
			Utilities.getElementColour(webElement); 
			Utilities.getElementFontSize(webElement);
			Utilities.moveToElementAction(webElement);
			Utilities.getElementColour(webElement);
			Utilities.getElemenetSize(webElement);
			Utilities.getPosition(webElement);
			String tooltip = Utilities.getText(barGraphText);
			System.out.println(tooltip);
		}
	}

	public void WebPagehandling() {
		String yearXpath = "//tr[@class='row no-margin yearlypaymentdetails']//td[@id='year"
				+ Constant.Testdata.get("Year") + "']";
		String year = yearXpath + "//following-sibling::td";
		WebElement yearData = driver.findElement(By.xpath(yearXpath));
		List<WebElement> yeardataXpath = driver.findElements(By.xpath(year));
		Utilities.ScrollIntoView(driver, yearData);
		String yearValue = (Utilities.getText(yearData));

		if (yearValue.equals(Constant.Testdata.get("Year"))) {
			System.out.println(yearValue);
			for (WebElement webElement : yeardataXpath) {
				System.out.println(Utilities.getText(webElement));
			}
			Utilities.Click(yearData);
			String MonthXpath = "//tr[@class='row no-margin yearlypaymentdetails']/..//tr[@id='monthyear"
					+ Constant.Testdata.get("Year") + "']//tr//td";
			List<WebElement> monthXpath = driver.findElements(By.xpath(MonthXpath));
			for (WebElement mothelement : monthXpath) {
				System.out.println(Utilities.getText(mothelement));
			}
		}
	}
	
	
	public  void ComplexSvgHandling() throws ParseException {
	WebElement element=	driver.findElement(By.xpath("(//*[name()='svg' and @class='uch-psvg'])[2]"));
	int height=element.getSize().getHeight();
	int width=element.getSize().getWidth();
	//This calculation helps to move the mouse in left to right direction (-to+)
	int yHeight=height/2-height;//This gives negative value
	int xwidth=width/2-width;//This also gives negative value 
	
	String Startdate="20-03-2020";
	SimpleDateFormat sf= new SimpleDateFormat("dd-MM-yyyy");
	Date d1=sf.parse(Startdate);
	Date d2 = new Date();
	long diffDays=TimeUnit.MILLISECONDS.toDays(d2.getTime()-d1.getTime());
	Actions action = new Actions(driver);
	for (int i = 0; i < diffDays; i++) {
		action.moveToElement(element, xwidth+i, yHeight).perform();
		String tooltip=	driver.findElement(By.xpath("//table[@class='swWWne']//tbody")).getText();
		System.out.println(tooltip);
	}
	
	
	}
	

}
