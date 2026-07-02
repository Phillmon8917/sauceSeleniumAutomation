package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.saucedemo.base.BasePage;

import io.github.phillmon.selenium.errors.SafeStep;

public class CheckoutStepOnePage extends BasePage {
    private static final By FIRST_NAME_INPUT = By.cssSelector("[data-test='firstName']");
    private static final By LAST_NAME_INPUT = By.cssSelector("[data-test='lastName']");
    private static final By POSTAL_CODE_INPUT = By.cssSelector("[data-test='postalCode']");
    private static final By CONTINUE_BUTTON = By.cssSelector("[data-test='continue']");
    private static final By CANCEL_BUTTON = By.cssSelector("[data-test='cancel']");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return SafeStep.run(driver, "CheckoutStepOnePage.isLoaded", () -> waitForUrlToContain("checkout-step-one.html"));
    }

    public void fillCustomerInformation(String firstName, String lastName, String postalCode) {
        SafeStep.run(driver, "CheckoutStepOnePage.fillCustomerInformation", () -> {
            if (firstName != null && !firstName.isEmpty()) {
                modulars.elementActions.typeText(FIRST_NAME_INPUT, firstName, "First name input",
                        "CheckoutStepOnePage.fillCustomerInformation");
            }
            if (lastName != null && !lastName.isEmpty()) {
                modulars.elementActions.typeText(LAST_NAME_INPUT, lastName, "Last name input",
                        "CheckoutStepOnePage.fillCustomerInformation");
            }
            if (postalCode != null && !postalCode.isEmpty()) {
                modulars.elementActions.typeText(POSTAL_CODE_INPUT, postalCode, "Postal code input",
                        "CheckoutStepOnePage.fillCustomerInformation");
            }
        });
    }

    public void continueToOverview() {
        SafeStep.run(driver, "CheckoutStepOnePage.continueToOverview", () ->
                modulars.elementActions.click(CONTINUE_BUTTON, "Continue button", "CheckoutStepOnePage.continueToOverview"));
    }

    public void cancel() {
        SafeStep.run(driver, "CheckoutStepOnePage.cancel", () ->
                modulars.elementActions.click(CANCEL_BUTTON, "Cancel button", "CheckoutStepOnePage.cancel"));
    }

    public boolean isErrorDisplayed() {
        return SafeStep.run(driver, "CheckoutStepOnePage.isErrorDisplayed", () ->
                modulars.elementActions.isDisplayed(ERROR_MESSAGE, "Checkout error banner", "CheckoutStepOnePage.isErrorDisplayed"));
    }

    public String getErrorMessage() {
        return SafeStep.run(driver, "CheckoutStepOnePage.getErrorMessage", () ->
                modulars.elementActions.getText(ERROR_MESSAGE, "Checkout error banner", "CheckoutStepOnePage.getErrorMessage"));
    }
}
