package org.bridgelabz.pgforyou.pages;

import org.bridgelabz.pgforyou.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PGDetailsPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // Locators
    private By pgName =
            By.cssSelector(".pg-details h1");

    private By bookNowButton =
            By.linkText("Book Now");

    private By writeReviewLink =
            By.linkText("Write a Review");

    private By backToPGs =
            By.linkText("Back to PGs");

    // Constructor
    public PGDetailsPage(WebDriver driver) {

        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    // Actions
    public String getPGName() {

        return waitUtils
                .waitForVisibility(pgName)
                .getText();
    }

    public void clickBookNow() {

        waitUtils
                .waitForClickable(bookNowButton)
                .click();
    }

    public void clickWriteReview() {

        waitUtils
                .waitForClickable(writeReviewLink)
                .click();
    }

    public void clickBackToPGs() {

        waitUtils
                .waitForClickable(backToPGs)
                .click();
    }


    public boolean isBookNowDisplayed() {

        return waitUtils
                .waitForVisibility(bookNowButton)
                .isDisplayed();
    }
}