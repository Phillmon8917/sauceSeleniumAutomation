package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.saucedemo.base.BasePage;

import io.github.phillmon.selenium.errors.SafeStep;

public class CheckoutStepTwoPage extends BasePage {
    private static final By FINISH_BUTTON = By.cssSelector("[data-test='finish']");
    private static final By CANCEL_BUTTON = By.cssSelector("[data-test='cancel']");
    private static final By SUBTOTAL_LABEL = By.cssSelector("[data-test='subtotal-label']");
    private static final By TAX_LABEL = By.cssSelector("[data-test='tax-label']");
    private static final By TOTAL_LABEL = By.cssSelector("[data-test='total-label']");
    private static final By ITEM_NAMES = By.cssSelector("[data-test='inventory-item-name']");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return SafeStep.run(driver, "CheckoutStepTwoPage.isLoaded", () -> waitForUrlToContain("checkout-step-two.html"));
    }

    public java.util.List<String> getItemNames() {
        return SafeStep.run(driver, "CheckoutStepTwoPage.getItemNames", () ->
                modulars.elementActions.getAllText(ITEM_NAMES, "Overview item names", "CheckoutStepTwoPage.getItemNames"));
    }

    public String getSubtotal() {
        return SafeStep.run(driver, "CheckoutStepTwoPage.getSubtotal", () ->
                modulars.elementActions.getText(SUBTOTAL_LABEL, "Subtotal label", "CheckoutStepTwoPage.getSubtotal"));
    }

    public String getTax() {
        return SafeStep.run(driver, "CheckoutStepTwoPage.getTax", () ->
                modulars.elementActions.getText(TAX_LABEL, "Tax label", "CheckoutStepTwoPage.getTax"));
    }

    public String getTotal() {
        return SafeStep.run(driver, "CheckoutStepTwoPage.getTotal", () ->
                modulars.elementActions.getText(TOTAL_LABEL, "Total label", "CheckoutStepTwoPage.getTotal"));
    }

    public void finish() {
        SafeStep.run(driver, "CheckoutStepTwoPage.finish", () ->
                modulars.elementActions.click(FINISH_BUTTON, "Finish button", "CheckoutStepTwoPage.finish"));
    }

    public void cancel() {
        SafeStep.run(driver, "CheckoutStepTwoPage.cancel", () ->
                modulars.elementActions.click(CANCEL_BUTTON, "Cancel button", "CheckoutStepTwoPage.cancel"));
    }
}
