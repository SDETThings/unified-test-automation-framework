Feature: Practice Extend API Testing

  Scenario: Validate health of Practice Extend application API
    When I hit the health check endpoint
    Then the response status code should be 200

  Scenario: Validate user can successfully register and login with new account
    When I register a new user with valid details
    And I login with the newly registered user credentials
    Then I should receive a successful login response

  Scenario: Validate existing user can create new note after login
    When I register a new user with valid details
    When I login with the newly registered user credentials
    And I create a new note with valid details
    Then I should see the newly created note in my notes list

  Scenario: Validate user has all privilages to create, read, update, and delete notes
    When I register a new user with valid details
    When I login with the newly registered user credentials
    And I create a new note with valid details
    Then I should see the newly created note in my notes list
    When I update the created note with new details
    Then I should see the updated note in my notes list
    When I delete the updated note
    Then I should not see the deleted note in my notes list
