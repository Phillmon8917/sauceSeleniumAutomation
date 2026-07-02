package com.saucedemo.stepdefinitions;

import com.saucedemo.config.Pages;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    @When("I log in as {string} with password {string}")
    public void iLogIn(String username, String password) {
        Pages.get(LoginPage.class).login(username, password);
    }

    @Then("I should be redirected to the products page")
    public void iShouldBeRedirectedToTheProductsPage() {
        InventoryPage inventoryPage = Pages.get(InventoryPage.class);
        Assert.assertTrue(inventoryPage.isLoaded(), "Expected the products page to be loaded");
        Assert.assertEquals(inventoryPage.getPageTitle(), "Products");
    }

    @Then("I should see the login error {string}")
    public void iShouldSeeTheLoginError(String expectedMessage) {
        LoginPage loginPage = Pages.get(LoginPage.class);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected a login error banner to be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessage);
    }
}
