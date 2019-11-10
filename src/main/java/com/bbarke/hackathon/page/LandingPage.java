package com.bbarke.hackathon.page;

import com.bbarke.hackathon.model.Transaction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LandingPage extends PageObject {

    private LandingPage() {
        // Use getter
    }

    public static LandingPage getPage() {
        return new LandingPage();
    }

    public boolean onLandingPage() {
        return super.findElements(By.cssSelector(".layout-w")).size() == 1;
    }

    public void clickAmountTableHeader() {
        click(By.id("amount"));
    }

    /**
     * Returns a list of transactions that are in the table
     * @return
     */
    public List<Transaction> getTransactions() {
        final int COL_STATUS = 0;
        final int COL_DATE = 1;
        final int COL_DESCRIPTION = 2;
        final int COL_CATEGORY = 3;
        final int COL_AMOUNT = 4;

        return findElements(By.cssSelector("#transactionsTable tbody tr"))
                .stream()
                .map(row -> {
                    List<WebElement> columns = row.findElements(By.cssSelector("td"));
                    String status = getText(columns.get(COL_STATUS));
                    String date = getText(columns.get(COL_DATE));
                    String description = getText(columns.get(COL_DESCRIPTION));
                    String category = getText(columns.get(COL_CATEGORY));
                    String amount = getText(columns.get(COL_AMOUNT));

                    String formattedAmount = amount
                            .replaceAll("([\\-,+])?\\s([\\d,,]+\\.\\d{2}).*", "$1$2")
                            .replaceAll(",", "");
                    BigDecimal parsedAmount = new BigDecimal(formattedAmount);
                    return new Transaction(status, date, description, category, parsedAmount);
                }).collect(Collectors.toList());
    }

    public void clickCompareExpenses() {
        click(By.id("showExpensesChart"));
    }

    /**
     * Gets the ads that are currently displayed on the page
     * @return a list of the src tags for the ads displayed on the page
     */
    public List<String> getAds() {
        List<String> ads = new ArrayList<>();

        ads.add(getAdSrcIfFound(By.cssSelector("#flashSale img")));
        ads.add(getAdSrcIfFound(By.cssSelector("#flashSale2 img")));
        ads.removeIf(Objects::isNull);

        return ads;
    }

    /**
     * Gets the ad image source if the ad is both found and currently displayed
     * @param by the selector where the ad should be
     * @return the image source, or null if it is absent
     */
    private String getAdSrcIfFound(By by) {
        List<WebElement> adOne = findElements(by);

        if (adOne.size() == 1 && adOne.get(0).isDisplayed()) {
            return adOne.get(0).getAttribute("src");
        }
        return null;
    }
}
