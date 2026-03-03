package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import sharedData.SharedData;
import utils.LogUtility;

public class TestAddFirstProductToCart extends SharedData {

    @Test
    public void testAddOneProductToCart() {
        HomePage homePage = new HomePage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        LogUtility logUtility = new LogUtility();

        homePage.navigateToHomePage();
        logUtility.infoLog(" Am accesat pagina de home ");
        homePage.openFirstProductFromList();
        logUtility.infoLog(" Am deschis primul produs din lista de produse ");
        homePage.clickAddToCart();
        logUtility.infoLog(" Am adaugat produsul in cos ");

        Assert.assertTrue(homePage.isCartButtonDisplayed(),
                "Butonul de cart ar trebui sa fie vizibil in header dupa adaugarea unui produs in cosul de cumparaturi.");
        logUtility.infoLog(" Am verificat ca butonul de cart este vizibil in header. ");
        homePage.clickCartButton();
        logUtility.infoLog("Am dat click pe butonul de cart pentru a naviga catre pagina de cart ");

    }
}
