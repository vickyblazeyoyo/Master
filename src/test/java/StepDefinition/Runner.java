package StepDefinition;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.gurock.testrail.APIException;

import Pages.Browser;
import Pages.SetuptearDown;
import Utilities.Utilities;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/resources/Features",
		glue= {"StepDefinition","Hooks"},
		tags="@SVGWebtableHandling",
		monochrome = true,
		plugin = {"pretty","rerun:target/rerun.txt","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
				}
	
	)

public class Runner  {
@BeforeClass
public static void Setup() throws FileNotFoundException, IOException, APIException {
	SetuptearDown.setup();

}
	@AfterClass
	public static void teardown()   { 
		SetuptearDown.teardown();
		 
	} 
	
	
}
