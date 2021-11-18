package StepDefinition;

import java.io.IOException;

import Pages.ActionsTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ActionTestingScenarios {

	
	@Given("User Login to Spicejet Site")
	public void user_login_to_spicejet_site() {
	    System.out.println("User LoggedIn successfully");
		
	}

	@When("User mouse over to Add-ons tab")
	public void user_mouse_over_to_add_ons_tab() {
	 new ActionsTesting().mouseovertoaddons();
	}

	@Then("Pring all the subtab menu of Add-ons")
	public void pring_all_the_subtab_menu_of_add_ons() {
		new ActionsTesting().Printallvalues();
	}
	
	
	@Given("User Login to jqueryui Site")
	public void user_login_to_jqueryui_site() {
	    System.out.println("User login jquery site");
	}

	@When("User perform DragandDrop")
	public void user_perform_dragand_drop() throws Throwable {
		
		new ActionsTesting().dragAndDrop();
	}

	@Then("user logout from the webpage")
	public void user_logout_from_the_webpage() {
	   System.out.println("Close the window");
	}


}
