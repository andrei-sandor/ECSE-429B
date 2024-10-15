Feature: Update Todo done status

  As a user, I want to update the done status of a todo to monitor my done tasks.



    # Normal flow

  Scenario Outline: Update an existent todo doneStatus to true.
    Given the API is responsive for this update
    When an todo with valid id <id> is attempted to change its doneStatus to doneStatus <doneStatus> by maintaining title <title> and description <description>
    Then the status code 200 will be received for the change
    Then the todo with id <id> is updated to doneStatus <doneStatus>

    Examples:
      | id  |  title           | description      | doneStatus   |
      | "1" | "scan paperwork" | ""               | "true"       |


    # Alternate flow
  Scenario Outline: Update an existent todo doneStatus from true to false
    When an todo with valid id <id> is attempted to change its doneStatus to doneStatus false <doneStatus> by maintaining title <title> and description <description>
    Then the status code 200 will be received for this new change
    Then the todo with id <id> is updated to doneStatus <doneStatus> false

    Examples:
      | id  |  title           | description | doneStatus    |
      | "1" | "scan paperwork" | ""          | "false"       |

    # Error flow
  Scenario Outline: Update todo with invalid id.
    When an update with id <id> is done for changing status
    Then the status code <statusCode> will be received for for changing status

    Examples:
      | id  | statusCode  |
      | 0   | 404         |
