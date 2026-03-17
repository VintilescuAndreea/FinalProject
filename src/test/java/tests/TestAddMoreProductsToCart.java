package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.LogUtility;
import utils.TestUtility;

public class TestAddMoreProductsToCart extends SharedData {

    @Test
    public void testAddMoreProductsAndValidateTotal() {
        TestUtility testUtility = new TestUtility(getDriver());

        int targetProductsToAdd = 5;
        int addedProducts = testUtility.addMultipleProductsToCart(targetProductsToAdd);

        Assert.assertEquals(addedProducts, targetProductsToAdd, "Nu am reusit sa adaug " + targetProductsToAdd + " produse disponibile in cos.");
        Assert.assertTrue(testUtility.getHomePage().isCartButtonDisplayed(), "Butonul de cart ar trebui sa fie vizibil in header dupa adaugarea produselor in cos.");

        testUtility.getHomePage().clickCartButton();
        testUtility.getCartPage().waitUntilCheckoutIsOpened();

        double calculatedTotal = testUtility.getCartPage().calculateTotalOfProducts();
        double displayedTotal = testUtility.getCartPage().displayCheckoutTotal();

        LogUtility.infoLog("Total calculat din produsele din cos: " + calculatedTotal);
        LogUtility.infoLog("Total afisat din cosul de cumparaturi: " + displayedTotal);

        Assert.assertEquals(calculatedTotal, displayedTotal, 0.01,
                "Totalul afisat nu corespunde cu suma produselor din cos.");
    }
}