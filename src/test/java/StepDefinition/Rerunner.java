package StepDefinition;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import Pages.SetuptearDown;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="@target/rerun.txt",
		glue= {"StepDefinition","Hooks"},
		monochrome = true
	
	
	)
public class Rerunner {

@BeforeClass
public static void Setup() throws FileNotFoundException, IOException {
	SetuptearDown.setup();

}
	@AfterClass
	public static void teardown()   { 
		SetuptearDown.teardown();
		 
	} 
	
	

}
