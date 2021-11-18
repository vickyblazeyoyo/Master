package StepDefinition;

import Pages.Webtabletesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WebtableScenarios extends Webtabletesting {
	
	@Given("User Login to dezlearn Site")
	public void user_login_to_dezlearn_site() {
	    System.out.println("Login done");
	}

	@When("User select webtable values and update the table")
	public void user_select_webtable_values_and_update_the_table() {
		Webtabletesting.getInstance();
	    headerPrint();
	   printNamesFromWebTable();
	   printEmail();
	   clickAllCheckbox();
	   SelectAllDropdown();
	   CommentsField();
	   UpdateAndMessageValidation();
	   singleSelectOption("John White", "Standard", "Suv", "Vicky");
	   UpdateAndMessageValidation();
	}

	@Then("User logout from the website")
	public void user_logout_from_the_website() {
		System.out.println("Logout done");
	}
}
