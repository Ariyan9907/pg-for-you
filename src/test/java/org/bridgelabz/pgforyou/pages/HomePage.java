package org.bridgelabz.pgforyou.pages;

import org.bridgelabz.pgforyou.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // Locators
    private By locationInput = By.name("location");

    private By searchButton = By.cssSelector("button[type='submit']");

    private By viewPGButton = By.linkText("View PG");

    // Constructor
    public HomePage(WebDriver driver) {

        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    // Actions
    public void enterLocation(String location) {

        waitUtils
                .waitForVisibility(locationInput)
                .sendKeys(location);
    }

    public void clickSearch() {

        waitUtils
                .waitForClickable(searchButton)
                .click();
    }

    public void searchPG(String location) {

        enterLocation(location);
        clickSearch();
    }

    public void clickViewPG() {

        waitUtils
                .waitForClickable(viewPGButton)
                .click();
    }
}