package StoryTest.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class f4updateTodoDescription {
    private final String json = "application/json";
    private final String xml = "application/xml";
    private Response res;
    private Response res2;
    private String jsonString;
    private String jsonStringNoDescription;



    @Given("the API is responsive for this scenario")
    public void the_api_is_responsive_for_this_scenario() {
    }

    @When("an todo with valid id {string} is attempted to change its description to description {string} by maintaining title {string} and doneStatus {string}")
    public void an_todo_with_valid_id_is_attempted_to_change_its_description_to_description_by_maintaining_title_and_done_status(String string, String string2, String string3, String string4) {

        jsonString = "{" + "\"title\": \"" + string3 + "\"," + "\"doneStatus\": "+ string4 +"," +
                "\"description\": \""+ string2 +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";
        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos/" + string);

        // This is used to mention which user story is executed in random mode
        System.out.println("-------------------------- Executing f4---------------------------------------------------");
        //


    }

    @Then("the status code 200 will be received for this update")
    public void update_description_200_status() {
        int statusCode = res.getStatusCode();
        assertEquals(200,statusCode);
    }

    @Then("the todo with id {string} is updated to description {string}")
    public void confirmatio_update_description_normal_flow(String id, String description) {
        String actualDescription = res.getBody().jsonPath().getString("description");
        assertEquals("new description", actualDescription);
    }

    @When("a new todo is updated with title {string} and doneStatus {string}")
    public void no_update_on_description(String title, String doneStatus){
        jsonStringNoDescription = "{" + "\"title\": \"" + title + "\"," + "\"doneStatus\": "+ doneStatus +"," +
                "\"description\": \""+ "" +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";
    }

    @And("the todo is updated via id {string}")
    public void update_alt_with_id(String id) {
        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonStringNoDescription)
                .post("http://localhost:4567/todos/" + id);

    }

    @Then("the status code 200 will be received for this alternate flow")
    public void update_description_200_status_alternate_flow() {
        int statusCode = res.getStatusCode();
        assertEquals(200,statusCode);
    }

    @Then("the the todo with id {string} will have description {string}")
    public void confirmatio_update_description_alternate_flow(String id, String description) {
        String actualDescription = res.getBody().jsonPath().getString("description");
        assertEquals("", actualDescription);
    }

    @When("an update with id {string} is done for description with {string}, {string}, {string}")
    public void an_update_with_id_is_done_for_description(String id, String title, String description, String doneStatus) {
        jsonString = "{" + "\"title\": \"" + title + "\"," + "\"doneStatus\": "+ doneStatus +"," +
                "\"description\": \""+ "" +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";

        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos/" + id);

    }

    @Then("the status code {int} will be received for this error flow")
    public void the_status_code_will_be_received_for_this_error_flow(int status) {
        int statusCode = res.getStatusCode();
        assertEquals(status ,statusCode);
    }


}
