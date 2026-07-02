package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.saucedemo.base.BasePage;

import io.github.phillmon.selenium.errors.SafeStep;

public class CartPage extends BasePage {
    private static final By CART_ITEM = By.cssSelector("[data-test='inventory-item']");
    private static final By CART_ITEM_NAMES = By.cssSelector("[data-test='inventory-item-name']");
    private static final By CHECKOUT_BUTTON = By.cssSelector("[data-test='checkout']");
    private static final By CONTINUE_SHOPPING_BUTTON = By.cssSelector("[data-test='continue-shopping']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return SafeStep.run(driver, "CartPage.isLoaded", () -> waitForUrlToContain("cart.html"));
    }

    public int getItemCount() {
        return SafeStep.run(driver, "CartPage.getItemCount", () ->
                modulars.elementActions.countElements(CART_ITEM, "Cart items", "CartPage.getItemCount"));
    }

    public java.util.List<String> getItemNames() {
        return SafeStep.run(driver, "CartPage.getItemNames", () ->
                modulars.elementActions.getAllText(CART_ITEM_NAMES, "Cart item names", "CartPage.getItemNames"));
    }

    public void removeProduct(String productName) {
        SafeStep.run(driver, "CartPage.removeProduct", () ->
                modulars.elementActions.click(removeButtonFor(productName), "Remove button for " + productName,
                        "CartPage.removeProduct"));
    }

    public void continueShopping() {
        SafeStep.run(driver, "CartPage.continueShopping", () ->
                modulars.elementActions.click(CONTINUE_SHOPPING_BUTTON, "Continue shopping button", "CartPage.continueShopping"));
    }

    public void checkout() {
        SafeStep.run(driver, "CartPage.checkout", () ->
                modulars.elementActions.clickWithJsFallback(CHECKOUT_BUTTON, "Checkout button", "CartPage.checkout"));
    }

    private By removeButtonFor(String productName) {
        return By.xpath(".//div[contains(@class,'inventory_item_name') and text()='" + productName + "']"
                + "/ancestor::div[@data-test='inventory-item'][1]//button");
    }
}
