package Utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.asprise.ocr.Ocr;
import com.deque.axe.AXE;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageFetcher;
import com.twilio.rest.ipmessaging.v2.Service;

import Pages.Constant;
import io.cucumber.java.Scenario;
import junit.framework.Assert;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Utilities {

	// For Authentication Pop up Use:http://Username:Password@URL.com
	// For Shadow Element Handling use Javascript:document.quarrySelector,ShadowRoot.QuarySelector
	// For SVG Xpath use: //*[name()='svg']//*[local-name()='subtag']
	// following-sibling::
	// If there is ToolTip handling Go to source from inspect page click Ftn+F8 to pause the execution of the Webpage and then inspect particular tooltip and get the Xpath
	
	private static Cipher encryptCipher = null;
	private static Cipher decryptCipher = null;
	static String pass = "abcd1234";
	
	
	
	
	public static String ReadFromPropertyFile(String Key) {

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(
					System.getProperty("user.dir") + "\\" + "src\\test\\resources\\Config\\Config.properties"));
			} catch (IOException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Config Property File is not Found: "+e);
		}

		String Value = prop.getProperty(Key);
		return Value;
	}

	public static void SwitchToWindow(WebDriver driver, int windowno) {

		Set<String> windowsessionIDs = driver.getWindowHandles();
		if (windowsessionIDs.size()==0) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Window session id not Found");
} else {
	List<String> listofsessionId = new ArrayList<String>(windowsessionIDs);
	driver.switchTo().window(listofsessionId.get(windowno - 1));

}
			}

	public static void Click(WebElement element) {
		try {
			Wait wait = new WebDriverWait(Constant.driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Element Found and Clicked : "+element);
			element.click();
		} catch (NoSuchElementException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "NoSuchElementException for the Webelement :"+element);
		}catch (Exception e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Click Intercept with Webelement :"+element+" with ***exception*** "+e);
		}

	}

	public static String alertHandlingGetText() {
		Alert alert = Constant.driver.switchTo().alert();
		String alertText = alert.getText();
		if (alertText==null) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Alert Text is Empty");
		}
		return alertText;

	}

	public static void alertHandlingAccept() {
		Alert alert = Constant.driver.switchTo().alert();
		alert.accept();

	}

	public static void alertHandlingDismiss() {

		Alert alert = Constant.driver.switchTo().alert();
		alert.dismiss();
	}

	public static void implicitWait(int timeout) {
		Constant.driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);

	}

	public static void explicitWait(int waitTimeSec, WebElement element) {
		WebDriverWait wait = new WebDriverWait(Constant.driver, waitTimeSec);
		if (wait.until(ExpectedConditions.visibilityOf(element)) == null) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Element Not Visible : "+element);
		}else {
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Element is  Visible : "+element);
		}
	}

	public static void Fluentwait(int Timeoutsec, int pollingTimeFreq) {
		Wait wait = new FluentWait<WebDriver>(Constant.driver).withTimeout(Timeoutsec, TimeUnit.SECONDS)
				.pollingEvery(pollingTimeFreq, TimeUnit.SECONDS).ignoring(Exception.class);

	}

	public static void switchToDefaultContent() {
		Constant.driver.switchTo().defaultContent();

	}

	public static void switchToFrameByIndex(int Index) {
		Constant.driver.switchTo().frame(Index);

	}

	public static void switchToFrameByString(String FrameValue) {
		Constant.driver.switchTo().frame(FrameValue);
	}

	public static void switchToFrameByElement(WebElement element) {
		try {
			Constant.driver.switchTo().frame(element);
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Frame Element is  Present : "+element);
		} catch (Exception e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "NoSuchElement :"+element+" with ***Exception***"+e);
		}
	}

	public static void sendKeys(WebElement element, String Value) {
		explicitWait(20, element);
		element.sendKeys(Value);
}

	public static void ReadDataFromExcelPutItInMap()  {
		String value = null;
	    FileInputStream input = null;
		Workbook workbook = null;
		File file = new File(System.getProperty("user.dir") + "\\" + ReadFromPropertyFile("ExcelFilePath") + "\\"
				+ "TestData_" + ReadFromPropertyFile("Environment") + ".xlsx");
		
		
		try {
			input = new FileInputStream(file);
			 workbook = new XSSFWorkbook(input);
			 LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "TestData_"+ReadFromPropertyFile("Environment") + ".xlsx File Found and Accessed");
		} catch (IOException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "File Not Found : TestData_"+ReadFromPropertyFile("Environment") + ".xlsx"+" With ***Exception***"+e);
		}
		String[] splitedScenarioname = Constant.Scenario.split("-");
		Constant.SplitedFeature=splitedScenarioname[0].trim();
		Sheet sheet = workbook.getSheet(Constant.SplitedFeature);
		if (sheet == null) {
			 LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Test Data is not Applicable for this Scenario: " + Constant.Scenario);
			try {
				workbook.close();
				input.close();
				 LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
							+ new Throwable().getStackTrace()[0].getMethodName(), "TestData_"+ReadFromPropertyFile("Environment") + ".xlsx Workbook Closed Successfully");
			} catch (IOException e) {
				LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Exception With Closing the Workbook : "+e);
			}
			
		} else {
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					Cell cell = row.getCell(0);
					String key = cell.getStringCellValue();
					Cell cellValue = row.getCell(1);
					if (cellValue.getCellType() == CellType.NUMERIC) {
						DataFormatter data = new DataFormatter();
						value = data.formatCellValue(cellValue);
					} else {
						value = cellValue.getStringCellValue();
					}

					if (value.contains(",")) {
						String[] multiple = value.split(",");
						Constant.multipleDataSetup.put(key, multiple);
					}
					Constant.Testdata.put(key, value);
					break;
				}
			}
			try {
				workbook.close();
				input.close();
				 LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
							+ new Throwable().getStackTrace()[0].getMethodName(), "TestData_"+ReadFromPropertyFile("Environment") + ".xlsx Workbook Closed Successfully");
			} catch (IOException e) {
				LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Exception With Closing the Workbook : "+e);
			}
			
		}
	}

	public static void moveToElementAction(WebElement element) {

		Actions action = new Actions(Constant.driver);
		explicitWait(10, element);
		action.moveToElement(element).build().perform();
		
	}

	public static void moveToElementActionWithOffset(WebElement element,int xaxis,int yaxis) {

		Actions action = new Actions(Constant.driver);
		explicitWait(10, element);
		action.moveToElement(element, xaxis, yaxis).build().perform();
		
	}
	
	public static void rightClickAction(WebElement element) {

		Actions action = new Actions(Constant.driver);
		explicitWait(10, element);
		action.contextClick(element).build().perform();
		
	}

	public static void doubleClickAction(WebElement element) {

		Actions action = new Actions(Constant.driver);
		explicitWait(10, element);
		action.doubleClick(element).build().perform();
		
	}

	public static void DragAndDropAction(WebElement source, WebElement destination) {
		Actions action = new Actions(Constant.driver);
		if (source.isDisplayed() && destination.isDisplayed()) {
			action.dragAndDrop(source, destination).build().perform();
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Drag and Drop Source and Destination Webelement Found DragAndDrop Performed, ***Source Element: "+source+"Destination Element: "+destination);
		} else {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Drag and Drop Source and Destination Webelement is not Found, ***Source Element: "+source+"Destination Element: "+destination);
		}
	}
	
	public static String getCurrentURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public static String getTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public static void actionClear(WebDriver driver, WebElement webElement) {
		webElement.click();
		Actions action = new Actions(driver);
		webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), "55");
		action.sendKeys(Keys.DELETE);
	}

	public static void printAllTheValuesFromListWebelement(List<WebElement> elements) {
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "List of WebElements Size :"+elements.size());
		for (WebElement webElement : elements) {
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "List of WebElements Values :");
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), webElement.getText());
			
		}
	}

	//Arguments- element:we have specify list of webelements, attribute:The attribute value which we want to get
	public static void getIframes(List<WebElement> element, String attribute) {
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "List of IFrames Size :"+element.size());
		for (WebElement webElement : element) {
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(),webElement.getAttribute(attribute) );
			
		}
	}

	public static void navigateTo(String Url) {
		Constant.driver.navigate().to(Url);

	}

	public static void navigateForward() {
		Constant.driver.navigate().forward();

	}

	public static void navigateBackward() {
		Constant.driver.navigate().back();

	}

	public static void refreshBrowser() {
		Constant.driver.navigate().refresh();

	}

	public static void Takescreenshot() throws Throwable   {
		TakesScreenshot tk = ((TakesScreenshot) Constant.driver);
		File source = tk.getScreenshotAs(OutputType.FILE);
		File destination = new File(".Screenshots/Image"+getTimeStamp("MM-dd-yy-HH-mm-ss")+".png");
		try {
			FileUtils.copyFile(source, destination);
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "ScrenShot Taken and Saved in the Path:./target/Image.png");
		} catch (IOException e) {
			
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "ScrenShot is not Taken and Saved in the  Path:./target/Image.png  ***Exception*** "+e);
		}
		
          
	}

	// Using Ashot Api Arguments-element:Specify the webelement which we want to take screenshot,
	//ElementName:We have to specify Screenshot Name
	public static void TakeParticularElementScreenShot(WebElement element, String ElementName) throws Throwable  {
		Screenshot shot = new AShot().coordsProvider(new WebDriverCoordsProvider())
				.shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(Constant.driver, element);
		try {
			ImageIO.write(shot.getImage(), "PNG",
					new File(System.getProperty("user.dir") + "\\ParticularElementScreenshot\\" + ElementName +getTimeStamp("MM-dd-yy-HH-mm-ss")+ ".png"));
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), ElementName+" ScrenShot Taken and Saved in the Path: "+System.getProperty("user.dir") + "\\ParticularElementScreenshot\\" + ElementName + ".png");
		} catch (IOException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), ElementName +" ScrenShot is not Taken NosuchElement with  ***Exception*** "+e);
			
		}
	}

	// Using Ashot Api-We can verify two screenshots
	public static void verifyTwoImagesDifference(String ExpectedImagePath, String ActualImagePath)
			throws IOException {
		// Read Buffered Image using -->[]
		BufferedImage Expected = ImageIO.read(new File(ExpectedImagePath));
		BufferedImage Actual = ImageIO.read(new File(ActualImagePath));
		ImageDiffer differ = new ImageDiffer();
		ImageDiff diff = differ.makeDiff(Expected, Actual);
		if (diff.hasDiff() == true) {
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(),"Image1 and Image2 are Same");
			
		} else {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Image1 and Image2 are not Same");
		
		}
	}

	public static void highlightingElement(String Colour, WebElement element) {
		explicitWait(10, element);
		JavascriptExecutor js = ((JavascriptExecutor) Constant.driver);
		js.executeScript("arguments[0].style.background='" + Colour + "'", element);

	}

	public static void borderHighlightingWebelement(String Colour, WebElement element) {
		explicitWait(10, element);
		JavascriptExecutor js = ((JavascriptExecutor) Constant.driver);
		js.executeScript("arguments[0].style.border='2px solid " + Colour + "'", element);

	}

	public static void ClickbyJs(WebDriver driver, WebElement element) {
		try {
			Wait wait = new WebDriverWait(Constant.driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Element Found and Clicked : "+element);
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("arguments[0].click();", element);
		} catch (NoSuchElementException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "NoSuchElementException for the Webelement :"+element);
		}catch (Exception e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Click Intercept with Webelement :"+element+" with ***exception*** "+e);
	
	}}

	public static String printAllTheTextInWebpage(WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String text = js.executeScript("return document.documentElement.innerText;").toString();
		return text;

	}

	public static void ScrollIntoView(WebDriver driver, WebElement element) {
		explicitWait(10, element);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].scrollIntoView(true);", element);

	}

	public static void selectDatebyJs(WebDriver driver, WebElement element, String date) {
		explicitWait(10, element);
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].setAttribute('value','" + date + "');", element);

	}

	public static void PageDown(WebDriver driver) {

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static WebElement JsShadowElement(String JsPath) {
		JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
		WebElement elemenet = (WebElement) js.executeScript("return " + JsPath);
		return elemenet;

	}

	public static void stopPageLoading() {
		JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
		js.executeScript("return window.stop");

	}

	public static void openNewTab() {
		JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
		js.executeScript("window.open()");

	}
	
	// Scroll down to particular element location
		public static void scrollToElement(WebDriver driver, WebElement element) {
			try {
				int x = element.getLocation().getX();
				int y = element.getLocation().getY();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(" + (x - 200) + "," + (y - 200) + ")");
			} catch (Exception e) {
				LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Scrolling to element is not working");
	
			}
		}

		// Scroll to top of the page
		public static void scrollTop(WebDriver driver) {
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0, 0)");
				
			} catch (Exception e) {
				LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Scrolling top is not working");
			}
		}
		
	public static void selectAllOptionsBootsrapDropdown(List<WebElement> elements) {
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "List of Options in the dropdown"+elements.size());
		
		for (WebElement element : elements) {
			if (!element.isSelected()) {
				
				element.click();
			}
		}

	}

	public static void selectBootstrapDropdown(List<WebElement> elements, String Option) {
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "List of Options in the dropdown"+elements.size());
		
		for (WebElement element : elements) {
			if (element.getText().equals(Option)) {
				element.click();
			}
		}

	}

	public static void getBootsrapOptions(List<WebElement> elements) {
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "List of Options in the dropdown "+elements.size());
		
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "ptions in the dropdown: ");
		
		for (WebElement element : elements) {
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), element.getText());
			
		}

	}

	public static void brokenLinksCount(List<WebElement> element) throws IOException {
		int BrokenLink = 0;
		int NonBrokenlink = 0;
		for (WebElement webElement : element) {
			String Url = webElement.getAttribute("href");
			URL url = new URL(Url);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.connect();
			if (!(httpConnection.getResponseCode() == 200)) {
				BrokenLink++;
				LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Broken Link: " + Url + " ------->ResponseCode: " + httpConnection.getResponseCode()
						+ "Msg: " + httpConnection.getResponseMessage());
				
			} else {
				NonBrokenlink++;

			}
			httpConnection.disconnect();
		}
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), 
				"Total Links= " + element.size() + " BrokenLink= " + BrokenLink + " NonBrokenlink=" + NonBrokenlink);
	}

	public static void selectDropDownByText(WebElement element, String DropdownValue) {
		Select s = new Select(element);
		if (!element.isSelected()) {
			s.selectByVisibleText(DropdownValue);
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), 
					DropdownValue+" dropDown Successfully selected");
		} else {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), DropdownValue + " Value is Already selected");
			
		}
	}
	
	
	public static void deSelectByVisibletext(WebElement element, String DropdownValue) {
		try {
			Select selectBox = new Select(element);
			selectBox.deselectByVisibleText(DropdownValue);
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), 
					DropdownValue+" dropDown successfully Deselected");
		
		} catch (Exception e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), DropdownValue+" dropDown not  Deselected");
		}
	}


	public static void getOptionsDropdown(WebElement element) {
		Select s = new Select(element);
		
		List<WebElement> elements = s.getOptions();
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Total Number of Options in the dropdown:"+elements.size());
		
		for (WebElement webElement : elements) {
			String option = webElement.getText();
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), option);
			
		}
	}

	// Zxing API
	public static String barcodeReader(String Url)  {
		URL url;
		BufferedImage BI = null;
		String resultText = null;
		try {
			url = new URL(Url);
			BI = ImageIO.read(url);
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Barcode image has read");
			LuminanceSource lumi = new BufferedImageLuminanceSource(BI);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(lumi));
			Result result = null;
			result = new MultiFormatReader().decode(bitmap);
			resultText = result.getText();
		} 
	
		 catch (IOException e) {
			 LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Barcode Image is not Readed : ***Exception*** "+e);
				
		}
		catch (NotFoundException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Barcode Image is notFound : ***Exception*** "+e);
			
		}
		
		return resultText;
	}

	// Apache PDF Box API
	public static String pdfReader(String PdfPath)  {
		String pdfDocument=null;
		try {
			URL url = new URL(PdfPath);
			InputStream input = url.openStream();
			PDDocument pdfdocument = null;
			pdfdocument = PDDocument.load(input);
			 pdfDocument = new PDFTextStripper().getText(pdfdocument);
			 LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Pdf file opend and readed Successfully");
				
		} catch (IOException e) {
			 LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "Pdf file Exception :"+e);
				
		}
		
		return pdfDocument;
	}

	public static void disableImagesOnSite(ChromeOptions options) {
		HashMap<String, Object> images = new HashMap<String, Object>();
		images.put("images", 2);
		HashMap<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values", images);
		options.setExperimentalOption("prefs", prefs);
		
	}

	public static void headlessChromeoption(ChromeOptions option) {
		option.addArguments("window-size=1400,800");
		option.addArguments("headless");
		
	}

	public static void incognitoBrowser(ChromeOptions option) {
		option.addArguments("incognito");
		
	}
	
	public static void disableWindowPopups(ChromeOptions option) {
		option.setExperimentalOption("excludesSwitches", Arrays.asList("disable-popup-blocking"));
	}

	// Twilio API
	public static String getOtpNbr() {
		String otp = null;
		Twilio.init(Constant.Acct_SSID, Constant.Auth_Token);
		ResourceSet<Message> messages = Message.reader().limit(20).read();
		for (Message record : messages) {
			MessageFetcher message = Message.fetcher(record.getSid());
			String body = message.fetch().getBody();
			otp = body.replaceAll("[^0-9 ]", "").trim();
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "OTP is :" + otp);
			break;
		}
		return otp;
	}

	public static void AccessbilityCheck(String ScenarioName) {
		URL URL = Utilities.class.getResource("/axe.min.js");
		JSONObject object = new AXE.Builder(Constant.driver, URL).analyze();
		JSONArray violation = object.getJSONArray("violations");
		if (violation.length() == 0) {
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "No Accessibility Error");
		} else {
			AXE.writeResults(ScenarioName, violation);
			Assert.assertTrue(AXE.report(violation), false);
		}
	}

	public static String getText(WebElement element) {

		explicitWait(5, element);
		String Text = element.getText();
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Element "+element+" Text : "+Text);
		return Text;

	}

	public static void getElemenetSize(WebElement element) {
		explicitWait(10, element);
		Dimension d = element.getSize();
		int height = d.getHeight();
		int Width = d.getWidth();
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Element Height: " + height + " Element Width: " + Width);
		
	}

	public static void getPosition(WebElement element) {
		explicitWait(10, element);
		Point p = element.getLocation();
		int xaxis = p.getX();
		int yaxis = p.getY();
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Element Position X-axis: " + xaxis + " Element Position Y-axis " + yaxis);
		
	}

	public static void pageDownKey(WebElement element) {
		explicitWait(10, element);
		element.sendKeys(Keys.PAGE_DOWN);
	}

	public static void pageUPKey(WebElement element) {
		explicitWait(10, element);
		element.sendKeys(Keys.PAGE_UP);

	}

	public static void enterKey(WebElement element) {
		explicitWait(10, element);
		element.sendKeys(Keys.ENTER);

	}

	public static String getElementBackgroundColour(WebElement element) {
		explicitWait(10, element);
		String Backgroundcolour = element.getCssValue("background-color");
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Element Background colour: " + Backgroundcolour);
	
		return Backgroundcolour;

	}
	
	public static String  getElementColour(WebElement element) {
		explicitWait(10, element);
		String colour = element.getCssValue("color");
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Element Colour: " + colour);
		
		return colour;
		

	}

	public static String getElementFontSize(WebElement element) {
		explicitWait(10, element);
		String font = element.getCssValue("font-size");
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), "Element FontSize: " + font);
		
		return font;

	}
	
	public static String randomNumber(int digit) {
		String generatednumber = RandomStringUtils.randomNumeric(digit);
		return generatednumber;
	}
	
	public static void waitForLoading(WebDriver driver, WebDriverWait wait) {
		WebElement loading = driver.findElement(By.xpath("//div[@class=\"ngx-loading-text center-center\"]"));
		wait.until(ExpectedConditions.invisibilityOf(loading));
	}

	// Using Commons-Csv jar for Reading CSV files 
	public static void readCSVFile(String Header)  {
		try {
			Reader reader = Files.newBufferedReader(Paths
					.get(System.getProperty("user.dir") + "\\" + ReadFromPropertyFile("ExcelCSVPath") + "\\Vicky.csv"));
			CSVParser parser = new CSVParser(reader,
					CSVFormat.DEFAULT.withHeader("Year", "Industry_aggregation_NZSIOC").withIgnoreHeaderCase().withTrim());
			for (CSVRecord csvRecord : parser) {
				String data = csvRecord.get(Header);
				LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), data);

			}} catch (IOException e) {
				LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), "CSV File Not Found "+e);
			
		}
		
	}

	// Use tess4j Api and paste the eng.traineddata file download from git
	// It will not support .JPG format images,Set Environmental variable for tessaract eng file
	// To read the image first takescreenshot and then read the text
	//Filename: This specify the Screenshot name 
	public static String readImageToText(String Filename)  {
		String ImageText=null;
		try {
			ITesseract image = new Tesseract();
			 ImageText = image.doOCR(
					new File(System.getProperty("user.dir") + "\\" + "ParticularElementScreenshot\\" + Filename + ".png"));
			LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), ImageText);
			

		} catch (TesseractException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "TesseractException :" + e);
		}
		
		
		return ImageText;
	}

	public static void downloadImageFromWebpage(WebElement element, String FileName) throws IOException {
		try {
			String ImagePath = element.getAttribute("src");
			URL url = new URL(ImagePath);
			BufferedImage bufferedImage = ImageIO.read(url);
			ImageIO.write(bufferedImage, "png", new File("DownloadedImage\\" + FileName + ".png"));

		} catch (IOException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "DownloadImage Exception" + e);
		}
		
	}

	public static void certificateCapabilities() {
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	}

	public static  void maxLengthOfTextField(WebElement element,String FieldName,String Fieldmaxl) {
	
	String maxlength=	element.getAttribute("maxlength");
	if (maxlength.equals("Fieldmaxlength")) {
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), FieldName+" "+maxlength+" is the Maxlength of the Field and its verified");
		
	}else {
		LoggerUtility.LogMessage(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
				+ new Throwable().getStackTrace()[0].getMethodName(), FieldName+" "+maxlength+" is the Maxlength of the Field and its Not Equal");
	
	}
	}
	
	public static  String getTodayDate(String Format) {
		Date d = new Date();
		SimpleDateFormat date=new SimpleDateFormat(Format);
		String TodayDate=date.format(d);
		return TodayDate;

	}
	
	public static  String getYesturdayDate(String Format) {
		int millisec=1000*60*60*24;
		Date d = new Date();
		SimpleDateFormat date=new SimpleDateFormat(Format);
	String yesturdayDate=	date.format(d.getTime()-millisec);
	return yesturdayDate;

	}
	
	public static String getTomorrowDate(String Format) {
		Date d = new Date();
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DATE, 1);
        d=c.getTime();
    	String tomoDate=new SimpleDateFormat(Format).format(d);
		return tomoDate;
    	
	}
	
	public static  void DeleteFiles(String path) {
		 File file = new File(path);
		 File[] files = file.listFiles(); 
		 for (File filee : files) {
			if (filee.isFile()&&filee.exists()) {
				filee.delete();
			}else {
				LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
						+ new Throwable().getStackTrace()[0].getMethodName(), path+" Folder is Empty");
			}
		}
	}
	
	public static String getTimeStamp(String Format) throws Throwable {
		DateFormat date = new SimpleDateFormat(Format);
		Date dateobj = new Date();
		return date.format(dateobj);
	}
	
	
	public static  void DeleteParticularFile(String path,String Extention) {
		 File file = new File(path);
		 File[] files = file.listFiles(); 
		 for (File filee : files) {
			if (FilenameUtils.getExtension(filee.getName()).equals(Extention)&&filee.exists()) {
				filee.delete();
			}
		}
	}
	
	public static  void readTextFile(String path) {
		 String st = null;
	try {
			File txtFile= new File(path);
			BufferedReader reader=new BufferedReader(new FileReader(txtFile));
			while ((st = reader.readLine()) != null) {
	            System.out.println(st);
	    }
			
		} catch (IOException e) {
			LoggerUtility.LogException(MethodHandles.lookup().lookupClass().toString().split(" ")[1]+"."
					+ new Throwable().getStackTrace()[0].getMethodName(), "Reading Text Exception: "+e);
		
		}
	
	}
	//Getting date along with the days count-Autobots(Today()+1)
	public static String getCurrentCSTDateWithArgFormat(String format, int days) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat(format);
		date.setTimeZone(TimeZone.getTimeZone("CST"));
		Date dateobj = new Date();
		cal.setTime(dateobj);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return(date.format(cal.getTime()));
	}
	
	//Getting curent month or year or current day 
	public static String getCSTSpecificComponentInDate(String component) {
		SimpleDateFormat cstCdtFormat=new SimpleDateFormat(component);
		cstCdtFormat.setTimeZone(TimeZone.getTimeZone("CST6CDT"));
		return(Integer.toString(Integer.parseInt(cstCdtFormat.format(new Date()))));
	}
	
	//Getting Timestamp Using Current Timezon-CST
	public static String getTellerTimeStamp() throws Throwable {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss:SSSS");
		date.setTimeZone(TimeZone.getTimeZone("CST"));
		Date dateobj = new Date();
		cal.setTime(dateobj);
		return date.format(cal.getTime());
	}
	
	//Generating 5 random numbers
	public static String fiveDigitRandomNum() throws Throwable {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(100000);
		String randomNum = String.format("%05d", num); 
		return randomNum;
	}
	//Generating 3 random numbers
	public static String threeDigitRandomNum() throws Throwable {
		SecureRandom random = new SecureRandom();
		int num = random.nextInt(100);
		String randomNum = String.format("%03d", num); 
		return randomNum;
	}
	//Getting 9 random numbers
	public static String get9Digit() throws Throwable {
		DateFormat date = new SimpleDateFormat("yyMMddmmss");
		Date dateobj = new Date();
		String stringDay = date.format(dateobj);
		stringDay = stringDay.substring(0, stringDay.length()-1);
		return stringDay;
	}
	//Generating SSN number
	public static String genSSN() {
        long timeSeed = System.nanoTime(); // to get the current date time value
        double randSeed = Math.random() * 1000; // random number generation
        long midSeed = (long) (timeSeed * randSeed); // mixing up the time and
        String s = midSeed + "";
        String subStr = s.substring(0, 9);
        int finalSeed = Integer.parseInt(subStr);    // integer value
        System.out.println(finalSeed); 
        return String.valueOf(finalSeed);
	}
	//Get 10 digit random number
	public static String get10Digit() throws Throwable {
		DateFormat date = new SimpleDateFormat("yyMMddmmss");
		Date dateobj = new Date();
		return date.format(dateobj);
	}
	
	//This method is used to encrypt a string with base 64 encryption
	public static String encryptBase64(String unencryptedString) throws Exception {
		// Encode the string into bytes using utf-8
		byte[] unencryptedByteArray = unencryptedString.getBytes("UTF8");

		// Encrypt
		byte[] encryptedBytes = encryptCipher.doFinal(unencryptedByteArray);

		// Encode bytes to base64 to get a string
		byte[] encodedBytes = Base64.encodeBase64(encryptedBytes);

		return new String(encodedBytes);
	}
	
	// This method is used to decrypt an encrypted string with base 64 encryption
	public static String decryptBase64(String encryptedString) throws Exception {
		// Encode bytes to base64 to get a string
		byte[] decodedBytes = Base64.decodeBase64(encryptedString.getBytes());

		// Decrypt
		byte[] unencryptedByteArray = decryptCipher.doFinal(decodedBytes);

		// Decode using utf-8
		return new String(unencryptedByteArray, "UTF8");
	}
	
	//Decrypt the String
	public static String dstr(String evalue) throws Throwable {
		DESKeySpec key = new DESKeySpec(pass.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

		SecretKey ekey = keyFactory.generateSecret(key);
		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, ekey);
        String dString = decryptBase64(evalue);
		return dString.toString().trim();
	}
	
	//Encrypt the String
	public static String encstr(String value) throws Throwable {
		DESKeySpec key = new DESKeySpec(pass.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey ekey = keyFactory.generateSecret(key);
		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, ekey);
        String unencryptedString = value;
		String encryptedString = encryptBase64(unencryptedString);
		return encryptedString.toString().trim();
	}
	
	//Getting time difference between two times
	public static String getTimeDiff(String fromTime, String toTime) throws ParseException  {
		Long diff;
		SimpleDateFormat dateFormat = new SimpleDateFormat("ss S");
		Date firstParsedDate = dateFormat.parse(fromTime);
		Date secondParsedDate = dateFormat.parse(toTime);
		if (secondParsedDate.getTime() > firstParsedDate.getTime()) {
			diff = secondParsedDate.getTime() - firstParsedDate.getTime();
		} else {
			diff = secondParsedDate.getTime() + 60000 - firstParsedDate.getTime();
		}
		return Long.toString(diff);
	}
	
	
	
}
