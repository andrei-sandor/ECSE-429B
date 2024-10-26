Feature: Delete a Project

  As a user, I want to delete a project to remove completed or abandoned projects.

  # Normal flow
  Scenario Outline: Delete a project that has no associated tasks
    Given a project with ID <id> is stored in the database
    And the project with ID <id> has no associated tasks
    When I delete the project with ID <id>
    Then the status code 200 appears
    And the project with ID <id> is deleted

    Examples:
      | id |
      | 1  |

  # Alternate flow
  Scenario Outline: Delete a project that has associated tasks
    Given a project with ID <id> exists and has tasks
    And the project with ID <id> has associated tasks
    When I delete the project with ID <id>
    Then the status code 200 appears
    And the project with ID <id> is deleted

    Examples:
      | id |
      | 1  |

  # Error flow
  Scenario Outline: Delete a non-existent project
    Given no project with ID <id> is stored in the database
    When I delete the project with ID <id>
    Then the status code 404 appears

    Examples:
      | id  |
      | 999 |