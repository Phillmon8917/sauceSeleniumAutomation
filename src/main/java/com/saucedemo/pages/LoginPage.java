package com.saucedemo.pages;

import com.saucedemo.base.BasePage;
import io.github.phillmon.selenium.errors.SafeStep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final By USERNAME_INPUT = By.cssSelector("[data-test='username']");
    private static final By PASSWORD_INPUT = By.cssSelector("[data-test='password']");
    private static final By LOGIN_BUTTON = By.cssSelector("[data-test='login-button']");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");
    private static final By ERROR_DISMISS_BUTTON = By.cssSelector(".error-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        SafeStep.run(driver, "LoginPage.open", () -> {
            modulars.browserActions.navigateToUrl(url, "LoginPage.open");
            modulars.elementActions.scrollIntoView(USERNAME_INPUT, "Username input", "LoginPage.open");
        });
    }

    public void login(String username, String password) {
        SafeStep.run(driver, "LoginPage.login", () -> {
            modulars.elementActions.typeText(USERNAME_INPUT, username, "Username input", "LoginPage.login");
            modulars.elementActions.typeText(PASSWORD_INPUT, password, true, "Password input", "LoginPage.login");
            modulars.elementActions.click(LOGIN_BUTTON, "Login button", "LoginPage.login");
        });
    }

    public boolean isErrorDisplayed() {
        return SafeStep.run(driver, "LoginPage.isErrorDisplayed", () ->
                modulars.elementActions.isDisplayed(ERROR_MESSAGE, "Login error banner", "LoginPage.isErrorDisplayed"));
    }

    public String getErrorMessage() {
        return SafeStep.run(driver, "LoginPage.getErrorMessage", () ->
                modulars.elementActions.getText(ERROR_MESSAGE, "Login error banner", "LoginPage.getErrorMessage"));
    }

    public void dismissError() {
        SafeStep.run(driver, "LoginPage.dismissError", () ->
                modulars.elementActions.click(ERROR_DISMISS_BUTTON, "Login error dismiss button", "LoginPage.dismissError"));
    }
}
