@checkout
Feature: Checkout
  As a logged in Swag Labs customer
  I want to check out the products in my cart
  So that I can complete my purchase

  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"
    And I have added "Sauce Labs Backpack" to the cart
    And I open the cart
    And I proceed to checkout

  @smoke @positive
  Scenario: Completing checkout with valid customer information
    When I fill in the checkout information with first name "John", last name "Doe" and postal code "12345"
    And I continue to the order overview
    Then the order overview should list "Sauce Labs Backpack"
    When I finish the order
    Then I should see the order confirmation "Thank you for your order!"

  @negative
  Scenario Outline: Checkout is rejected when required customer information is missing
    When I fill in the checkout information with first name "<firstName>", last name "<lastName>" and postal code "<postalCode>"
    And I continue to the order overview
    Then I should see the checkout error "<errorMessage>"

    Examples:
      | firstName | lastName | postalCode | errorMessage                     |
      |           | Doe      | 12345      | Error: First Name is required    |
      | John      |          | 12345      | Error: Last Name is required     |
      | John      | Doe      |            | Error: Postal Code is required   |
