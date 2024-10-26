package StoryTest.stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class f7getProjectsStepDef {

    private final String json = "application/json";
    private String endpoint;
    private Response res;

    //
    // Get projects
    //

    @Given("there are projects in the database")
    public void there_are_projects_in_the_database() {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/1");

        assertEquals(200, res.getStatusCode());
    }

    @Given("there are no projects in the database")
    public void there_are_no_projects_in_the_database() {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects/1");

        if (res.getStatusCode() == 200) {
            RestAssured.given()
                    .header("Content-Type", json)
                    .delete("http://localhost:4567/projects/1")
                    .then()
                    .assertThat()
                    .statusCode(200);
        }
    }

    @Given("I have an incorrect API endpoint {string}")
    public void i_have_an_incorrect_api_endpoint(String invalidEndpoint) {
        endpoint = invalidEndpoint;
    }

    @When("I request all projects")
    public void i_request_all_projects() {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567/projects");

        // This is used to mention which user story is executed in random mode
        System.out.println("-------------------------- Executing f7---------------------------------------------------");
        //
    }

    @When("I try to retrieve all projects")
    public void i_try_to_retrieve_all_projects() {
        res = RestAssured.given()
                .header("Accept", json)
                .get("http://localhost:4567" + endpoint);
    }

    @Then("the status code {int} is returned for this request")
    public void the_status_code_is_returned_for_this_request(int expectedStatusCode) {
        int statusCode = res.getStatusCode();
        assertEquals(expectedStatusCode, statusCode);
    }

    @Then("the response contains a non-empty list of projects")
    public void the_response_contains_a_non_empty_list_of_projects() {
        List<Map<String, Object>> projects = res.getBody().jsonPath().getList("projects");

        assertFalse(projects.isEmpty(), "Expected a non-empty list of projects, but found none.");

        for (Map<String, Object> project : projects) {
            assertTrue(project.containsKey("id"), "Project is missing 'id' field");
            assertTrue(project.containsKey("title"), "Project is missing 'title' field");
            assertTrue(project.containsKey("description"), "Project is missing 'description' field");
            assertTrue(project.containsKey("completed"), "Project is missing 'completed' field");
            assertTrue(project.containsKey("active"), "Project is missing 'active' field");
        }
    }

    @Then("the response contains an empty list of projects")
    public void the_response_contains_an_empty_list_of_projects() {
        boolean isProjectListEmpty = res.getBody().jsonPath().getList("projects").isEmpty();
        assertTrue(isProjectListEmpty, "Expected an empty list of projects, but found some.");
    }
}
