package StepDefinition;

import Pages.WindowPopupTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class WindowPopupScenarios {
	@Given("User Login to seleniumeasy website")
	public void user_login_to_seleniumeasy_website() {
	   System.out.println("Browser Launched");
	}

	@When("User Click on Follow On Twitter link")
	public void user_click_on_follow_on_twitter_link() {
	  new WindowPopupTesting().clickonfollowOnTwitterBtn();
	}

	@Then("User Click on No Thanks Button")
	public void user_click_on_no_thanks_button() {
		 new WindowPopupTesting().switchTowindow();
	}
}
