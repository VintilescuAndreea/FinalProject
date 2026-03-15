package tests;

import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.LogUtility;
import utils.TestUtility;

public class TestSuccessfulLogin extends SharedData {

    @Test
    public void testSuccessfulLogin() {
        TestUtility testUtility = new TestUtility(getDriver());

        testUtility.navigateToRegistrationPage();

        testUtility.getRegisterPage().registerUser("Andreea", "Vintilescu", "23/02/1999",
                "Iuliu Maniu", "061126", "Bucharest", "Sector 6",
                "Romania", "0765354782", "vinti23@gmail.com", "Parola123!");

        LogUtility.infoLog("Completeaza formularul de inregistrare cu date valide si apasa pe butonul 'Register'");

        testUtility.getLoginPage().login("vinti23@gmail.com", "Parola123!");
        LogUtility.infoLog("Completeaza formularul de login cu email-ul si parola inregistrate anterior si apasa pe butonul 'Login'");
    }
}
