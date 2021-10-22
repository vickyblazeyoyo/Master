package StepDefinition;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import Pages.Browser;
import Pages.SetuptearDown;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/resources/Features",
		glue= {"StepDefinition","Hooks"},
		tags="@SVGHandling or @SVGWebtableHandling",
		monochrome = true,
		plugin = {"pretty","rerun:target/rerun.txt","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
				}
	
	)

public class Runner  {
@BeforeClass
public static void Setup() throws FileNotFoundException, IOException {
	SetuptearDown.setup();

}
	@AfterClass
	public static void teardown()   { 
		SetuptearDown.teardown();
		 
	} 
	
	
}
