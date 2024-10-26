package StoryTest.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class f5updateDoneStatus {
    private final String json = "application/json";
    private final String xml = "application/xml";
    private Response res;
    private Response res2;
    private String jsonString;
    private String jsonStringNoDescription;



    @Given("the API is responsive for this update")
    public void the_api_is_responsive_for_this_scenario() {
    }

    @When("an todo with valid id {string} is attempted to change its doneStatus to doneStatus {string} by maintaining title {string} and description {string}")
    public void an_todo_with_valid_id_is_attempted_to_change_its_status_by_maintaining_title_and_description(String string, String string2, String string3, String string4) {

        jsonString = "{" + "\"title\": \"" + string3 + "\"," + "\"doneStatus\": "+ string2 +"," +
                "\"description\": \""+ string4 +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";
        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos/" + string);

        // This is used to mention which user story is executed in random mode
        System.out.println("-------------------------- Executing f5---------------------------------------------------");
        //

    }

    @Then("the status code 200 will be received for the change")
    public void update_description_200_status_done() {
        int statusCode = res.getStatusCode();
        assertEquals(200,statusCode);
    }

    @Then("the todo with id {string} is updated to doneStatus {string}")
    public void the_todo_with_id_is_updated_to_done_status(String string, String string2) {
        String actualStatus = res.getBody().jsonPath().getString("doneStatus");
        assertEquals("true", actualStatus);
    }

    @When("an todo with valid id {string} is attempted to change its doneStatus to doneStatus false {string} by maintaining title {string} and description {string}")
    public void an_todo_with_valid_id_is_attempted_to_change_status_to_false(String id, String status, String title, String description){
        jsonString = "{" + "\"title\": \"" + title + "\"," + "\"doneStatus\": "+ status +"," +
                "\"description\": \""+ "" +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";

        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos/" + id);
    }


    @Then("the status code 200 will be received for this new change")
    public void update_description_200_status_alternate_flow_false() {
        int statusCode = res.getStatusCode();
        assertEquals(200,statusCode);
    }

    @Then("the todo with id {string} is updated to doneStatus {string} false")
    public void confirmatio_update_description_alternate_flow(String id, String status) {
        String actualStatus = res.getBody().jsonPath().getString("doneStatus");
        assertEquals("false", actualStatus);
    }

    @When("an update with id {int} is done for changing status")
    public void an_update_with_id_is_done_for_changing_status(Integer id) {
        jsonString = "{" + "\"title\": \"" + "" + "\"," + "\"doneStatus\": "+ "" +"," +
                "\"description\": \""+ "" +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";

        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos/" + id);

    }

    @Then("the status code {int} will be received for for changing status")
    public void the_status_code_will_be_received_for_for_changing_status(int string) {
        int statusCode = res.getStatusCode();
        assertEquals(string ,statusCode);
    }
}
