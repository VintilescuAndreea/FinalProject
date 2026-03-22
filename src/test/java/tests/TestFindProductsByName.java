package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.LogUtility;
import utils.TestUtility;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class TestFindProductsByName extends SharedData {

    @Test
    public void testFindProductsByName() {

        TestUtility testUtility = new TestUtility(getDriver());

        LogUtility.infoLog(" Am accesat pagina de home. ");
        testUtility.getHomePage().navigateToHomePage();
        String productName = "hammer";
        LogUtility.infoLog("Caut produsul: " + productName);
        testUtility.getHomePage().searchForProduct(productName);

        List<WebElement> matchedProducts = testUtility.getHomePage().findProductByName(productName);

        for (WebElement product : matchedProducts) {
            assertTrue(product.getText().toLowerCase().contains(productName.toLowerCase()),
                    "Produsul gasit nu contine textul cautat: " + product.getText());
        }

        LogUtility.infoLog("Total produse gasite cu numele '" + productName + "': " + matchedProducts.size());

    }
}
