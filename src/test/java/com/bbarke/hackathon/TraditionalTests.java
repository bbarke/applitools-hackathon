package com.bbarke.hackathon;

import com.bbarke.hackathon.model.Transaction;
import com.bbarke.hackathon.page.LandingPage;
import com.bbarke.hackathon.page.LoginPage;
import com.google.common.collect.Comparators;
import com.google.common.collect.Ordering;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(DataProviderRunner.class)
public class TraditionalTests extends BaseTest {

    private LoginPage loginPage;
    private LandingPage landingPage;

    @Before
    public void setup() {
        loginPage = LoginPage.getPage();
        landingPage = LandingPage.getPage();
    }

    @Test
    public void testLoginPageUIElements() {

        // Check the login page title
        String pageTitle = loginPage.getPageTitle();
        Assert.assertEquals("The login title is not correct!","Login Form", pageTitle);

        // Check the username label
        String usernameLabel = loginPage.getUsernameLabel();
        Assert.assertEquals("The username label is incorrect!", "Username", usernameLabel);

        // Check the username placeholder
        String usernamePlaceholder = loginPage.getUsernamePlaceholder();
        Assert.assertEquals("The username placeholder is incorrect!", "Enter your username", usernamePlaceholder);

        // Check the Password label
        String passwordLabel = loginPage.getPasswordLabel();
        Assert.assertEquals("The password label is incorrect!", "Password", passwordLabel);

        // Check the password placeholder
        String passwordPlaceholder = loginPage.getPasswordPlaceholder();
        Assert.assertEquals("The password placeholder is incorrect!", "Enter your password", passwordPlaceholder);

        // Check the 'Remember Me' label for the checkbox
        // This would have been missed in v2
        String rememberMe = loginPage.getRememberMeText();
        Assert.assertEquals("The Remember Me text is incorrect!", "Remember Me", rememberMe);

        // Check social icons at the bottom of the page
        List<String> expectedSocialIcons = Arrays.asList("twitter", "facebook", "linkedin");
        List<String> actualSocialIconNames = loginPage.getSocialIconNames();

        Assert.assertEquals("The number of social icons on the page is incorrect!", expectedSocialIcons.size(), actualSocialIconNames.size());
        Assert.assertTrue(expectedSocialIcons.containsAll(actualSocialIconNames));
    }


    /*
    If you don’t enter the username and password and click the login button, it should throw an error
    If you only enter the username and click the login button, it should throw an error
    If you only enter the password and click the login button, it should throw an error
    If you enter both username (any value) and password (any value), it should log you in.
     */
    @DataProvider
    public static Object[][] loginScenarios() {
        return new Object[][] {
                { "No username or password",    null,       null,       false, "Both Username and Password must be present" },
                { "Only username",              "username", null,       false, "Password must be present" },
                { "Only password",              null,       "password", false, "Username must be present" },
                { "Both username and password", "username", "password", true,  null },
        };
    }

    @Test
    @UseDataProvider("loginScenarios")
    public void testLoginDataDriven(String description, String username, String password, boolean expectedToBeLoggedIn, String alert) {
        loginPage.setUsername(username)
                .setPassword(password)
                .clickLogin();

        if (!expectedToBeLoggedIn) {
            Assert.assertEquals(description + " had the wrong alert!", alert, loginPage.getAlertText());
        } else {
            Assert.assertTrue(description + " didn't log in!", landingPage.onLandingPage());
        }
    }

    @Test
    public void testTableSort() {

        loginPage.login();
        List<Transaction> transactionsBeforeSort = landingPage.getTransactions();

        landingPage.clickAmountTableHeader();
        List<Transaction> afterSortTransactions = landingPage.getTransactions();

        Assert.assertEquals(transactionsBeforeSort.size(), afterSortTransactions.size());

        // Make sure the data is all still in tact
        transactionsBeforeSort.forEach(before -> {
            Transaction after = afterSortTransactions.stream()
                    .filter(aft -> aft.getDescription().equals(before.getDescription()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Cant find transaction with description of " + before.getDescription()));
            Assert.assertEquals(before, after);
        });

        // Make sure it is sorted in desc order
        List<BigDecimal> amounts = afterSortTransactions.stream()
                .map(aft -> aft.getAmount())
                .collect(Collectors.toList());
        Assert.assertTrue("The sorted amount is not in the correct order!",
                Ordering.<BigDecimal> natural().isOrdered(amounts));
    }

    @Test
    @Ignore
    public void testCanvasChart() {
        loginPage.login();
        landingPage.clickCompareExpenses();
        // I can't verify the canvas on this bar chart, Selenium can't select anything within that canvas
    }

    @Test
    public void testCheckForAds() {
        loginPage.enableAds().login();
        List<String> ads = landingPage.getAds();
        Assert.assertEquals("The wrong amont of ads are on the page!", 2, ads.size());
    }
}
