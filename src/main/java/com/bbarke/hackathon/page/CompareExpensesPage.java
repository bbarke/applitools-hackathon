package com.bbarke.hackathon.page;

import org.openqa.selenium.By;

public class CompareExpensesPage extends PageObject {

    private CompareExpensesPage() {
        // Use getter
    }

    public static CompareExpensesPage getPage() {
        return new CompareExpensesPage();
    }

    public void clickShowDataForNextYear() {
        click(By.id("addDataset"));
    }
}
