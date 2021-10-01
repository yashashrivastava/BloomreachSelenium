Feature: Login into cms application

  Scenario: Verify that user is able to successfully login to cms application
    Given User is on cms login page
    Then Verify that user is successfully logged in as Author
    And Logout and quit the Browser