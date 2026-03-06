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

}