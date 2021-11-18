package Pages;

import java.io.IOException;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.google.zxing.NotFoundException;

import ScreenRec.MyScreenRecorder;
import Utilities.Utilities;

public class Barcode   {
WebDriver driver;

@FindBy(xpath="//textarea") WebElement textAtrea;
@FindBy(xpath="//a[contains(text(),'Refresh')]") WebElement refresh;
@FindBy(xpath="//a[contains(text(),'Create Sequence')]") WebElement createSequence;
@FindBy(xpath="//div[@class='barcode']//img") WebElement barcode;

public Barcode() {
	this.driver=Constant.driver;
	PageFactory.initElements(driver, this);
}
	
public  void barCodeGetInstance() {
	new Barcode();

}

public  void textArea() throws Exception {
	MyScreenRecorder.startRecording("textArea");
	String pdfText=Utilities.pdfReader(Utilities.ReadFromPropertyFile("PdfUrl"));
	System.out.println(pdfText);
	Utilities.explicitWait(30, textAtrea);
	textAtrea.clear();
	Utilities.sendKeys(textAtrea, Constant.Testdata.get("Text"));
	Utilities.ScrollIntoView(driver, refresh);
	Utilities.Click(refresh);
	Utilities.ScrollIntoView(driver, createSequence);
	MyScreenRecorder.stopRecording();
}
	
public void barCodeText() throws NotFoundException, IOException {
	String Code=barcode.getAttribute("src").toString();
	String CodeText=Utilities.barcodeReader(Code);
	System.out.println(CodeText);
   if (CodeText.equals(Constant.Testdata.get("Text"))) {
	Assert.assertTrue(true);
}else {
	Assert.assertTrue(false);
}
}


}
