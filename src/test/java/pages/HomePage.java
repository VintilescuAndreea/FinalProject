package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogUtility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static utils.LogUtility.infoLog;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private static final String url = "https://practicesoftwaretesting.com/";
    private static final Duration waitTime = Duration.ofSeconds(10);

    @FindBy(css = "[data-test='nav-sign-in']")
    private WebElement signInButton;

    @FindBy(css = "[data-test='nav-cart']")
    private WebElement cartButton;

    @FindBy(css = "[data-test='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy (xpath = "//select[@class='form-select']")
    private WebElement sortByDropdown;

    @FindBy(xpath = "//input[@id='search-query']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@data-test='search-submit']")
    private WebElement searchButton;

    @FindBy (xpath = "//span[@data-test='product-price']")
    private List<WebElement> productPrices;


    public void navigateToHomePage() {
        driver.get(url);
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public void openFirstProductFromList() {
        openProductFromListByIndex(1);
    }

    public void openProductFromListByIndex(int index) {
        By productByIndex = By.xpath("(//a[contains(@href,'/product/')])[" + index + "]");
        WebElement productLink = getWait().until(ExpectedConditions.elementToBeClickable(productByIndex));
        productLink.click();
    }

    public boolean isAddToCartAvailable() {
        try {
            getWait().until(ExpectedConditions.visibilityOf(addToCartButton));
            return addToCartButton.isEnabled();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    public void clickAddToCart() {
        WebElement button = getWait().until(ExpectedConditions.elementToBeClickable(addToCartButton));
        button.click();
        waitForToastToDisappear();
    }

    public boolean isCartButtonDisplayed() {
        try {
            return getWait().until(ExpectedConditions.visibilityOf(cartButton)).isDisplayed();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    public void clickCartButton() {
        waitForToastToDisappear();
        getWait().until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    private void waitForToastToDisappear() {
        try {
            getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[toast-component]")));
        } catch (TimeoutException ignored) {
        }
    }

    public void sortProductsByPriceLowToHigh() {
        WebElement dropdown = getWait().until(ExpectedConditions.elementToBeClickable(sortByDropdown));
        dropdown.click();
        LogUtility.infoLog("Am apasat pe dropdown pentru sortare");

        WebElement option = getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='price,asc']")));
        option.click();
        LogUtility.infoLog("Am selectat optiunea de sortare dupa pret crescator");

        getWait().until(ExpectedConditions.visibilityOfAllElements(productPrices));
        LogUtility.infoLog("Am asteptat sa se incarce preturile produselor dupa sortare.");
    }

    public void verifyOrder() {
        List<WebElement> pricesElements = getWait().until(
                ExpectedConditions.visibilityOfAllElements(productPrices));

        List<Double> prices = new ArrayList<Double>();
        for (WebElement price : pricesElements) {
            String priceText = price.getText().replace("$", "").trim();
            prices.add(Double.parseDouble(priceText));
            LogUtility.infoLog("A fost listat produsul cu pretul: " + priceText);
        }
        LogUtility.infoLog("Incepe verificarea ordinii preturilor.");

        for (int i = 0; i < prices.size() - 1; i++) {
            if (prices.get(i) > prices.get(i + 1)) {
                throw new AssertionError("Produsele nu sunt sortate corect dupa pret. Pretul " + prices.get(i) + " este mai mare decat " + prices.get(i + 1));
            }
        }

        LogUtility.infoLog("Produsele au fost sortate corect.");
    }

    public void searchForProduct(String product) {
        WebElement searchField = getWait().until(ExpectedConditions.elementToBeClickable(searchInput));
        searchField.clear();
        searchField.sendKeys(product);
        searchButton.click();
        infoLog("Am introdus in campul de cautare: " + product + " si am apasat pe butonul de search.");

        getWait().until(ExpectedConditions.textToBe(By.xpath("//span[@data-test='search-term']"), product));
        infoLog("Sunt listate toate produsele care contin termenul " + product +"'.");
    }

    public List<String> findProductByName(String product) {
        List<String> matchedProducts = new ArrayList<>();
        getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h5[@data-test='product-name']")));

        List<WebElement> productElements = driver.findElements(By.xpath("//h5[@data-test='product-name']"));

        for (WebElement element : productElements) {
            try {
                String name = element.getText().trim();
                if (name.toLowerCase().contains(product.toLowerCase())) {
                    matchedProducts.add(name);
                    LogUtility.infoLog("Am gasit produsul cu numele: '" + name+ "'.");
                }
            } catch (StaleElementReferenceException e) {
                // m a ajutat gepeto
                LogUtility.infoLog("Elementul a devenit stale, îl refetchăm...");
                WebElement freshElement = driver.findElement(By.xpath("//h5[@data-test='product-name'][text()='" + element.getText() + "']"));
                String name = freshElement.getText().trim();
                if (name.toLowerCase().contains(product.toLowerCase())) {
                    matchedProducts.add(name);
                }
            }
        }

        return matchedProducts;
    }
}
