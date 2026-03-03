package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {

    private final By linePriceLocator = By.cssSelector("[data-test='line-price'], [data-test='cart-item-total-price']");
    private final By totalLocator = By.cssSelector("[data-test='cart-total'], [data-test='total'], .total-price");

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void waitUntilCheckoutIsOpened() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/checkout"));
    }

    public double calculateTotalOfProducts() {
        List<WebElement> linePrices = driver.findElements(linePriceLocator);

        double sum = 0.0;
        int counted = 0;

        for (WebElement linePrice : linePrices) {
            Double parsedValue = extractPrice(linePrice.getText());
            if (parsedValue != null) {
                sum += parsedValue;
                counted++;
            }
        }

        if (counted == 0) {
            throw new IllegalStateException("Nu am gasit preturi valide pentru produsele din cos.");
        }

        return roundTo2Decimals(sum);
    }

    public double displayCheckoutTotal() {
        List<WebElement> totals = driver.findElements(totalLocator);

        for (WebElement total : totals) {
            Double parsedValue = extractPrice(total.getText());
            if (parsedValue != null) {
                return roundTo2Decimals(parsedValue);
            }
        }

        throw new IllegalStateException("Nu am putut identifica totalul de plata pe pagina de checkout/cart.");
    }

    private Double extractPrice(String text) {
        String valueOnly = keepOnlyNumbersAndSeparators(text);
        if (valueOnly.isBlank()) {
            return null;
        }

        int lastComma = valueOnly.lastIndexOf(',');
        int lastDot = valueOnly.lastIndexOf('.');
        int decimalSeparatorIndex = Math.max(lastComma, lastDot);

        String normalized;
        if (decimalSeparatorIndex >= 0) {
            String integerPart = valueOnly.substring(0, decimalSeparatorIndex)
                    .replace(",", "")
                    .replace(".", "");

            String decimalPart = valueOnly.substring(decimalSeparatorIndex + 1)
                    .replace(",", "")
                    .replace(".", "");

            if (integerPart.isBlank()) {
                integerPart = "0";
            }

            normalized = integerPart + "." + decimalPart;
        } else {
            normalized = valueOnly;
        }

        return Double.parseDouble(normalized);
    }

    private String keepOnlyNumbersAndSeparators(String text) {
        StringBuilder builder = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isDigit(character) || character == ',' || character == '.') {
                builder.append(character);
            }
        }

        return builder.toString();
    }

    private double roundTo2Decimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}