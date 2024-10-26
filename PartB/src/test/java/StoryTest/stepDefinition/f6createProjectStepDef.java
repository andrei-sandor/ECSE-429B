package StoryTest.stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class f6createProjectStepDef {

    private final String json = "application/json";
    private Response res;
    private Response res2;
    private String jsonString;

    //
    // Create project
    //

    @Given("I do not have a project and want to create one")
    public void i_do_not_have_a_project_and_want_to_create_one() {
    }

    @When("I create a new project with title {string}")
    public void i_create_a_new_project_with_title(String title) {
        jsonString = "{" + "\"title\": \"" + title + "\"" + "}";
        res = RestAssured.given()
                .header("Content-Type", json)
                .body(jsonString)
                .post("http://localhost:4567/projects");
    }

    @When("I create a new project with title {string} and completed {string} as a string")
    public void i_create_a_new_project_with_title_and_completed_as_a_string(String title, String completed) {
        jsonString = "{" + "\"title\": \"" + title + "\"," + "\"completed\": \"" + completed + "\"}";
        res = RestAssured.given()
                .header("Content-Type", json)
                .body(jsonString)
                .post("http://localhost:4567/projects");
    }

    @When("I create a new project with title {string}, completed {string}, active {string}, and description {string}")
    public void i_create_a_new_project_with_title_completed_active_and_description(String title, String completed, String active, String description) {
        jsonString = "{" +
                "\"title\": \"" + title + "\"," +
                "\"completed\": " + completed + "," +
                "\"active\": " + active + "," +
                "\"description\": \"" + description + "\"" +
                "}";
        res = RestAssured.given()
                .header("Content-Type", json)
                .body(jsonString)
                .post("http://localhost:4567/projects");
    }

    @Then("the status code {int} is returned")
    public void the_status_code_is_returned(int expectedStatusCode) {
        int statusCode = res.getStatusCode();
        assertEquals(expectedStatusCode, statusCode);
    }

    @Then("the error message {string} is returned")
    public void the_error_message_is_returned(String expectedErrorMessage) {
        String actualErrorMessage = (String) res.getBody().jsonPath().getList("errorMessages").get(0);

        assertEquals(expectedErrorMessage, actualErrorMessage);
    }

    @Then("a new project is created with title {string}")
    public void a_new_project_is_created_with_title(String title) {
        res2 = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/2");
        String actualTitle = res2.getBody().jsonPath().getString("projects[0].title");

        assertEquals(title, actualTitle);
    }

    @Then("a new project is created with title {string}, completed {string}, active {string}, and description {string}")
    public void a_new_project_is_created_with_title_completed_active_and_description(String title, String completed, String active, String description) {
        res2 = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/2");
        String actualTitle = res2.getBody().jsonPath().getString("projects[0].title");
        String actualCompleted = res2.getBody().jsonPath().getString("projects[0].completed");
        String actualActive = res2.getBody().jsonPath().getString("projects[0].active");
        String actualDescription = res2.getBody().jsonPath().getString("projects[0].description");

        assertEquals(title, actualTitle);
        assertEquals(completed, actualCompleted);
        assertEquals(active, actualActive);
        assertEquals(description, actualDescription);
    }
}