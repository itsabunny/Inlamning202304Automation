package stepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterMailChimpStepdefs {
    @Given("I have opened my {string} and I'm on the MailChimp sign up page")
    public void iHaveOpenedMyAndIMOnTheMailChimpSignUpPage(String browser) {

    }

    @Given("I have entered my {string} and my {string} and my {string}")
    public void iHaveEnteredMyAndMyAndMy(String emailadress, String username, String password) {
    }

    @When("I click the Sign Up button")
    public void iClickTheSignUpButton() {

    }

    @Then("I have {string} to register")
    public void iHaveToRegister(String succeeded) {
    }
}
