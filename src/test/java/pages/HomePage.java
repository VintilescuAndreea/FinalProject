package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage{

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

    @FindBy(xpath = "(//a[contains(@href,'/product/')])[1]")
    private WebElement firstProductFromListing;

    public void navigateToHomePage() {
        driver.get(url);
    }

    public void clickSignInButton() {
        signInButton.click();
    }

    public boolean isCartButtonDisplayed() {
        return cartButton.isDisplayed();
    }
    public void clickCartButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[toast-component]")));
        } catch (TimeoutException ignored) {
            // In some runs toast may not exist or may disappear quickly; continue to clickable wait.
        }

        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }
    public void openFirstProductFromListing() {
        firstProductFromListing.click();
    }
    public void clickAddToCart() {
        addToCartButton.click();
    }
    }




