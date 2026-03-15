package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "[data-test='line-price']")
    private List<WebElement> linePrices;

    @FindBy(css = "[data-test='cart-total']")
    private WebElement totalPrice;

    @FindBy( xpath ="//a[@class='btn btn-danger']")
    private List<WebElement> removeButtons;

    @FindBy(css = "tr.ng-star-inserted")
    private List<WebElement> cartProducts;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void waitUntilCheckoutIsOpened() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("/checkout"));
    }

    public double calculateTotalOfProducts() {
        if (linePrices.isEmpty()) {
            throw new IllegalStateException("Nu am gasit preturile produselor din cos.");
        }

        double sum = 0;
        for (WebElement linePrice : linePrices) {
            sum += parsePrice(linePrice.getText());
        }

        return round(sum);
    }

    public double displayCheckoutTotal() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(totalPrice));

        return round(parsePrice(totalPrice.getText()));
    }

    private double parsePrice(String text) {
        StringBuilder numeric = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isDigit(character) || character == '.' || character == ',') {
                numeric.append(character);
            }
        }

        String value = numeric.toString();
        if (value.isBlank()) {
            throw new IllegalStateException("Pret invalid: " + text);
        }

        value = value.replace(',', '.');

        int lastDot = value.lastIndexOf('.');
        if (lastDot > 0) {
            String integerPart = value.substring(0, lastDot).replace(".", "");
            String decimalPart = value.substring(lastDot + 1).replace(".", "");
            value = integerPart + "." + decimalPart;
        }

        return Double.parseDouble(value);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    public void waitForToastToDisappear() {
        try {
            getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.toast-success")));
        } catch (TimeoutException e) {
        }
    }

    public void removeAllProducts() {

        waitForToastToDisappear();

        List<WebElement> removeButtons = driver.findElements(By.cssSelector(".btn.btn-danger"));

        while (!removeButtons.isEmpty()) {
            getWait().until(ExpectedConditions.elementToBeClickable(removeButtons.get(0))).click();

            waitForToastToDisappear();
            removeButtons = driver.findElements(By.cssSelector(".btn.btn-danger"));
        }
    }
    public boolean isCartEmpty() {
        return cartProducts.isEmpty();
    }

    public double getTotalPrice() {
        WebElement total = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='cart-total']")));
        return round(parsePrice(total.getText()));
    }

    public void removeFirstProduct() {
        waitForToastToDisappear();

        By removeButtonBy = By.cssSelector(".btn.btn-danger");
        List<WebElement> currentRemoveButtons = driver.findElements(removeButtonBy);

        if (currentRemoveButtons.isEmpty()) {
            throw new IllegalStateException("Nu exista produse in cos pentru stergere.");
        }

        int productsBefore = currentRemoveButtons.size();

        try {
            getWait().until(ExpectedConditions.elementToBeClickable(currentRemoveButtons.get(0))).click();
        } catch (StaleElementReferenceException exception) {
            getWait().until(ExpectedConditions.elementToBeClickable(removeButtonBy)).click();
        }

        waitForToastToDisappear();

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.numberOfElementsToBeLessThan(removeButtonBy, productsBefore));
    }
}