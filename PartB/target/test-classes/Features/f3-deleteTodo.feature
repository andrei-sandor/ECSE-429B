Feature: Delete todo

  As a user, I want to delete a todo to remove it from the list of todos.

  Background:



    # Normal flow

  Scenario Outline: Successfully delete a todo with valid id.
    Given the API is responsive for delete
    When an todo with valid id <id> is attempted to be deleted
    Then the status code 200 will be received for deletion

    Examples:
      | id  |
      | "1" |


    # Alternate flow
  Scenario Outline: Successfully create a new todo and try to delete it
    Given the API is responsive for delete
    When a new todo is created with title <title>, description <description> and doneStatus <doneStatus> for delete
    And it is deleted via id <id>
    Then the status code 200 will be received for this operation

    Examples:
      | id  | title                | description | doneStatus |
      | "3" | "todo to be deleted" | "none"      | "false"    |

    # Error flow
  Scenario Outline: Delete todo with invalid id.
    When a delete with id <id> is done
    Then the status code 404 will be received for this case

    Examples:
      | id  |
      | "0" |

