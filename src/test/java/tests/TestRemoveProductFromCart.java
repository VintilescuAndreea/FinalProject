package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.LogUtility;
import utils.TestUtility;

public class TestRemoveProductFromCart extends SharedData {
    /* Adaug mai multe produse -> verific ca totalul este corect -> sterg un produs -> verific ca totalul s-a actualizat corect
     1. Modificare in CartPage pentru a putea sterge un produs din cos -> removeButton
      + metoda de remodeProduct
      2. testul in TestRemoveProductFromCart:
        - adaug 2 produse in cos
        - verific totalul
        - sterg unul dintre produse
        - verific ca totalul s-a actualizat corect
     */

    @Test
    public void testRemoveProductFromCart() {
        TestUtility testUtility = new TestUtility(getDriver());

        int numberOfProductsToAdd = 2;
        int addedProducts = testUtility.addMultipleProductsToCart(numberOfProductsToAdd);

        Assert.assertEquals(addedProducts, numberOfProductsToAdd, "Nu s au putut adauga suficiente produse in cos pentru test.");
        Assert.assertTrue(testUtility.getHomePage().isCartButtonDisplayed(), "Butonul pt cosul de cumparaturi nu este vizibil dupa adaugarea produselor.");

        testUtility.getHomePage().clickCartButton();
        LogUtility.infoLog("Am accesat cosul.");

        double totalBeforeRemoval = testUtility.getCartPage().getTotalPrice();
        LogUtility.infoLog("Totalul inainte de stergere: " + totalBeforeRemoval);

        testUtility.getCartPage().removeFirstProduct();
        LogUtility.infoLog("Primul produs a fost sters din cos.");

        double totalAfterRemoval = testUtility.getCartPage().getTotalPrice();
        LogUtility.infoLog("Totalul dupa stergere: " + totalAfterRemoval);

        Assert.assertTrue(totalAfterRemoval < totalBeforeRemoval, "Totalul dupa stergerea primului produs ar trebui sa fie mai mic.");
    }
}