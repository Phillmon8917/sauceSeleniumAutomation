package com.saucedemo.stepdefinitions;

import com.saucedemo.config.Pages;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class CartSteps {

    @Given("I have added {string} to the cart")
    @When("I add {string} to the cart")
    public void iAddProductToTheCart(String productName) {
        Pages.get(InventoryPage.class).addProductToCart(productName);
    }

    @When("I remove {string} from the cart")
    public void iRemoveProductFromTheCart(String productName) {
        Pages.get(InventoryPage.class).removeProductFromCart(productName);
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        Pages.get(InventoryPage.class).openCart();
        Assert.assertTrue(Pages.get(CartPage.class).isLoaded(), "Expected the cart page to load");
    }

    @Then("the cart badge should show {string}")
    public void theCartBadgeShouldShow(String expectedCount) {
        InventoryPage inventoryPage = Pages.get(InventoryPage.class);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), Integer.parseInt(expectedCount));
    }

    @Then("the cart badge should not be displayed")
    public void theCartBadgeShouldNotBeDisplayed() {
        InventoryPage inventoryPage = Pages.get(InventoryPage.class);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 0, "Expected no cart badge to be displayed");
    }

    @Then("{string} should show a {string} button")
    public void productShouldShowButton(String productName, String expectedButtonLabel) {
        InventoryPage inventoryPage = Pages.get(InventoryPage.class);
        boolean inCart = inventoryPage.isProductInCart(productName);
        if ("Remove".equalsIgnoreCase(expectedButtonLabel)) {
            Assert.assertTrue(inCart, "Expected " + productName + " to show a Remove button");
        } else {
            Assert.assertFalse(inCart, "Expected " + productName + " to show an Add to cart button");
        }
    }

    @Then("the cart should contain the following products:")
    public void theCartShouldContainTheFollowingProducts(List<String> expectedProducts) {
        CartPage cartPage = Pages.get(CartPage.class);
        Assert.assertEquals(cartPage.getItemCount(), expectedProducts.size());
        Assert.assertTrue(cartPage.getItemNames().containsAll(expectedProducts),
                "Expected cart to contain: " + expectedProducts + " but found: " + cartPage.getItemNames());
    }
}
