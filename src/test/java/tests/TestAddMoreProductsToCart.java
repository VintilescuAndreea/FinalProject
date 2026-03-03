package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import sharedData.SharedData;
import utils.LogUtility;

public class TestAddMoreProductsToCart extends SharedData {

    @Test
    public void testAddMoreProductsAndValidateTotal() {
        HomePage homePage = new HomePage(getDriver());
        CartPage cartPage = new CartPage(getDriver());
        LogUtility logUtility = new LogUtility();

        int targetProductsToAdd = 5;
        int maxProductsToTry = 20;
        int addedProducts = 0;

        for (int index = 1; index <= maxProductsToTry && addedProducts < targetProductsToAdd; index++) {
            homePage.navigateToHomePage();
            logUtility.infoLog("Am accesat pagina home pentru produsul cu index: " + index);

            homePage.openProductFromListByIndex(index);
            logUtility.infoLog("Am deschis produsul cu index: " + index);

            if (homePage.isAddToCartAvailable()) {
                homePage.clickAddToCart();
                addedProducts++;
                logUtility.infoLog("Am adaugat produsul cu index " + index + " in cos. Produse adaugate: " + addedProducts);
            } else {
                logUtility.infoLog("Am sarit peste produsul cu index " + index + " deoarece este indisponibil (out of stock).");
            }
        }

        Assert.assertEquals(addedProducts, targetProductsToAdd,
                "Nu am reusit sa adaug " + targetProductsToAdd + " produse disponibile in cos.");

        Assert.assertTrue(homePage.isCartButtonDisplayed(),
                "Butonul de cart ar trebui sa fie vizibil in header dupa adaugarea produselor in cos.");

        homePage.clickCartButton();
        cartPage.waitUntilCheckoutIsOpened();

        double calculatedTotal = cartPage.calculateTotalOfProducts();
        double displayedTotal = cartPage.displayCheckoutTotal();

        logUtility.infoLog("Total calculat din produsele din cos: " + calculatedTotal);
        logUtility.infoLog("Total afisat in checkout/cart: " + displayedTotal);

        Assert.assertEquals(calculatedTotal, displayedTotal, 0.01,
                "Totalul afisat nu corespunde cu suma produselor din cos.");
    }
}