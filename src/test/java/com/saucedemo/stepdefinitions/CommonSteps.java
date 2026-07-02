package com.saucedemo.stepdefinitions;

import com.saucedemo.config.Pages;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.github.phillmon.selenium.utils.env.EnvLoader;
import org.testng.Assert;

/**
 * Steps shared by every feature: getting to the login page and logging in
 * successfully as a precondition for a scenario that is not itself testing
 * the login flow.
 */
public class CommonSteps {

    @Given("I am on the Swag Labs login page")
    public void iAmOnTheLoginPage() {
        Pages.get(LoginPage.class).open(EnvLoader.getUrl());
    }

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedIn(String username, String password) {
        LoginPage loginPage = Pages.get(LoginPage.class);
        loginPage.open(EnvLoader.getUrl());
        loginPage.login(username, password);

        InventoryPage inventoryPage = Pages.get(InventoryPage.class);
        Assert.assertTrue(inventoryPage.isLoaded(), "Expected the inventory page to load after logging in as " + username);
    }
}
