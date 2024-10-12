Feature: Delete todo

  As a user, I want to delete a todo to remove it from the list of todos.

  Background:
    Given the API is responsive


    # Normal flow

  Scenario Outline: Successfully delete a todo with valid id.
    When an todo with valid id "<id>" is attempted to be deleted (feature3)
    Then the status code 201 will be received (feature3)
    Then the todo with id "<id>" is deleted (feature3)

    Examples:
      | title   | description | doneStatus |
#
#
#    # Alternate flow
#  Scenario Outline: Successfully create a new todo and try to delete it
#    When a new todo is created with title "<title>", description "<description>" and doneStatus "<doneStatus>" (feature3)
#    And it is deleted via id "<id>" (feature3)
#    Then the status code 201 will be received (feature3)
#    Then the the todo with "<title>", description "<description>" and doneStatus "<doneStatus>" will be deleted (feature3)
#
#    Examples:
#      | title | description | doneStatus |
#
#    # Error flow
#  Scenario Outline: Delete todo with invalid id.
#    When a delete with id 0 is done (feature3)
#    Then the status code 400 will be received (feature3)
#
#    Examples:
#      | statusCode |
#      | 400        |
#
#  Scenario: Teardown
#    Then the system is destroyed to restart to the initial state at the next test.