package StepDefinition;

import Pages.ModelPopupTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ModelPopupsScenario {
	@Given("User Login to CRM Site for ModelPopups")
	public void user_login_to_crm_site_for_model_popups() {
	   System.out.println("User Logged In CRM Site");
	}

	@When("User Clicks on Chatbox")
	public void user_clicks_on_chatbox() {
	    new ModelPopupTesting().openChatBox();
	}

	@When("User Clicks on See all your conversations")
	public void user_clicks_on_see_all_your_conversations() {
		new ModelPopupTesting().seeAllYourCoversations();
	}

	@When("User Clicks Send as a message and type a message come back to chat homepage")
	public void user_clicks_send_as_a_message_and_type_a_message_come_back_to_chat_homepage() {
		new ModelPopupTesting().findAnswer();
	}

	@When("Search for crm get the suggession list")
	public void search_for_crm_get_the_suggession_list() {
		new ModelPopupTesting().searchSuggessions();
	}

	@Then("User clicks Chatbot")
	public void user_clicks_chatbot() {
	   System.out.println("User Logged Out Successfully");
	}
}
 