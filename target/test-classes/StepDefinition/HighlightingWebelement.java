package StepDefinition;

import java.io.IOException;

import Pages.HighlightingWebelementscenarios;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HighlightingWebelement {

	@Given("User Login to FreeCRM Site")
	public void user_login_to_free_crm_site() {
	   
		System.out.println("Login done");
	}

	@When("User Highlighted webelement")
	public void user_highlighted_webelement() throws Throwable {
	    new HighlightingWebelementscenarios().highlightElement();
		
	}

	@Then("User logout successfully")
	public void user_logout_successfully() {
	   
		System.out.println("Logout done");
	}
	
}
