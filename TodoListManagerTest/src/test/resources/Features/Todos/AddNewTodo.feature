Feature: Create Todo

  As a user, I want to create a new todo to monitor it with the others todo.

      # Normal Flow

      Scenario Outline: Successfully create a new todo with title, description and done status
        Given I have a todo that does not exist and wish to add one
        When a new todo is created with title <title>, description <description> and doneStatus <doneStatus>
        Then the status code 201 will be received
        Then a new todo exists in the database with title <title>, description <description> and doneStatus <doneStatus>

        Examples:
            | title   | description  | doneStatus   |
            | "todo1" | " Finish A2"  | "false"     |

      # Alternate flow
      Scenario Outline: Successfully create a new todo with title and done status
        Given I have a todo that does not exist and wish to add one
        When a new todo is created with title <title> and doneStatus <doneStatus>
        Then the status code 201 will be received again
        Then a new todo exists in the database with title <title> and doneStatus <doneStatus>

        Examples:
            | title   | doneStatus |
            | "todo2" | "false"  |
    # Error flow
    Scenario Outline: Create a new todo without title
      Given I have a todo that does not exist and wish to add one
      When a new todo is created without title, but with description <description> and doneStatus <doneStatus>
      Then the status code 400 will be received this time

        Examples:
          | description   | doneStatus   |
          | " Finish A2"  | "false"     |
