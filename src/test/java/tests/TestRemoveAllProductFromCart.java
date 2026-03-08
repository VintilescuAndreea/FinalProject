package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import sharedData.SharedData;
import utils.LogUtility;

public class TestRemoveAllProductFromCart extends SharedData {
    // Sterg toate produsele din cos si verific ca totalul este 0
     /* 1. Modificare in CartPage pentru a putea sterge toate produsele din cos -> removeAllProducts
       + metoda de removeAllProducts
       2. testul in TestRemoveAllProductFromCart:
         - adaug 2 produse in cos
         - verific totalul
         - sterg toate produsele din cos
         - verific ca totalul este 0
      */

    @Test
    public void testRemoveAllProductsFromCart() {

        HomePage homePage = new HomePage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        LogUtility logUtility = new LogUtility();

        int numberOfProductsToAdd = 5;
        int maxProductsToTry = 20;
        int addedProducts = 0;

        for (int index = 1; index <= maxProductsToTry && addedProducts < numberOfProductsToAdd; index++) {

            homePage.navigateToHomePage();
            logUtility.infoLog("Am accesat home pentru produsul cu index: " + index);

            homePage.openProductFromListByIndex(index);

            if (homePage.isAddToCartAvailable()) {
                homePage.clickAddToCart();
                addedProducts++;
                logUtility.infoLog("Produs adaugat in cos. Total produse: " + addedProducts);
            }
        }

        Assert.assertEquals(addedProducts, numberOfProductsToAdd,
                "Nu s-au putut adauga suficiente produse.");

        if (homePage.isCartButtonDisplayed()) {
            homePage.clickCartButton();
            LogUtility.infoLog("Am accesat cosul.");
        } else {
            LogUtility.infoLog("Cart button nu este vizibil!");
            return;
        }

        cartPage.removeAllProducts();
        LogUtility.infoLog("Au fost sterse toate produsele din cos.");

        boolean isCartEmpty = cartPage.isCartEmpty();
        if (isCartEmpty) {
            LogUtility.infoLog("Coșul este gol. Test trecut!");
        } else {
            LogUtility.infoLog("Coșul NU este gol. Test eșuat!");
        }
    }
}
 /* Probleme intampinate:
        - Dupa ce se sterge primul element din lista, testul pica => STALE ELEMENT?
        - Toast-ul acopera butoanele => adaug wait pentru toast sa dispara
  */
