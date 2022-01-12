package StepDefinition;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;

import ApiUtilities.RestUtility;
import Pages.Constant;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Api_FeatureSteps {

@When("I navigate to  API Group page")
public void i_navigate_to_api_group_page() {
    
}

@When("I navigate to API page")
public void i_navigate_to_api_page() {
    
}

@When("I  using the {string} method")
public void i_using_the_method(String string) throws Throwable {
	RestUtility.executeAPIAndGetResponse(string);
}

@Then("{string} method should be successful and generates {string} response code")
public void method_should_be_successful_and_generates_response_code(String apiMethod, String expRespCode) {
	
	Constant.respCode = Integer.toString(Constant.res.getStatusCode());
	Assert.assertEquals("Expected Response code:200 and Actual Response code is : " + Constant.respCode, expRespCode, Constant.respCode);
}
}
