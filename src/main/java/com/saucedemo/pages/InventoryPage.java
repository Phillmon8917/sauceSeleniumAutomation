package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.saucedemo.base.BasePage;

import io.github.phillmon.selenium.errors.SafeStep;

public class InventoryPage extends BasePage {
    private static final By PAGE_TITLE = By.className("title");
    private static final By INVENTORY_LIST = By.cssSelector("[data-test='inventory-list']");
    private static final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");
    private static final By SORT_DROPDOWN = By.cssSelector("[data-test='product-sort-container']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return SafeStep.run(driver, "InventoryPage.isLoaded", () ->
                modulars.elementActions.isDisplayed(INVENTORY_LIST, "Inventory list", "InventoryPage.isLoaded"));
    }

    public String getPageTitle() {
        return SafeStep.run(driver, "InventoryPage.getPageTitle", () ->
                modulars.elementActions.getText(PAGE_TITLE, "Page title", "InventoryPage.getPageTitle"));
    }

    public void addProductToCart(String productName) {
        SafeStep.run(driver, "InventoryPage.addProductToCart", () ->
                modulars.elementActions.click(actionButtonFor(productName), "Add to cart button for " + productName,
                        "InventoryPage.addProductToCart"));
    }

    public void removeProductFromCart(String productName) {
        SafeStep.run(driver, "InventoryPage.removeProductFromCart", () ->
                modulars.elementActions.click(actionButtonFor(productName), "Remove button for " + productName,
                        "InventoryPage.removeProductFromCart"));
    }

    public boolean isProductInCart(String productName) {
        return SafeStep.run(driver, "InventoryPage.isProductInCart", () ->
                "Remove".equalsIgnoreCase(modulars.elementActions.getText(actionButtonFor(productName),
                        "Add/remove button for " + productName, "InventoryPage.isProductInCart")));
    }

    public int getCartBadgeCount() {
        return SafeStep.run(driver, "InventoryPage.getCartBadgeCount", () -> {
            if (!modulars.elementActions.isDisplayed(CART_BADGE, "Cart badge", "InventoryPage.getCartBadgeCount")) {
                return 0;
            }
            return Integer.parseInt(modulars.elementActions.getText(CART_BADGE, "Cart badge", "InventoryPage.getCartBadgeCount"));
        });
    }

    public void openCart() {
        SafeStep.run(driver, "InventoryPage.openCart", () ->
                modulars.elementActions.clickWithJsFallback(CART_LINK, "Cart link", "InventoryPage.openCart"));
    }

    public void sortBy(String visibleOptionText) {
        SafeStep.run(driver, "InventoryPage.sortBy", () -> {
            modulars.dropdownActions.selectByVisibleText(SORT_DROPDOWN, visibleOptionText, "Sort dropdown", "InventoryPage.sortBy");
        });
    }

    /**
     * Locates the product's name element and walks up to its shared
     * inventory_item container, then finds the single button inside it -
     * the same element regardless of whether it currently reads "Add to
     * cart" or "Remove".
     */
    private By actionButtonFor(String productName) {
        return By.xpath(".//div[contains(@class,'inventory_item_name') and text()='" + productName + "']"
                + "/ancestor::div[@data-test='inventory-item'][1]//button");
    }
}
