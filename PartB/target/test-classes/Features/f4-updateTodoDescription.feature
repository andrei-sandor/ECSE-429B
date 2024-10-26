Feature: Update Todo Description

  As a user, I want to update the description of a todo to have more accurate information about the todo.

    # Normal flow

  Scenario Outline: Update an existent todo to an another description.
    Given the API is responsive for this scenario
    When an todo with valid id <id> is attempted to change its description to description <description> by maintaining title <title> and doneStatus <doneStatus>
    Then the status code 200 will be received for this update
    Then the todo with id <id> is updated to description <description>

    Examples:
      | id  | title            | description        | doneStatus   |
      | "1" | "scan paperwork" | "new description"  | "false"      |


    # Alternate flow
  Scenario Outline: An update of an existent todo without providing a description to empty description
    When a new todo is updated with title <title> and doneStatus <doneStatus>
    And the todo is updated via id <id>
    Then the status code 200 will be received for this alternate flow
    Then the the todo with id <id> will have description <description>

    Examples:
      | id  | title          | description   | doneStatus   |
      | "1" | "new title"    |       ""      | "false"      |

    # Error flow
  Scenario Outline: Update todo with invalid id.
    When an update with id <id> is done for description with <title>, <description>, <doneStatus>
    Then the status code <statusCode> will be received for this error flow

    Examples:
      | id  | statusCode   | title       | description       | doneStatus   |
      | "0" | 404        | "new title" | "new description" | "doneStatus" |

