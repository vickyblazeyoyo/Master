package StepDefinition;

import Pages.DateTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DateScenarios {

	@Given("User Login to spicejet website")
	public void user_login_to_spicejet_website() {
	    System.out.println("User LogIn Successfully");
	}

	@When("User Select on DepartDate")
	public void user_select_on_depart_date() {
		new DateTesting().selectDepartDate();
	}

	@When("User Select on ReturnDate")
	public void user_select_on_return_date() {
		new DateTesting().selectReturnDate();
	}

	@Then("User Logout")
	public void user_logout() {
		
		System.out.println("User LogOut Successfully");
	}
	
}
