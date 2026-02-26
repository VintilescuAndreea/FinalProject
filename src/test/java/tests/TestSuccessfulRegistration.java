package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import sharedData.SharedData;
import utils.LogUtility;

public class TestSuccessfulRegistration extends SharedData {

    @Test
    public void testSuccessfulRegistration() {

        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        RegisterPage registerPage = new RegisterPage(getDriver());
        LogUtility logUtility = new LogUtility();

        homePage.navigateToHomePage();
        logUtility.infoLog("Navigated to home page");
        homePage.clickSignInButton();
        logUtility.infoLog("Clicked on Sign In button");
        loginPage.clickRegisterLink();
        logUtility.infoLog("Clicked on Register link");

        registerPage.registerUser("Andreea", "Test", "23/02/1999",
                "Iuliu Maniu", "061126", "Bucharest", "Sector 6",
                "Romania", "0765354782", "vinti23@gmail.com", "Test123!");

    }
}
