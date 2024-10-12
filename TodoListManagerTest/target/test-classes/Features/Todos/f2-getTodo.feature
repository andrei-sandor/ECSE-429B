Feature: Get todo

  As a user, I want to get a todo to analyze it more precisely.

    # Normal flow

  Scenario Outline: Successfully get a todo with valid id.
    Given the API is responsive
    When a todo with id <id> is queried
    Then the status code 200 will be received for this case
    Then the the todo with id <id>, title <title>, description <description> and doneStatus <doneStatus> will be retrieved

    Examples:
      | id     | title            | description | doneStatus  |
      | "1"    | "scan paperwork" | ""          | "false"     |

    # Alternate flow
  Scenario Outline: Successfully create a new todo and try to obtain in
    Given the API is responsive
    When a new todo is created with title <title>, description <description> and doneStatus <doneStatus> to have in value
    And it is obtained via id <id>
    Then the the todo with title <title>, description <description> and doneStatus <doneStatus> will be retrieved

    Examples:
      | id   | title   | description     | doneStatus |
      | "3" | "todo1"  | "newTodo"        | "false"    |

    # Error flow
  Scenario Outline: Get todo with invalid id.
    When a get with id 0 is done
    Then the status code <statusCode> will be received for this error case

    Examples:
      | statusCode |
      | 404        |
#
