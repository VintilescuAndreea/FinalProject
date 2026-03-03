package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import sharedData.SharedData;
import utils.LogUtility;

import java.time.Duration;

public class TestSuccessfulLogin extends SharedData {

    @Test
    public void testSuccessfulLogin() {

        LogUtility logUtility = new LogUtility();
        HomePage homePage = new HomePage(getDriver());
        LoginPage loginPage = new LoginPage(getDriver());
        RegisterPage registerPage = new RegisterPage(getDriver());

        homePage.navigateToHomePage();
        logUtility.infoLog("Deschide home page-ul");
        homePage.clickSignInButton();
        logUtility.infoLog("Apasa pe butonul 'Sign In' de pe home page");
        loginPage.clickRegisterLink();
        logUtility.infoLog("Apasa pe link-ul 'Register' de pe pagina de login");


        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first_name")));

        registerPage.registerUser("Andreea", "Vintilescu", "23/02/1999",
                "Iuliu Maniu", "061126", "Bucharest", "Sector 6",
                "Romania", "0765354782", "vinti23@gmail.com", "Parola123!");

        logUtility.infoLog("Completeaza formularul de inregistrare cu date valide si apasa pe butonul 'Register'");

        loginPage.login("vinti23@gmail.com", "Parola123!");
        logUtility.infoLog("Completeaza formularul de login cu email-ul si parola inregistrate anterior si apasa pe butonul 'Login'");
    }
}
