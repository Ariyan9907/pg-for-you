package org.bridgelabz.pgforyou.tests;

import org.bridgelabz.pgforyou.base.BaseTest;
import org.bridgelabz.pgforyou.pages.HomePage;
import org.bridgelabz.pgforyou.pages.PGDetailsPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PGSearchTest extends BaseTest {

    @Test
    void searchAndViewPGTest() {

        // 1. Login
        login();

        // 2. Create HomePage
        HomePage homePage =
                new HomePage(driver);

        // 3. Search PG
        homePage.searchPG("Pune");

        // 4. Click View PG
        homePage.clickViewPG();

        // 5. Create PGDetailsPage
        PGDetailsPage pgDetailsPage =
                new PGDetailsPage(driver);

        // 6. Verify PG details page
        assertTrue(
                pgDetailsPage.isBookNowDisplayed(),
                "Book Now button should be displayed"
        );
    }
}