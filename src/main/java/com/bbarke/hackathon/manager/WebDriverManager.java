package com.bbarke.hackathon.manager;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Objects;

/**
 * Manages the lifecycle of the {@link WebDriver}
 */
public class WebDriverManager implements Manager {
    private static WebDriver driver;

    private WebDriverManager() {
        // Use getter
    }

    /**
     * Gets the web driver manager
     * @return the web driver manager
     */
    public static WebDriverManager getManager() {
        return new WebDriverManager();
    }

    /**
     * Obtains the current WebDriver. It will create a web driver if it does not exist, or return an existing one
     * @return the {@link WebDriver}
     */
    public WebDriver getDriver() {
        if (Objects.isNull(driver)) {
            driver = new ChromeDriver();
            driver.manage().window().setSize(new Dimension(1600, 900));
            driver.get(System.getProperty("site.url"));
        }
        return driver;
    }

    /**
     * Stops the current webdriver
     */
    @Override
    public void teardown() {
        driver.quit();
        driver = null;
    }
}
