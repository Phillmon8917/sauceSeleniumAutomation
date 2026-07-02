package com.saucedemo.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class BasePage extends io.github.phillmon.selenium.base.BasePage {
    protected BasePage(WebDriver driver) {
        super(driver);
    }

    protected boolean waitForUrlToContain(String fragment) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.urlContains(fragment));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}
