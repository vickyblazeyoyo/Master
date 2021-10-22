package StepDefinition;

import java.io.IOException;

import Pages.BrokenLinksTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BrokenLinksScenario {
	
	@Given("User Login to CRM Site for Brokenlinks")
	public void user_login_to_crm_site_for_brokenlinks() {
	    System.out.println("Login Successfully");
	}

	@When("User Find broken Links")
	public void user_find_broken_links() throws IOException {
		//new BrokenLinksTesting().login();
		new BrokenLinksTesting().Links();
	}

	@Then("User Logout from the CRM site")
	public void user_logout_from_the_crm_site() {
		System.out.println("Logout Successfully");
	}


}
