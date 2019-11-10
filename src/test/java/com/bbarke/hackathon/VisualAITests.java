package com.bbarke.hackathon;

import com.applitools.eyes.exceptions.DiffsFoundException;
import com.bbarke.hackathon.iterations.LoginScenario;
import com.bbarke.hackathon.page.CompareExpensesPage;
import com.bbarke.hackathon.page.LandingPage;
import com.bbarke.hackathon.page.LoginPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class VisualAITests extends BaseTest {
    private LoginPage loginPage;
    private LandingPage landingPage;
    private CompareExpensesPage compareExpensesPage;

    @Before
    public void setup() {
        loginPage = LoginPage.getPage();
        landingPage = LandingPage.getPage();
        compareExpensesPage = CompareExpensesPage.getPage();
    }

    @Test
    public void testLoginPageUIElements() {
        LoginPage.getPage();
        validateWindow("Login Page Elements");
    }

    @Test
    public void testLoginDataDriven() {

        // This adjustment from the other traditional tests is done to take advantage of "Test Steps"
        List<LoginScenario> loginScenarios = Arrays.asList(
            new LoginScenario( "No username or password",    null,       null,       false, "Both Username and Password must be present" ),
            new LoginScenario( "Only username",              "username", null,       false, "Password must be present" ),
            new LoginScenario( "Only password",              null,       "password", false, "Username must be present" ),
            new LoginScenario( "Both username and password", "username", "password", true,  null )
        );

        boolean success = true;

        for (LoginScenario scenario : loginScenarios) {
            loginPage.navToPage()
                    .setUsername(scenario.getUsername())
                    .setPassword(scenario.getPassword())
                    .clickLogin();

            // Surround in try/catch so we can get screenshots of all of the failures, if any
            try {
                validateWindow(scenario.getDescription());
            } catch (DiffsFoundException e) {
                e.printStackTrace();
                success = false;
            }
        }

        Assert.assertTrue(success);
    }

    @Test
    public void testTableSort() {

        loginPage.login();
        validateWindow("Before sorting");

        landingPage.clickAmountTableHeader();
        validateWindow("After sorting");
    }

    @Test
    public void testCanvasChart() {
        loginPage.login();
        landingPage.clickCompareExpenses();
        validateWindow("Default data set");

        compareExpensesPage.clickShowDataForNextYear();
        validateWindow("Adding 2019");

    }

    @Test
    public void testCheckForAds() {
        loginPage.enableAds().login();
        validateWindow("Checking for ads");
    }
}
