package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.saucedemo.base.BasePage;

import io.github.phillmon.selenium.errors.SafeStep;

public class CheckoutCompletePage extends BasePage {
    private static final By COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");
    private static final By BACK_TO_PRODUCTS_BUTTON = By.cssSelector("[data-test='back-to-products']");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return SafeStep.run(driver, "CheckoutCompletePage.isLoaded", () -> waitForUrlToContain("checkout-complete.html"));
    }

    public String getConfirmationMessage() {
        return SafeStep.run(driver, "CheckoutCompletePage.getConfirmationMessage", () ->
                modulars.elementActions.getText(COMPLETE_HEADER, "Order confirmation header",
                        "CheckoutCompletePage.getConfirmationMessage"));
    }

    public void backToProducts() {
        SafeStep.run(driver, "CheckoutCompletePage.backToProducts", () ->
                modulars.elementActions.click(BACK_TO_PRODUCTS_BUTTON, "Back to products button",
                        "CheckoutCompletePage.backToProducts"));
    }
}
