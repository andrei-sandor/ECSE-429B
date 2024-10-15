package StoryTest.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class f2getTodoStepDef {
    private final String json = "application/json";
    private final String xml = "application/xml";
    private Response res;
    private Response res2;
    private String jsonString;


    @Given("the API is responsive")
    public void i_have_a_todo_that_does_not_exist_and_wish_to_add_one() {
    }

    @When("a todo with id {string} is queried")
    public void a_new_todo_is_created_with_title_description_and_done_status2(String string) {

        res = RestAssured.given()
                .header("Content-Type",json)
                .get("http://localhost:4567/todos/" + string);


    }

    @Then("the status code 200 will be received for this case")
    public void i_should_see_the_todo_listed_in_the_todos2() {

        int statusCode = res.getStatusCode();
        assertEquals(200,statusCode);
    }

    @Then("the the todo with id {string}, title {string}, description {string} and doneStatus {string} will be retrieved")
    public void retrieve_valid_todo(String id, String title, String description, String doneStatus){
        String actualID = res.getBody().jsonPath().getString("todos[0].id");
        String actualTitle = res.getBody().jsonPath().getString("todos[0].title");
        String actualStatus = res.getBody().jsonPath().getString("todos[0].doneStatus");
        String actualDescription = res.getBody().jsonPath().getString("todos[0].description");

        assertEquals(id,actualID );
        assertEquals(title, actualTitle);
        assertEquals(description, actualDescription);
        assertEquals(doneStatus, actualStatus);

    }

    @When("a new todo is created with title {string}, description {string} and doneStatus {string} to have in value")
    public void create_a_todo_to_get_it(String title, String description, String doneStatus){
        jsonString = "{" + "\"title\": \"" + title + "\"," + "\"doneStatus\": "+ doneStatus +"," +
                "\"description\": \""+ description +"\"," + "\"tasksof\": [" + "{" + "\"id\": \"1\"" + "}" + "]," +
                "\"categories\": [" + "{" + "\"id\": \"1\"" + "}" + "]" + "}";
        res = RestAssured.given()
                .header("Content-Type",json)
                .body(jsonString)
                .post("http://localhost:4567/todos");
    }

    @And("it is obtained via id {string}")
    public void it_is_obtained_via_id(String id){
        res = RestAssured.given()
                .get("http://localhost:4567/todos/" + id);
    }

    @Then("the the todo with title {string}, description {string} and doneStatus {string} will be retrieved")
    public void confirm_we_get_the_good_todo(String title, String description, String doneStatus){
        String actualTitle = res.getBody().jsonPath().getString("todos[0].title");
        String actualStatus = res.getBody().jsonPath().getString("todos[0].doneStatus");
        String actualDescription = res.getBody().jsonPath().getString("todos[0].description");

        assertEquals("todo1", actualTitle);
        assertEquals("newTodo", actualDescription);
        assertEquals("false",actualStatus );
    }

    @When("a get with id 0 is done")
    public void get_with_invalid_id(){
        res = RestAssured.given().delete("http://localhost:4567/todos/0");
    }

    @Then("the status code {int} will be received for this error case")
    public void error_code_invalid_id_get(int errorCode){
        int statusCode = res.getStatusCode();
        assertEquals(statusCode,errorCode);
    }
}
