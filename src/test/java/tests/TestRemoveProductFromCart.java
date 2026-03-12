package tests;
//  DE TERMINAT
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import sharedData.SharedData;
import utils.LogUtility;

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

        HomePage homePage = new HomePage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        LogUtility logUtility = new LogUtility();

        int numberOfProductsToAdd = 2;
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

        if (homePage.isCartButtonDisplayed()) {
            homePage.clickCartButton();
            LogUtility.infoLog("Am accesat cosul.");
        } else {
            LogUtility.infoLog("Cart button nu este vizibil!");
            return;
        }

        double totalBeforeRemoval = cartPage.getTotalPrice();
        logUtility.infoLog("Total inainte de stergere: " + totalBeforeRemoval);

        cartPage.removeFirstProduct();
        logUtility.infoLog("Primul produs a fost sters din cos.");

        double totalAfterRemoval = cartPage.getTotalPrice();
        logUtility.infoLog("Total dupa stergere: " + totalAfterRemoval);

    }

}
