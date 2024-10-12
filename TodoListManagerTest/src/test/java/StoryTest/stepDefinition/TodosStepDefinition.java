package StoryTest.stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodosStepDefinition {

    private final String json = "application/json";
    private final String xml = "application/xml";
    private Response res;
    private Response res2;
    private String jsonString;


    // --------------- Feature: Add a new todos -------------//

    @Given("I have a todo that does not exist and wish to add one")
    public void i_have_a_todo_that_does_not_exist_and_wish_to_add_one() {
    }

    @When("a new todo is created with title {string}, description {string} and doneStatus {string}")
    public void a_new_todo_is_created_with_title_description_and_done_status(String string, String string2, String string3) {
        jsonString = "{" + "\"title\": \"" + string + "\"," + "\"doneStatus\": "+ string3 +"," +
                "\"description\": \""+string2+"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";
        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos");


    }

    @Then("the status code 201 will be received")
    public void i_should_see_the_todo_listed_in_the_todos() {

        int statusCode = res.getStatusCode();
        assertEquals(201,statusCode);
    }

    @Then("a new todo exists in the database with title {string}, description {string} and doneStatus {string}")
    public void i_should_see_the_todo_listed_in_the_todos2(String string, String string2, String string3) {

        res2 = RestAssured.given().header("Accept",json).get("http://localhost:4567/todos/3");
        String actualTitle = res2.getBody().jsonPath().getString("todos[0].title");
        String actualDescription = res2.getBody().jsonPath().getString("todos[0].description");
        String actualDoneStatus = res2.getBody().jsonPath().getString("todos[0].doneStatus");

        assertEquals(string, actualTitle);
        assertEquals(string2, actualDescription);
        assertEquals(string3, actualDoneStatus);

    }

    @When("a new todo is created with title {string} and doneStatus {string}")
    public void a_new_todo_is_created_with_title_and_done_status(String string, String string2) {
        jsonString = "{" + "\"title\": \"" + string + "\"," + "\"doneStatus\": "+ string2 +"," +
                "\"description\": \""+ "" +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";
        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos");


    }

    @Then("the status code 201 will be received again")
    public void status_code_alternate_scenario_should_be_201() {

        int statusCode = res.getStatusCode();
        assertEquals(201,statusCode);
    }

    @Then("a new todo exists in the database with title {string} and doneStatus {string}")
    public void i_should_see_the_todo_listed_in_the_todo3(String string, String string2) {

        res2 = RestAssured.given().header("Accept",json).get("http://localhost:4567/todos/3");
        String actualTitle = res2.getBody().jsonPath().getString("todos[0].title");
        String actualDescription = res2.getBody().jsonPath().getString("todos[0].description");
        String actualDoneStatus = res2.getBody().jsonPath().getString("todos[0].doneStatus");

        assertEquals(string, actualTitle);
        assertEquals(string2, actualDoneStatus);
        assertEquals("", actualDescription);

    }

    @When("a new todo is created without title, but with description {string} and doneStatus {string}")
    public void a_new_todo_is_created_without_title_but_with_description_and_done_status(String string, String string2) {
        jsonString = "{" + "\"title\": \"" + "" + "\"," + "\"doneStatus\": "+ string2 +"," +
                "\"description\": \""+ string +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";
        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos");


    }

    @Then("the status code {int} will be received this time")
    public void the_status_code_will_be_received(Integer int1) {

        int statusCode = res.getStatusCode();
        assertEquals(400,statusCode);
    }



}
