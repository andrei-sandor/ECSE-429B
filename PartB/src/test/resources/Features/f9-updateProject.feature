Feature: Update a Project

  As a user, I want to update a project to reflect its current state.

  # Normal flow
  Scenario Outline: Update an existent project's title, completed, active, and description
    Given a project with ID <projectID> exists in the database
    When I update the project with ID <projectID> with title <title>, completed <completed>, active <active>, and description <description>
    Then the status code 200 is observed for this request
    And the project with ID <projectID> is updated with title <title>, completed <completed>, active <active>, and description <description>

    Examples:
      | projectID | title           | completed | active | description           |
      | 1         | "Updated title" | "true"    | "true" | "Updated description" |

  # Alternate flow
  Scenario Outline: Update only the title of an existent project
    Given a project with ID <id> exists in the database
    When I update the project with ID <id> with title <title>
    Then the status code 200 is observed for this request
    And the project with ID <id> is updated with title <title>

    Examples:
      | id | title           |
      | 1  | "Updated title" |

  # Error flow
  Scenario Outline: Update a non-existent project
    Given no project with ID <id> exists in the database
    When I update the project with ID <id> with title <title>
    Then the status code 404 is observed for this request

    Examples:
      | id  | title           |
      | 999 | "Updated title" |
