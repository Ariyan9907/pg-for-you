package org.bridgelabz.pgforyou.pages;

import org.bridgelabz.pgforyou.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookingSuccessPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    private By successMessage =
            By.xpath("//*[contains(text(),'Booking Successful')]");

    public BookingSuccessPage(WebDriver driver) {

        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public boolean isBookingSuccessful() {

        return waitUtils
                .waitForVisibility(successMessage)
                .isDisplayed();
    }
}