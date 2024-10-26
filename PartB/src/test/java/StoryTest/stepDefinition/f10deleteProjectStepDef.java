package StoryTest.stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.*;

public class f10deleteProjectStepDef {

    private final String json = "application/json";
    private Response res;

    //
    // Delete Project
    //

    @Given("a project with ID {int} is stored in the database")
    public void a_project_with_id_is_stored_in_the_database(int projectID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(200, res.getStatusCode(), "Expected project to exist.");
    }

    @Given("a project with ID {int} exists and has tasks")
    public void a_project_with_id_exists_and_has_tasks(int projectID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID + "/tasks");
        assertEquals(200, res.getStatusCode(), "Expected project with tasks to exist.");
    }

    @Given("no project with ID {int} is stored in the database")
    public void no_project_with_id_is_stored_in_the_database(int projectID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(404, res.getStatusCode(), "Expected project to not exist.");
    }

    @Given("the project with ID {int} has no associated tasks")
    public void the_project_with_id_has_no_associated_tasks(int projectID) {
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .get("http://localhost:4567/projects/" + projectID);

        assertEquals(200, response.getStatusCode(), "Expected successful retrieval of project.");

        List<Map<String, Object>> tasks = response.getBody().jsonPath().getList("projects[0].tasks");

        for (Map<String, Object> task : tasks) {
            int taskID = Integer.parseInt(task.get("id").toString());
            Response deleteResponse = RestAssured.given()
                    .header("Accept", "application/json")
                    .delete("http://localhost:4567/projects/" + projectID + "/tasks/" + taskID);

            Assert.assertEquals(deleteResponse.getStatusCode(), 200, "Failed to delete task relationship with ID " + taskID + " from project with ID " + projectID);
        }
    }

    @Given("the project with ID {int} has associated tasks")
    public void the_project_with_id_has_associated_tasks(int projectID) {
        Response response = RestAssured.given()
                .header("Accept", "application/json")
                .get("http://localhost:4567/projects/" + projectID);

        assertEquals(200, response.getStatusCode(), "Expected successful retrieval of project.");

        List<Map<String, Object>> tasks = response.getBody().jsonPath().getList("projects[0].tasks");

        assertNotNull(tasks, "Expected 'tasks' field to be present in the response, but it was null.");
        assertFalse(tasks.isEmpty(), "Expected project to have associated tasks, but the tasks list is empty.");
    }


    @When("I delete the project with ID {int}")
    public void i_delete_the_project_with_id(int projectID) {
        res = RestAssured.given()
                .header("Content-Type", json)
                .delete("http://localhost:4567/projects/" + projectID);
    }

    @Then("the status code {int} appears")
    public void the_status_code_appears(int expectedStatusCode) {
        int statusCode = res.getStatusCode();
        assertEquals(expectedStatusCode, statusCode);
    }

    @Then("the project with ID {int} is deleted")
    public void the_project_with_id_is_deleted(int projectID) {
        Response getResponse = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(404, getResponse.getStatusCode(), "Expected project to be deleted.");
    }
}