Feature: Get All Projects

  As a user, I want to get a list of all projects to view the state of ongoing work.

  # Normal flow
  Scenario: Get all projects when projects exist
    Given there are projects in the database
    When I request all projects
    Then the status code 200 is returned for this request
    And the response contains a non-empty list of projects

  # Alternate flow
  Scenario: Get all projects when no projects exist
    Given there are no projects in the database
    When I request all projects
    Then the status code 200 is returned for this request
    And the response contains an empty list of projects

  # Error flow
  Scenario Outline: Get projects with an invalid endpoint
    Given I have an incorrect API endpoint <invalidEndpoint>
    When I try to retrieve all projects
    Then the status code 404 is returned for this request

    Examples:
      | invalidEndpoint |
      | "/pr0jects"     |