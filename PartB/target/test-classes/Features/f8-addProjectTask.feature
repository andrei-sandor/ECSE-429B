Feature: Add a Task to a Project

  As a user, I want to add a task to a project to track its progress.

  # Normal flow
  Scenario Outline: Add a detailed task to a project
    Given a project with ID <projectID> exists
    And a todo with ID <todoID> exists with title <todoTitle>, description <todoDescription>, and done status <doneStatus>
    When the todo with ID <todoID> is added to the project with ID <projectID>
    Then the status code 201 is observed
    And the todo with ID <todoID> is a task of the project with ID <projectID>

    Examples:
      | projectID | todoID | todoTitle        | todoDescription | doneStatus |
      | 1         | 1      | "scan paperwork" | "use printer"   | "false"    |

  # Alternate flow
  Scenario Outline: Add a non-detailed task to a project
    Given a project with ID <projectID> exists
    And a todo with ID <todoID> exists with a title and no description
    When the todo with ID <todoID> is added to the project with ID <projectID>
    Then the status code 201 is observed
    And the todo with ID <todoID> is a task of the project with ID <projectID>

    Examples:
      | projectID | todoID |
      | 1         | 2      |

  # Error flow
  Scenario Outline: Add a task to a non-existent project
    Given no project with ID <projectID> exists
    And a todo with ID <todoID> exists
    When the todo with ID <todoID> is added to the project with ID <projectID>
    Then the status code 404 is observed

    Examples:
      | projectID | todoID |
      | 999       | 1      |


