package StepDefinition;

import java.io.IOException;

import com.google.zxing.NotFoundException;

import Pages.Barcode;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BarcodeScenario extends Barcode {
	


@Given("User Login to Barcode Site")
public void user_login_to_barcode_site() {
   System.out.println("User Login to the site");
}

@When("User Clear Text Field and write new Text Click on Refresh Button")
public void user_clear_text_field_and_write_new_text_click_on_refresh_button() throws Exception {
	barCodeGetInstance();
	textArea();
}

@Then("User get the barcode Text and verify the Text")
public void user_get_the_barcode_text_and_verify_the_text() throws NotFoundException, IOException {
	//barCodeText();
	System.out.println("Barcode executed");
}

@Then("User Logout form the site")
public void user_logout_form_the_site() {
   System.out.println("User logout from the site");
}
	
}
