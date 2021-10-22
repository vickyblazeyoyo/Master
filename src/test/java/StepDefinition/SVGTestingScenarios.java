package StepDefinition;

import java.io.IOException;
import java.text.ParseException;

import Pages.SVGHandling;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.sourceforge.tess4j.TesseractException;

public class SVGTestingScenarios extends SVGHandling {
	@Given("User Login to Emicalculator  Site")
	public void user_login_to_emicalculator_site() {
	   System.out.println("User Logged In EMIcalculator website");
	}

	@When("User SwitchTo Bar graph and read the Text")
	public void user_switch_to_bar_graph_and_read_the_text() throws Throwable {
		getInstance();
		barGraphHandling();
	}

	@Then("User clicks on LogoutBtn")
	public void user_clicks_on_logout_btn() {
		 System.out.println("User Logout from the site");
	}

	@Given("User Login to Emicalculator Site for Webtable Handling")
	public void user_login_to_emicalculator_site_for_webtable_handling() {
		System.out.println("User Logged In EMIcalculator website");
	}

	@When("User read year based data")
	public void user_read_year_based_data() {
		WebPagehandling();
	}

	@Then("User read month based data")
	public void user_read_month_based_data() {
	   System.out.println("User Logout from the site");
	}

}
