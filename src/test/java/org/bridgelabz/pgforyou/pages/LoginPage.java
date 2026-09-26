package org.bridgelabz.pgforyou.pages;

import org.bridgelabz.pgforyou.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // Locators
    private By email = By.name("email");

    private By password = By.name("password");

    private By loginButton = By.cssSelector("button[type='submit']");

    private By registerLink = By.linkText("Register");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils=new WaitUtils(driver);
    }

    // Actions
    public void enterEmail(String emailValue) {
        waitUtils.waitForVisibility(email).sendKeys(emailValue);
    }

    public void enterPassword(String passwordValue) {
        waitUtils.waitForVisibility(password).sendKeys(passwordValue);
    }

    public void clickLogin() {
        waitUtils.waitForClickable(loginButton).click();
    }

    public void clickRegister() {
        waitUtils.waitForClickable(registerLink).click();
    }

    // Complete login action
    public void login(String emailValue, String passwordValue) {
        enterEmail(emailValue);
        enterPassword(passwordValue);
        clickLogin();
    }
}