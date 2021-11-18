package StepDefinition;

import Pages.OTPTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OTPScenarios extends OTPTesting {

	@Given("User Login to amzon  Site")
	public void user_login_to_amzon_site() {
	   System.out.println("User Logged In the Website");
	}

	@When("User Switchover signin dropdown and click on  starthere Link")
	public void user_switchover_signin_dropdown_and_click_on_starthere_link() throws Exception {
		OTPTesting.getInstance();
		newCustomerCreation();
	}

	@When("Enter yourname,mobileNumber,password,passwordAgain Click on continew")
	public void enter_yourname_mobile_number_password_password_again_click_on_continew() {
		CrateAccount();
	}

	@When("Enter Otp and Click on ResendOtp verify success Message")
	public void enter_otp_and_click_on_resend_otp_verify_success_message() throws Exception {
		EnterOtp();
	}

	@Then("Logout from the website")
	public void logout_from_the_website() {
	    System.out.println("User Logout Successfully");
	}

	
	
}
