package org.bridgelabz.pgforyou.base;

import org.bridgelabz.pgforyou.pages.LoginPage;
import org.bridgelabz.pgforyou.utils.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void setUp() {

        driver = DriverFactory.createDriver();
        driver.get("http://localhost:8080");
    }

    protected void login() {

        driver.get("http://localhost:8080/login");

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                "test@gmail.com",
                "12345"
        );
    }

    @AfterEach
    void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}