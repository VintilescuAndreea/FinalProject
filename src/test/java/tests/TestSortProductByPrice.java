package tests;

import org.testng.annotations.Test;
import sharedData.SharedData;
import utils.TestUtility;

public class TestSortProductByPrice extends SharedData {

    // Sortez lista si dupa verific ca e ordinea buna

    @Test
    public void testSortProductByPrice() {
        TestUtility testUtility = new TestUtility(getDriver());

        testUtility.getHomePage().navigateToHomePage();
        testUtility.getHomePage().sortProductsByPriceLowToHigh();

        testUtility.getHomePage().verifyOrder();


    }
}
/* Probleme intampinate:
        - StaleElement
 */