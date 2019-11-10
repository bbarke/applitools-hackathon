package com.bbarke.hackathon;

import com.applitools.eyes.selenium.Eyes;
import com.bbarke.hackathon.manager.EyesManager;
import com.bbarke.hackathon.manager.WebDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {

    @BeforeClass
    public static void beforeClass() throws IOException {
        loadProperties();
    }

    @After
    public void teardown() {
        WebDriverManager.getManager().teardown();
        EyesManager.getManager().close();
    }

    @AfterClass
    public static void teardownTests() {
        EyesManager.getManager().teardown();
    }

    private static void loadProperties() throws IOException {
        Properties properties = System.getProperties();
        try (InputStream in = ClassLoader.class.getResourceAsStream("/test.properties")) {
            properties.load(in);
        }
    }

    protected void validateWindow(String tag) {

        String testName = Thread.currentThread().getStackTrace()[2].getMethodName();

        Eyes eyes = EyesManager.getManager().getEyes(testName);
        eyes.checkWindow(tag);
    }
}
