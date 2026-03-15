package tests;

import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.TestUtility;

public class TestSuccessfulRegistration extends SharedData {

    @Test
    public void testSuccessfulRegistration() {
        TestUtility testUtility = new TestUtility(getDriver());

        testUtility.navigateToRegistrationPage();

        testUtility.getRegisterPage().registerUser("Andreea", "Test", "23/02/1999",
                "Iuliu Maniu", "061126", "Bucharest", "Sector 6",
                "Romania", "0765354782", "vinti23@gmail.com", "Test123!");

    }
}
