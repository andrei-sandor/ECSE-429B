package StoryTest.stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class f9updateProjectStepDef {

    private final String json = "application/json";
    private Response res;

    //
    // Update Project
    //

    @Given("a project with ID {int} exists in the database")
    public void a_project_with_id_exists_in_the_database(int projectID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(200, res.getStatusCode(), "Expected project to exist.");
    }

    @Given("no project with ID {int} exists in the database")
    public void no_project_with_id_exists_in_the_database(int projectID) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);
        assertEquals(404, res.getStatusCode(), "Expected project to not exist.");
    }

    @When("I update the project with ID {int} with title {string}, completed {string}, active {string}, and description {string}")
    public void i_update_the_project_with_title_completed_active_and_description(int projectID, String title, String completed, String active, String description) {
        String jsonString = "{" +
                "\"title\": \"" + title + "\"," +
                "\"completed\": " + completed + "," +
                "\"active\": " + active + "," +
                "\"description\": \"" + description + "\"" +
                "}";

        res = RestAssured.given()
                .header("Content-Type", json)
                .body(jsonString)
                .put("http://localhost:4567/projects/" + projectID);
    }

    @When("I update the project with ID {int} with title {string}")
    public void i_update_the_project_with_id_with_title(int projectID, String title) {
        String jsonString = "{\"title\": \"" + title + "\"}";

        res = RestAssured.given()
                .header("Content-Type", json)
                .body(jsonString)
                .put("http://localhost:4567/projects/" + projectID);
    }

    @Then("the status code {int} is observed for this request")
    public void the_status_code_is_observed_for_this_request(int expectedStatusCode) {
        int statusCode = res.getStatusCode();
        assertEquals(expectedStatusCode, statusCode);
    }

    @Then("the project with ID {int} is updated with title {string}, completed {string}, active {string}, and description {string}")
    public void the_project_with_id_is_updated_with_title_completed_active_and_description(int projectID, String title, String completed, String active, String description) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);

        String actualTitle = res.getBody().jsonPath().getString("projects[0].title");
        String actualCompleted = res.getBody().jsonPath().getString("projects[0].completed");
        String actualActive = res.getBody().jsonPath().getString("projects[0].active");
        String actualDescription = res.getBody().jsonPath().getString("projects[0].description");

        assertEquals(title, actualTitle);
        assertEquals(completed, actualCompleted);
        assertEquals(active, actualActive);
        assertEquals(description, actualDescription);
    }

    @Then("the project with ID {int} is updated with title {string}")
    public void the_project_with_id_is_updated_with_title(int projectID, String title) {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/" + projectID);

        String actualTitle = res.getBody().jsonPath().getString("projects[0].title");
        assertEquals(title, actualTitle);
    }
}
