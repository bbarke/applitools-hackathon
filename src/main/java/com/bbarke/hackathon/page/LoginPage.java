package com.bbarke.hackathon.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class LoginPage extends PageObject {

    private final By usernameSelector = By.id("username");
    private final By passwordSelector = By.id("password");

    private LoginPage() {
        // Use getter
    }

    public static LoginPage getPage() {
        return new LoginPage();
    }

    public LoginPage navToPage() {
        driver.get(System.getProperty("site.url"));
        return this;
    }

    public String getPageTitle() {
        return getText(By.cssSelector(".auth-header"));
    }

    public String getUsernamePlaceholder() {
        return findElement(usernameSelector).getAttribute("placeholder");
    }

    public String getPasswordPlaceholder() {
        return findElement(passwordSelector).getAttribute("placeholder");
    }

    public String getUsernameLabel() {
        return getText(By.cssSelector(".form-group:nth-child(1) label"));
    }

    public String getPasswordLabel() {
        return getText(By.cssSelector(".form-group:nth-child(2) label"));
    }

    public String getRememberMeText() {
        return getText(By.cssSelector(".form-check-label"));
    }

    public List<String> getSocialIconNames() {
        List<WebElement> socialImages = driver.findElements(By.cssSelector(".buttons-w img"));
        return socialImages.stream()
                .map(img -> img.getAttribute("src").replaceAll(".*\\/(\\w+)\\.png", "$1"))
                .collect(Collectors.toList());
    }

    public LoginPage setUsername(String username) {
        setText(usernameSelector, username);
        return this;
    }

    public LoginPage setPassword(String password) {
        setText(passwordSelector, password);
        return this;
    }

    public void clickLogin() {
        click(By.id("log-in"));
    }

    public String getAlertText() {
        return getText(By.cssSelector("div.alert[style='display: block;']"));
    }

    /**
     * Logs into the site
     */
    public void login() {
        setUsername("test");
        setPassword("test");
        clickLogin();
    }

    public LoginPage enableAds() {
        driver.get(driver.getCurrentUrl() + "?showAd=true");
        return this;
    }
}
