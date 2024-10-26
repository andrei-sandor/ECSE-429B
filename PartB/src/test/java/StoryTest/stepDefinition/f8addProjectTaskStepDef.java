package StoryTest.stepDefinition;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class f8addProjectTaskStepDef {

    private final String json = "application/json";
    private Response res;

    //
    // Add project task
    //

    @Given("a project with ID {int} exists")
    public void a_project_with_id_exists(int projectID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(200, res.getStatusCode(), "Expected project to exist.");
    }

    @Given("a todo with ID {int} exists with title {string}, description {string}, and done status {string}")
    public void a_todo_with_id_exists_with_title_description_and_done_status(int todoID, String title, String description, String doneStatus) {
        String jsonBody = "{" +
                "\"title\": \"" + title + "\"," +
                "\"description\": \"" + description + "\"," +
                "\"doneStatus\": " + doneStatus +
                "}";

        res = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .post("http://localhost:4567/todos/" + todoID);

        assertTrue(res.getStatusCode() == 200 || res.getStatusCode() == 204, "Expected successful amendment of todo.");

        res = RestAssured.given()
                .header("Accept", "application/json")
                .get("http://localhost:4567/todos/" + todoID);

        assertEquals(200, res.getStatusCode(), "Expected todo to exist with specified details after amendment.");

        String actualTitle = res.getBody().jsonPath().getString("todos[0].title");
        String actualDescription = res.getBody().jsonPath().getString("todos[0].description");
        String actualDoneStatus = res.getBody().jsonPath().getString("todos[0].doneStatus");

        assertEquals(title, actualTitle, "Todo title does not match expected value.");
        assertEquals(description, actualDescription, "Todo description does not match expected value.");
        assertEquals(doneStatus, actualDoneStatus, "Todo done status does not match expected value.");
    }


    @Given("a todo with ID {int} exists with a title and no description")
    public void a_todo_with_id_exists_with_title_and_no_description(int todoID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/todos/" + todoID);
        assertEquals(200, res.getStatusCode(), "Expected todo to exist with title only.");
    }

    @Given("no project with ID {int} exists")
    public void no_project_with_id_exists(int projectID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(404, res.getStatusCode(), "Expected project to not exist.");
    }

    @Given("a todo with ID {int} exists")
    public void a_todo_with_id_exists(int todoID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/todos/" + todoID);
        assertEquals(200, res.getStatusCode(), "Expected todo to exist.");
    }

    @When("the todo with ID {int} is added to the project with ID {int}")
    public void the_todo_with_id_is_added_to_the_project_with_id(int todoID, int projectID) {
        String jsonString = "{" + "\"id\": \"" + todoID + "\"}";
        res = RestAssured.given()
                .header("Content-Type", json)
                .body(jsonString)
                .post("http://localhost:4567/projects/" + projectID + "/tasks");
    }

    @Then("the status code {int} is observed")
    public void the_status_code_is_observed(int expectedStatusCode) {
        int statusCode = res.getStatusCode();
        assertEquals(expectedStatusCode, statusCode);
    }

    @Then("the todo with ID {int} is a task of the project with ID {int}")
    public void the_todo_with_id_is_a_task_of_the_project_with_id(int todoID, int projectID) {
        res = RestAssured.given()
                .header("Accept", "application/json")
                .get("http://localhost:4567/projects/" + projectID);

        assertEquals(200, res.getStatusCode(), "Expected successful retrieval of project.");

        List<Map<String, Object>> tasks = res.getBody().jsonPath().getList("projects[0].tasks");

        boolean isTodoAdded = tasks.stream().anyMatch(task -> task.get("id").toString().equals(String.valueOf(todoID)));

        assertTrue(isTodoAdded, "Expected todo with ID " + todoID + " to be added to the project with ID " + projectID + ".");
    }
}
