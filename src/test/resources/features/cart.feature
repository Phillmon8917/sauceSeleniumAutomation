@cart
Feature: Shopping cart
  As a logged in Swag Labs customer
  I want to add and remove products from my cart
  So that I only buy what I intend to

  Background:
    Given I am logged in as "standard_user" with password "secret_sauce"

  @smoke @positive
  Scenario: Adding a single product updates the cart badge
    When I add "Sauce Labs Backpack" to the cart
    Then the cart badge should show "1"
    And "Sauce Labs Backpack" should show a "Remove" button

  @positive
  Scenario: Adding multiple products keeps them all in the cart
    When I add "Sauce Labs Backpack" to the cart
    And I add "Sauce Labs Bike Light" to the cart
    Then the cart badge should show "2"
    When I open the cart
    Then the cart should contain the following products:
      | Sauce Labs Backpack   |
      | Sauce Labs Bike Light |

  @negative
  Scenario: Removing a product from the inventory page clears the cart badge
    Given I have added "Sauce Labs Backpack" to the cart
    When I remove "Sauce Labs Backpack" from the cart
    Then the cart badge should not be displayed
