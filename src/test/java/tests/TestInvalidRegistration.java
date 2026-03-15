package tests;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.TestUtility;

public class TestInvalidRegistration extends SharedData {

    @Test
    public void testInvalidRegistration() {
        TestUtility testUtility = new TestUtility(getDriver());

        testUtility.navigateToRegistrationPage();

        testUtility.getRegisterPage().registerUser("Andreea", "Test", "23/02/9999",
                "Iuliu Maniu", "061126", "Bucharest", "Sector 6",
                "Romania", "0765354782", "emailGresit2gmail.com", "123");

        WebElement[] fieldsToCheck = {
                testUtility.getRegisterPage().getFirstNameInput(),
                testUtility.getRegisterPage().getLastNameInput(),
                testUtility.getRegisterPage().getDobInput(),
                testUtility.getRegisterPage().getStreetInput(),
                testUtility.getRegisterPage().getPostalCodeInput(),
                testUtility.getRegisterPage().getCityInput(),
                testUtility.getRegisterPage().getStateInput(),
                testUtility.getRegisterPage().getCountrySelect(),
                testUtility.getRegisterPage().getPhoneInput(),
                testUtility.getRegisterPage().getEmailInput(),
                testUtility.getRegisterPage().getPasswordInput()
        };

        for(WebElement field : fieldsToCheck) {
            if (testUtility.getRegisterPage().isFieldInvalid(field)) {
                System.out.println("EROARE! Campul " + field.getAttribute("id") + " este invalid.");
            }
        }
    }
}