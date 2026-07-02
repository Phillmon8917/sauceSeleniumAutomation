@login
Feature: Login
  As a Swag Labs customer
  I want to log in with my credentials
  So that I can access the product catalog

  Background:
    Given I am on the Swag Labs login page

  @smoke @positive
  Scenario: Successful login with a standard user
    When I log in as "standard_user" with password "secret_sauce"
    Then I should be redirected to the products page

  @negative
  Scenario: Login is rejected for a locked out user
    When I log in as "locked_out_user" with password "secret_sauce"
    Then I should see the login error "Epic sadface: Sorry, this user has been locked out."

  @negative
  Scenario Outline: Login is rejected for invalid credentials
    When I log in as "<username>" with password "<password>"
    Then I should see the login error "<errorMessage>"

    Examples:
      | username      | password       | errorMessage                                                            |
      | standard_user | wrong_password | Epic sadface: Username and password do not match any user in this service |
      | unknown_user  | secret_sauce   | Epic sadface: Username and password do not match any user in this service |

  @negative
  Scenario: Login is rejected when the password is missing
    When I log in as "standard_user" with password ""
    Then I should see the login error "Epic sadface: Password is required"

  @negative
  Scenario: Login is rejected when the username is missing
    When I log in as "" with password "secret_sauce"
    Then I should see the login error "Epic sadface: Username is required"
