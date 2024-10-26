package StoryTest.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class f3deleteTodoStepDef {
    private final String json = "application/json";
    private final String xml = "application/xml";
    private Response res;
    private Response res2;
    private String jsonString;


    @Given("the API is responsive for delete")
    public void i_have_a_todo_that_does_not_exist_and_wish_to_add_one() {
    }

    @When("an todo with valid id {string} is attempted to be deleted")
    public void a_new_todo_is_created_with_title_description_and_done_status2(String id) {

        res = RestAssured.given()
                .header("Content-Type",json)
                .delete("http://localhost:4567/todos/" + id);

        // This is used to mention which user story is executed in random mode
        System.out.println("-------------------------- Executing f3---------------------------------------------------");
        //
    }

    @Then("the status code 200 will be received for deletion")
    public void deleted_201() {

        int statusCode = res.getStatusCode();
        assertEquals(200,statusCode);
    }

    @When("a new todo is created with title {string}, description {string} and doneStatus {string} for delete")
    public void a_new_todo_is_created_with_title_description_and_done_status_for_delete(String title, String description, String doneStatus) {
        jsonString = "{" + "\"title\": \"" + title + "\"," + "\"doneStatus\": "+ doneStatus +"," +
                "\"description\": \""+ description +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";
        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos");


    }

    @And("it is deleted via id {string}")
    public void it_is_deleted_via_id(String id) {
        res = RestAssured.given().delete("http://localhost:4567/todos/" + id);
    }

    @Then("the status code 200 will be received for this operation")
    public void the_status_code_200_will_be_received_for_this_operation() {
        int statusCode = res.getStatusCode();
        assertEquals(200,statusCode);
    }

    @When("a delete with id {string} is done")
    public void delete_invalid_id(String id){
        res = RestAssured.given().delete("http://localhost:4567/todos/" + id);
    }

    @Then("the status code 404 will be received for this case")
    public void the_status_code_404_will_be_received_for_this_case() {
        int statusCode = res.getStatusCode();
        assertEquals(404,statusCode);
    }


}
