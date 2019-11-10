package com.bbarke.hackathon.page;

import com.bbarke.hackathon.manager.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class PageObject {
    protected WebDriver driver = WebDriverManager.getManager().getDriver();

    protected String getText(By by) {
        return findElement(by).getText();
    }

    protected String getText(WebElement webElement) {
        return webElement.getText();
    }

    protected void setText(By by, String text) {

        if (Objects.isNull(text)) {
            return;
        }

        findElement(by).sendKeys(text);
    }

    protected void click(By by) {
        findElement(by).click();
    }

    protected WebElement findElement(By by) {
        return driver.findElement(by);
    }

    protected List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }
}
