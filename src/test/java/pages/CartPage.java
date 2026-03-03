package pages;

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

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void waitUntilCheckoutIsOpened() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/checkout"));
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
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(totalPrice));

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
}