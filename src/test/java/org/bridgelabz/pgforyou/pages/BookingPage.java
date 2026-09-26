package org.bridgelabz.pgforyou.pages;

import org.bridgelabz.pgforyou.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookingPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // Locators
    private By nameInput =
            By.name("name");

    private By phoneInput =
            By.name("phone");

    private By bookNowButton =
            By.cssSelector("button[type='submit']");

    private By backToPGLink =
            By.linkText("Back to PG");

    // Constructor
    public BookingPage(WebDriver driver) {

        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    // Actions
    public void enterName(String name) {

        waitUtils
                .waitForVisibility(nameInput)
                .sendKeys(name);
    }

    public void enterPhone(String phone) {

        waitUtils
                .waitForVisibility(phoneInput)
                .sendKeys(phone);
    }

    public void clickBookNow() {

        waitUtils
                .waitForClickable(bookNowButton)
                .click();
    }

    public void clickBackToPG() {

        waitUtils
                .waitForClickable(backToPGLink)
                .click();
    }

    public void bookRoom(String name, String phone) {

        enterName(name);
        enterPhone(phone);
        clickBookNow();
    }
}