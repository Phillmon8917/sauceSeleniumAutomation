package com.saucedemo.config;

import io.github.phillmon.selenium.utils.env.EnvLoader;
import io.github.phillmon.selenium.utils.logging.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public final class DriverFactory {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialised for this thread. Call initDriver() first.");
        }
        return driver;
    }

    public static void initDriver() {
        String browser = readOrDefault("BROWSER", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(readOrDefault("HEADLESS", "false"));

        WebDriver driver = switch (browser) {
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                yield new FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                yield new EdgeDriver(options);
            }
            default -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                // standard_user/secret_sauce are public demo credentials, which trips Chrome's
                // leaked-password dialog. That dialog steals focus and blocks every step behind
                // it until dismissed, so it must be disabled rather than handled reactively.
                options.addArguments("--disable-features=PasswordLeakDetection,AutofillServerCommunication");
                java.util.Map<String, Object> prefs = new java.util.HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);
                options.setExperimentalOption("prefs", prefs);
                options.addArguments("--disable-save-password-bubble");
                if (headless) {
                    options.addArguments("--headless=new", "--disable-gpu");
                }
                yield new ChromeDriver(options);
            }
        };

        driver.manage().window().maximize();
        DRIVER.set(driver);
        LoggerUtil.info("DriverFactory initialised a " + browser + " driver (headless=" + headless + ")");
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
            LoggerUtil.info("DriverFactory quit the driver for this thread");
        }
    }

    private static String readOrDefault(String key, String defaultValue) {
        try {
            return EnvLoader.get(key);
        } catch (IllegalStateException e) {
            return defaultValue;
        }
    }
}
