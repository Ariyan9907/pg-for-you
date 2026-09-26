package org.bridgelabz.pgforyou.tests;

import org.bridgelabz.pgforyou.base.BaseTest;
import org.bridgelabz.pgforyou.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LoginTest extends BaseTest {


    @Test
    void verifyLoginPageTitle(){
        driver.get("http://localhost:8080/login");
        assertEquals("Login - PGForYou",driver.getTitle());
    }

    @Test
    void loginFailTest() {
        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:8080/login");
        loginPage.login("test@gmail.com", "12345");
        assertNotEquals("PGForYou | Find Your Perfect Stay", driver.getTitle());
    }
}

