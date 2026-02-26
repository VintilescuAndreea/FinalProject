package tests;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import sharedData.SharedData;

public class TestInvalidRegistration extends SharedData {

    @Test
    public void testInvalidRegistration() {

        HomePage homePage = new HomePage(getDriver());
        homePage.navigateToHomePage();
        homePage.clickSignInButton();

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.clickRegisterLink();

        RegisterPage registerPage = new RegisterPage(getDriver());

        registerPage.registerUser("Andreea", "Test", "23/02/9999",
                "Iuliu Maniu", "061126", "Bucharest", "Sector 6",
                "Romania", "0765354782", "emailGresit2gmail.com", "123");


        WebElement[] fieldsToCheck = {
                registerPage.getFirstNameInput(),
                registerPage.getLastNameInput(),
                registerPage.getDobInput(),
                registerPage.getStreetInput(),
                registerPage.getPostalCodeInput(),
                registerPage.getCityInput(),
                registerPage.getStateInput(),
                registerPage.getCountrySelect(),
                registerPage.getPhoneInput(),
                registerPage.getEmailInput(),
                registerPage.getPasswordInput()
        };

        for(WebElement field : fieldsToCheck) {
            if (registerPage.isFieldInvalid(field)) {
                System.out.println("EROARE! Campul " + field.getAttribute("id") + " este invalid.");
            }
        }
    }
}