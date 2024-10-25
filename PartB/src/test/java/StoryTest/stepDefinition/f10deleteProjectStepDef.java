package StoryTest.stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class f10deleteProjectStepDef {

    private final String json = "application/json";
    private Response res;

    //
    // Delete Project
    //

    @Given("a project with ID {int} exists")
    public void a_project_with_id_exists(int projectID) {
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

    @Given("no project with ID {int} exists")
    public void no_project_with_id_exists(int projectID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(404, res.getStatusCode(), "Expected project to not exist.");
    }

    @When("I delete the project")
    public void i_delete_the_project() {
        int projectID = res.getBody().jsonPath().getInt("id");
        res = RestAssured.given()
                .header("Content-Type", json)
                .delete("http://localhost:4567/projects/" + projectID);
    }

    @Then("the status code {int} is returned")
    public void the_status_code_is_returned(int expectedStatusCode) {
        int statusCode = res.getStatusCode();
        assertEquals(expectedStatusCode, statusCode);
    }

    @Then("the project is deleted")
    public void the_project_is_deleted() {
        int projectID = res.getBody().jsonPath().getInt("id");
        Response getResponse = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(404, getResponse.getStatusCode(), "Expected project to be deleted.");
    }

    @Then("the project with its associated tasks are deleted")
    public void the_project_with_its_associated_tasks_are_deleted() {
        int projectID = res.getBody().jsonPath().getInt("id");
        Response getProjectResponse = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(404, getProjectResponse.getStatusCode(), "Expected project to be deleted.");

        Response getTasksResponse = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID + "/tasks");
        assertEquals(404, getTasksResponse.getStatusCode(), "Expected tasks to be deleted along with the project.");
    }
}