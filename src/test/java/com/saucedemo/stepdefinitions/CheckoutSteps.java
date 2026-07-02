package com.saucedemo.stepdefinitions;

import com.saucedemo.config.Pages;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.CheckoutStepOnePage;
import com.saucedemo.pages.CheckoutStepTwoPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CheckoutSteps {

    @Given("I proceed to checkout")
    public void iProceedToCheckout() {
        Pages.get(CartPage.class).checkout();
        Assert.assertTrue(Pages.get(CheckoutStepOnePage.class).isLoaded(),
                "Expected the checkout information page to load");
    }

    @When("I fill in the checkout information with first name {string}, last name {string} and postal code {string}")
    public void iFillInTheCheckoutInformation(String firstName, String lastName, String postalCode) {
        Pages.get(CheckoutStepOnePage.class).fillCustomerInformation(firstName, lastName, postalCode);
    }

    @When("I continue to the order overview")
    public void iContinueToTheOrderOverview() {
        Pages.get(CheckoutStepOnePage.class).continueToOverview();
    }

    @Then("the order overview should list {string}")
    public void theOrderOverviewShouldList(String productName) {
        CheckoutStepTwoPage overviewPage = Pages.get(CheckoutStepTwoPage.class);
        Assert.assertTrue(overviewPage.isLoaded(), "Expected the order overview page to load");
        Assert.assertTrue(overviewPage.getItemNames().contains(productName),
                "Expected order overview to list: " + productName + " but found: " + overviewPage.getItemNames());
    }

    @When("I finish the order")
    public void iFinishTheOrder() {
        Pages.get(CheckoutStepTwoPage.class).finish();
    }

    @Then("I should see the order confirmation {string}")
    public void iShouldSeeTheOrderConfirmation(String expectedMessage) {
        CheckoutCompletePage completePage = Pages.get(CheckoutCompletePage.class);
        Assert.assertTrue(completePage.isLoaded(), "Expected the order confirmation page to load");
        Assert.assertEquals(completePage.getConfirmationMessage(), expectedMessage);
    }

    @Then("I should see the checkout error {string}")
    public void iShouldSeeTheCheckoutError(String expectedMessage) {
        CheckoutStepOnePage checkoutStepOnePage = Pages.get(CheckoutStepOnePage.class);
        Assert.assertTrue(checkoutStepOnePage.isErrorDisplayed(), "Expected a checkout error banner to be displayed");
        Assert.assertEquals(checkoutStepOnePage.getErrorMessage(), expectedMessage);
    }
}
