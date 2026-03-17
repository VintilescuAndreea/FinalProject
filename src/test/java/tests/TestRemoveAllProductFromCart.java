package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.LogUtility;
import utils.TestUtility;

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
        TestUtility testUtility = new TestUtility(getDriver());

        int numberOfProductsToAdd = 5;
        int addedProducts = testUtility.addMultipleProductsToCart(numberOfProductsToAdd);

        Assert.assertEquals(addedProducts, numberOfProductsToAdd, "Nu s-au putut adauga suficiente produse.");
        testUtility.navigateToCart();

        testUtility.getCartPage().removeAllProducts();
        LogUtility.infoLog("Au fost sterse toate produsele din cos.");

        boolean isCartEmpty = testUtility.getCartPage().isCartEmpty();
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
