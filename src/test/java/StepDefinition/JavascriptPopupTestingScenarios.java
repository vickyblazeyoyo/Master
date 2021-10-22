package StepDefinition;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;

import Pages.PopupTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class JavascriptPopupTestingScenarios  {

	
	
	@Given("User Login to redifmail website")
	public void user_login_to_redifmail_website() {
	    System.out.println("User Logged in the rediffMail Website");
	}

	@When("User Click on signin link")
	public void user_click_on_signin_link() throws InterruptedException, InvocationTargetException {
		
	new	PopupTesting().ClickonSignIn_Link();
		
	}

	@Then("User Click on signin Btn without providing Username and password")
	public void user_click_on_signin_btn_without_providing_username_and_password() throws InterruptedException, InvocationTargetException {
		
		new	PopupTesting().ClickonSignIn_Btn();
	}

	@Then("Validate Javascript Popup alert Message and click on OK btn")
	public void validate_javascript_popup_alert_message_and_click_on_ok_btn() throws InvocationTargetException {
		
		new	PopupTesting().popupHandling();
	}



}
