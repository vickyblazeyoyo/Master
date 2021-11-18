package StepDefinition;

import Pages.Frames;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FramesTesting  {
	
	@Given("User Login to CRM Site")
	public void user_login_to_crm_site() {
	   
		new Frames().login();
	}

	@When("User SwitchTo frames")
	public void user_switch_to_frames() {
		new Frames().frameHandling();
		
	}

	@Then("User clicks on Contacts Link")
	public void user_clicks_on_contacts_link() {
		new Frames().selectContact();
		
	}
}
