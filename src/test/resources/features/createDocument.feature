Feature: Create a document into cms application as an Author

  Scenario: Verify that user with Author role is successfully able to create a document and send the request to publish it
    Given User is on cms login page
    Then Verify that user is successfully logged in as Author
    Then Verify that Author successfully creates a folder if doesn't exist already
    Then Verify that Author successfully creates a document
    And Logout and quit the Browser
