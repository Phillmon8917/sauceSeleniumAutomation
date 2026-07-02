package com.saucedemo.hooks;

import com.saucedemo.config.DriverFactory;
import com.saucedemo.config.Pages;
import com.saucedemo.extensions.video.VideoRecorderActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Drives the lifecycle around every scenario: starts the browser and screen
 * recording before, then afterwards always attaches a screenshot and this
 * scenario's slice of the execution log to Allure, and either discards the
 * recording (passed) or keeps and attaches it (failed).
 */
public class Hooks {
    private static final Path EXECUTION_LOG = Paths.get("src", "logs", "execution.log");
    private static final Path VIDEO_TEMP_DIR = Paths.get("target", "videos", "tmp");
    private static final Path FAILED_VIDEO_DIR = Paths.get("target", "videos", "failed");

    private long logOffsetBeforeScenario;

    @Before
    public void beforeScenario(Scenario scenario) {
        DriverFactory.initDriver();
        logOffsetBeforeScenario = sizeOfLogFile();
        VideoRecorderActions.startRecording(VIDEO_TEMP_DIR, scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        attachScreenshot(scenario);
        attachExecutionLogSlice(scenario);
        handleVideo(scenario);
        DriverFactory.quitDriver();
        Pages.clear();
    }

    private void attachScreenshot(Scenario scenario) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot - " + scenario.getName(), "image/png",
                    new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            Allure.addAttachment("Screenshot capture failed", e.toString());
        }
    }

    private void attachExecutionLogSlice(Scenario scenario) {
        try {
            if (!Files.exists(EXECUTION_LOG)) {
                return;
            }
            byte[] allBytes = Files.readAllBytes(EXECUTION_LOG);
            int from = (int) Math.min(logOffsetBeforeScenario, allBytes.length);
            byte[] slice = java.util.Arrays.copyOfRange(allBytes, from, allBytes.length);
            InputStream logStream = new ByteArrayInputStream(slice);
            Allure.addAttachment("Execution Log - " + scenario.getName(), "text/plain", logStream, "log");
        } catch (IOException e) {
            Allure.addAttachment("Execution log capture failed", e.toString());
        }
    }

    private void handleVideo(Scenario scenario) {
        VideoRecorderActions.stopRecording(scenario.getName()).ifPresent(video -> {
            try {
                if (scenario.isFailed()) {
                    Files.createDirectories(FAILED_VIDEO_DIR);
                    Path destination = FAILED_VIDEO_DIR.resolve(sanitize(scenario.getName()) + "-" + System.currentTimeMillis() + ".avi");
                    Files.move(video.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                    Allure.addAttachment("Failure Video - " + scenario.getName(), "video/avi",
                            Files.newInputStream(destination), "avi");
                } else {
                    Files.deleteIfExists(video.toPath());
                }
            } catch (IOException e) {
                Allure.addAttachment("Video handling failed", e.toString());
            }
        });
    }

    private long sizeOfLogFile() {
        try {
            return Files.exists(EXECUTION_LOG) ? Files.size(EXECUTION_LOG) : 0L;
        } catch (IOException e) {
            return 0L;
        }
    }

    private String sanitize(String scenarioName) {
        return scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}
