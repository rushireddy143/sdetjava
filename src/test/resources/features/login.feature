Feature: Diathrive Login

  Scenario Outline: Valid and invalid login attempts
    Given I am on the Diathrive login page
    When I enter email "<email>" and password "<password>"
    And I click the login button
    Then I should see "<result>"

    Examples:
      | email                              | password    | result                     |
      | neha.kedia@technoidentity.com      | Test@12345  | login                       |