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

        LogUtility.infoLog(" Am accesat Home Page. ");
        testUtility.getHomePage().navigateToHomePage();
        String productName = "pliers";
        LogUtility.infoLog("Caut produsul: " + productName);
        testUtility.getHomePage().searchForProduct(productName);

        List<String> matchedProducts = testUtility.getHomePage().findProductByName(productName);

        for ( String product : matchedProducts) {
            assertTrue(product.toLowerCase().contains(productName.toLowerCase()));
        }

        LogUtility.infoLog("Totalul produselor gasite cu numele '" + productName + "': " + matchedProducts.size());

    }
}
/* Probleme intampinate:
        - Nu pare ca merge search-ul, trebuie s ami aduca 7 produse, nu 4..-> nu se reincarca lista noua de produse
          am folosit stalenessOf(initialProducts.get(0)) - practic asteapta pana cand primul element din lista initiala devine stale,
          adica nu mai este in DOM, ceea ce indica faptul ca lista de produse a fost actualizata dupa cautare.

        - ??  DAR DACA NU SE SCHIMBA PRIMUL ELEM? Pun de ex pliers, care e primul el din lista si nu pare sa se schimbe...
          help de la gepeto: am prins exceptia si daca reapare refac elementul din DOM ca sa il pot citi din nou
 */