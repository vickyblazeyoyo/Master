package StepDefinition;

import Pages.BoopstrapDropdownTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BosstrapDropdown {

	@Given("User Login to jquery website")
	public void user_login_to_jquery_website() {
	   System.out.println("User LogIN Successfully");
	}

	@When("User Click on Output dropdown")
	public void user_click_on_output_dropdown() {
		 new BoopstrapDropdownTesting().selectdropdownBtn();
	}

	@Then("User Select on all the options in the dropdown")
	public void user_select_on_all_the_options_in_the_dropdown() {
		new BoopstrapDropdownTesting().selectAlloptions();
	}

	@Then("User Select  one option {string}")
	public void user_select_one_option(String string) {
		new BoopstrapDropdownTesting().selectOneOption(string);
	}
	
}
