package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.LogUtility;
import utils.TestUtility;

public class TestAddFirstProductToCart extends SharedData {

    @Test
    public void testAddOneProductToCart() {
        TestUtility testUtility = new TestUtility(getDriver());

        testUtility.getHomePage().navigateToHomePage();
        LogUtility.infoLog(" Am accesat pagina de home. ");
        testUtility.getHomePage().openFirstProductFromList();
        LogUtility.infoLog(" Am deschis primul produs din lista de produse. ");
        testUtility.getHomePage().clickAddToCart();
        LogUtility.infoLog(" Am adaugat produsul in cos. ");

        Assert.assertTrue(testUtility.getHomePage().isCartButtonDisplayed(), "Butonul de cart ar trebui sa fie vizibil in header dupa adaugarea unui produs in cosul de cumparaturi.");
        LogUtility.infoLog(" Am verificat ca butonul de cart este vizibil in header. ");
        testUtility.getHomePage().clickCartButton();
        LogUtility.infoLog("Am dat click pe butonul de cart pentru a naviga catre pagina de cart ");
    }
}
