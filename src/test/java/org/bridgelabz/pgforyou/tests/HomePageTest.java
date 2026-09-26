package org.bridgelabz.pgforyou.tests;

import org.bridgelabz.pgforyou.base.BaseTest;
import org.bridgelabz.pgforyou.pages.HomePage;
import org.bridgelabz.pgforyou.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePageTest extends BaseTest {

    @Test
    void searchPGTest() {

        // Go to log in
        driver.get("http://localhost:8080/login");

        // Login
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
                "test@gmail.com",
                "12345"
        );

        // Home page
        HomePage homePage = new HomePage(driver);

        // Search
        homePage.searchPG("Pune");

        // Verify
        assertEquals(
                "PGForYou | Find Your Perfect Stay",
                driver.getTitle()
        );
    }
}