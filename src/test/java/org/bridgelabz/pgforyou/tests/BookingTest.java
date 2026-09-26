package org.bridgelabz.pgforyou.tests;

import org.bridgelabz.pgforyou.base.BaseTest;
import org.bridgelabz.pgforyou.pages.BookingPage;
import org.bridgelabz.pgforyou.pages.BookingSuccessPage;
import org.bridgelabz.pgforyou.pages.HomePage;
import org.bridgelabz.pgforyou.pages.PGDetailsPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingTest extends BaseTest {

    @Test
    void bookingTest() {

        // Login
        login();

        // Search PG
        HomePage homePage = new HomePage(driver);

        homePage.searchPG("Pune");

        // Open PG
        homePage.clickViewPG();

        PGDetailsPage pgDetailsPage = new PGDetailsPage(driver);

        // Click Book Now
        pgDetailsPage.clickBookNow();

        // Fill booking form
        BookingPage bookingPage = new BookingPage(driver);

        bookingPage.bookRoom(
                "Aryan",
                "9876543210"
        );

        // Verify booking success
        BookingSuccessPage successPage = new BookingSuccessPage(driver);

        assertTrue(
                successPage.isBookingSuccessful(),
                "Booking should be successful"
        );
    }
}