package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import io.github.phillmon.selenium.errors.SafeStep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationMenu extends BasePage {
    private static final By MENU_BUTTON = By.id("react-burger-menu-btn");
    private static final By LOGOUT_LINK = By.cssSelector("[data-test='logout-sidebar-link']");
    private static final By RESET_APP_STATE_LINK = By.cssSelector("[data-test='reset-sidebar-link']");
    private static final By ALL_ITEMS_LINK = By.cssSelector("[data-test='inventory-sidebar-link']");

    public NavigationMenu(WebDriver driver) {
        super(driver);
    }

    public void open() {
        SafeStep.run(driver, "NavigationMenu.open", () ->
                modulars.elementActions.click(MENU_BUTTON, "Burger menu button", "NavigationMenu.open"));
    }

    public void logout() {
        SafeStep.run(driver, "NavigationMenu.logout", () -> {
            open();
            modulars.elementActions.click(LOGOUT_LINK, "Logout link", "NavigationMenu.logout");
        });
    }

    public void resetAppState() {
        SafeStep.run(driver, "NavigationMenu.resetAppState", () -> {
            open();
            modulars.elementActions.click(RESET_APP_STATE_LINK, "Reset App State link", "NavigationMenu.resetAppState");
        });
    }

    public void goToAllItems() {
        SafeStep.run(driver, "NavigationMenu.goToAllItems", () -> {
            open();
            modulars.elementActions.click(ALL_ITEMS_LINK, "All Items link", "NavigationMenu.goToAllItems");
        });
    }
}
