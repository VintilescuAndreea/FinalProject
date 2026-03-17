package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.LogUtility;
import utils.TestUtility;

public class TestModifyTheQuantityOfProducts extends SharedData {

    /* Adauf mai multe produse in cos si apoi modific cantitatea pentru unul dintre ele. Verific daca totalul se actualizeaza corect.
    Merg mai departe si efectuez plata? -> PROCEED THE CHECKOUT -> GUEST? ?
    DE REVENIT
     */
    @Test
    public void testModifyProductQuantity() {

        TestUtility testUtility = new TestUtility(getDriver());
            int targetProductsToAdd = 3;
            int addedProducts = testUtility.addMultipleProductsToCart(targetProductsToAdd);

        Assert.assertEquals(addedProducts, targetProductsToAdd, "Nu am reusit sa adaug " + targetProductsToAdd + " produse disponibile in cos.");
        Assert.assertTrue(testUtility.getHomePage().isCartButtonDisplayed(), "Butonul de cart ar trebui sa fie vizibil in header dupa adaugarea produselor in cos.");

        testUtility.getHomePage().clickCartButton();
        testUtility.getCartPage().waitUntilCheckoutIsOpened();

        double totalBeforeRemoval = testUtility.getCartPage().getTotalPrice();
        LogUtility.infoLog("Total plata inainte de modificare cantitate: " + totalBeforeRemoval);

        testUtility.getCartPage().changeQuantity(5);

        testUtility.getCartPage().waitForToastToDisappear();
        Assert.assertEquals(testUtility.getCartPage().getQuantityInput(),  5, "Cantitatea modificata nu este corecta in cos.");
        LogUtility.infoLog("Am modificat cantitatea primului produs la 5 bucati.");

        testUtility.getCartPage().waitUntilTotalChanges(totalBeforeRemoval);
        double totalAfterModification = testUtility.getCartPage().getTotalPrice();
        LogUtility.infoLog("Total plata dupa modificare cantitate: " + totalAfterModification);
        Assert.assertNotEquals(totalBeforeRemoval, totalAfterModification, "Totalul dupa modificarea cantitatii ar trebui sa fie diferit de totalul initial.");

        testUtility.getCartPage().checkout1();
        LogUtility.infoLog("Am dat click pe primul buton 'Proceed to checkout'.");

        testUtility.getCartPage().fillGuestCheckoutForm("test@gmail.com", "FirstName", "LastName");
        LogUtility.infoLog("Am completat formularul de checkout pentru guest.");
        testUtility.getCartPage().checkout2();
        LogUtility.infoLog("Am dat click pe al doilea buton 'Proceed to checkout'.");
        testUtility.getCartPage().fillGuestAddressForm("Strada", "Numar", "Oras", "CodPostal", "Romania");
        LogUtility.infoLog("Am completat formularul de adresa pentru guest si am continuat fluxul de checkout.");

        testUtility.getCartPage().selectPaymentMethod("Cash on Delivery");
        LogUtility.infoLog("Am selectat metoda de plata 'Cash on Delivery'.");

         testUtility.getCartPage().getFinishButton();
        LogUtility.infoLog("Am confirmat comanda.");








    }

    /* Probleme intampinate:
         - La actualizarea cantitatii, totalul nu se actualizeaza automat/imediat-> adaug nmetoda noua wait..
     */

}
