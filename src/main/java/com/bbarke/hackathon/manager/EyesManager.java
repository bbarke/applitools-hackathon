package com.bbarke.hackathon.manager;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.selenium.Eyes;

import java.util.Objects;

public class EyesManager implements Manager {

    private static Eyes eyes;
    private EyesManager() {
        // Use getter
    }

    public static EyesManager getManager() {
        return new EyesManager();
    }

    public Eyes getEyes(String testName) {

        if (Objects.nonNull(eyes)) {
            openEyes(testName);
            return eyes;
        }

        eyes = new Eyes();
        eyes.setBatch(new BatchInfo("Hackathon"));
        eyes.setForceFullPageScreenshot(true);
        eyes.setApiKey(System.getProperty("applitools.api.key"));
        openEyes(testName);
        return eyes;
    }

    /**
     * Check if the eyes are open, and open them if needed
     * @param testName the name of the test to open eyes for
     */
    private void openEyes(String testName) {
        if (!eyes.getIsOpen()) {
            eyes.open(WebDriverManager.getManager().getDriver(), "Hackathon Project", testName);
        }
    }

    @Override
    public void teardown() {

        if (Objects.isNull(eyes)) {
            return;
        }

        eyes.abortIfNotClosed();
        eyes = null;
    }

    /**
     * Closes eyes, if eyes are available
     */
    public void close() {

        if (Objects.isNull(eyes)) {
            return;
        }

        eyes.close();
    }
}
