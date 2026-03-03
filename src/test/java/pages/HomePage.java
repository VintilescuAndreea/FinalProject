package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    private String url = "https://practicesoftwaretesting.com/";

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@data-test='nav-sign-in']")
    private WebElement signInButton;

    @FindBy(xpath = "//a[@data-test='nav-cart']")
    private WebElement cartButton;

    @FindBy(xpath = "//button[@data-test='add-to-cart' or normalize-space()='Add to cart' or contains(normalize-space(), 'Add to cart')]")
    private WebElement addToCartButton;

    public void navigateToHomePage() {
        driver.get(url);
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public void openFirstProductFromListing() {
        openProductFromListByIndex(1);
    }

    public void openProductFromListByIndex(int index) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(@href,'/product/')])[" + index + "]")));
        productLink.click();
    }


    public boolean isAddToCartAvailable() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(addToCartButton));
        return addToCartButton.isDisplayed() && addToCartButton.isEnabled() && addToCartButton.getAttribute("disabled") == null;
    }

    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOf(addToCartButton));
        wait.until(driver -> addToCartButton.isEnabled() && addToCartButton.getAttribute("disabled") == null);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartButton);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        } catch (ElementClickInterceptedException exception) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
        }

        waitForToastToDisappear(wait);
    }

    public boolean isCartButtonDisplayed() {
        return cartButton.isDisplayed();
    }

    public void clickCartButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        waitForToastToDisappear(wait);
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    private void waitForToastToDisappear(WebDriverWait wait) {

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[toast-component]")));
        } catch (TimeoutException ignored) {
        }
    }
}