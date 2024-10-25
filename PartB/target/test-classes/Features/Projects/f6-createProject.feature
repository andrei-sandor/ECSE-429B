Feature: Create Project

  As a user, I want to create a new project so that I can start working on it.

    # Normal flow
    Scenario Outline: Create a new project with title, completed, active, and description fields
      Given I do not have a project and want to create one
      When I create a new project with title <title>, completed <completed>, active <active>, and description <description>
      Then the status code 201 is returned
      And a new project is created with title <title>, completed <completed>, active <active>, and description <description>

      Examples:
        | title        | completed | active | description     |
        | "Project A"  | false     | true   | "Deliverable A" |

    # Alternate flow
    Scenario Outline: Create a new project with title only
      Given I do not have a project and want to create one
      When I create a new project with title <title>
      Then the status code 201 is returned
      And a new project is created with title <title>

      Examples:
        | title        |
        | "Project B"  |

    # Error flow
    Scenario Outline: Create a new project with string instead of boolean for completed
      Given I do not have a project and want to create one
      When I create a new project with title <title> and completed <completed>
      Then the status code 400 is returned
      And the error message "Failed Validation: completed should be BOOLEAN" is returned

      Examples:
        | title        | completed |
        | "Project C"  | "false"   |




