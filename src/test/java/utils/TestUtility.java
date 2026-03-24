package utils;

import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import org.openqa.selenium.WebDriver;

public class TestUtility {

    private final WebDriver driver;
    private final HomePage homePage;
    private final CartPage cartPage;
    private final LoginPage loginPage;
    private final RegisterPage registerPage;

    public TestUtility(WebDriver driver) {
        this.driver = driver;
        this.homePage = new HomePage(driver);
        this.cartPage = new CartPage(driver);
        this.loginPage = new LoginPage(driver);
        this.registerPage = new RegisterPage(driver);
    }

    public int addMultipleProductsToCart(int numberOfProductsToAdd) {
        int maxProductsToTry = 20;
        int addedProducts = 0;

        for (int index = 1; index <= maxProductsToTry && addedProducts < numberOfProductsToAdd; index++) {
            homePage.navigateToHomePage();
            LogUtility.infoLog("Din Home Page am accesat produsul cu index: " + index);

            homePage.openProductFromListByIndex(index);

            if (homePage.isAddToCartAvailable()) {
                homePage.clickAddToCart();
                addedProducts++;
                LogUtility.infoLog("Produs adaugat in cos. Total produse: " + addedProducts);
            }
        }

        return addedProducts;
    }

    public void navigateToRegistrationPage() {
        homePage.navigateToHomePage();
        LogUtility.infoLog("Am navigat pe Home Page");
        homePage.clickSignInButton();
        LogUtility.infoLog("Am dat click pe butonul 'Sign In'.");
        loginPage.clickRegisterLink();
        LogUtility.infoLog("Am dat click pe 'Register link'.");
    }

    public void navigateToCart() {
        if (homePage.isCartButtonDisplayed()) {
            homePage.clickCartButton();
            LogUtility.infoLog("Am accesat cosul de cumparaturi.");
        } else {
            LogUtility.infoLog("Butonul cart nu este vizibil!");
        }
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public CartPage getCartPage() {
        return cartPage;
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public RegisterPage getRegisterPage() {
        return registerPage;
    }

    public static LogUtility getLogUtility() {
        return new LogUtility();
    }
}
