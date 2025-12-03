import api.testImplementation.ApiFlows;
import dataHelpers.MasterDataUtils;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.Method;

import java.io.FileNotFoundException;

public class StepDef extends ApiFlows{
    private static Scenario scenario;
    MasterDataUtils masterDataUtils;

    @Before
    public void setup(Scenario s) {
        scenario = s;

    }
    @When("I register a new user with valid details")
    public void i_register_a_new_user_with_valid_details() {
    }

    @When("I login with the newly registered user credentials")
    public void i_login_with_the_newly_registered_user_credentials() {
    }

    @When("I create a new note with valid details")
    public void i_create_a_new_note_with_valid_details() {
    }

    @Then("I should see the newly created note in my notes list")
    public void i_should_see_the_newly_created_note_in_my_notes_list() {
    }

    @When("I update the created note with new details")
    public void i_update_the_created_note_with_new_details() {
    }

    @Then("I should see the updated note in my notes list")
    public void i_should_see_the_updated_note_in_my_notes_list() {
    }

    @When("I delete the updated note")
    public void i_delete_the_updated_note() {
    }

    @Then("I should not see the deleted note in my notes list")
    public void i_should_not_see_the_deleted_note_in_my_notes_list() {
    }

    @Then("I should receive a successful login response")
    public void iShouldReceiveASuccessfulLoginResponse() {

    }

    @When("I hit the health check endpoint")
    public void iHitTheHealthCheckEndpoint() {
        //performHealthCheck(Method.GET,"HealthCheckTest","defaultClient","staging",null);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int arg0) {
    }
}
