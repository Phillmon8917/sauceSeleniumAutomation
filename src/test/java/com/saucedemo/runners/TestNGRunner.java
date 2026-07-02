package com.saucedemo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.saucedemo.stepdefinitions", "com.saucedemo.hooks"},
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        publish = false
)
public class TestNGRunner extends AbstractTestNGCucumberTests {
}
