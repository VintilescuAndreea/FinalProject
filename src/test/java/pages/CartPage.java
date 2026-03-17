package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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

    @FindBy (xpath = "//button[@data-test='proceed-1']")
    private WebElement CheckoutButton1;

    @FindBy(css = "tr.ng-star-inserted")
    private List<WebElement> cartProducts;

    @FindBy (xpath = "(//input[@data-test='product-quantity'])[1]")
    private WebElement quantityInput;

    @FindBy (xpath="//a[@href='#guest-tab']")
    private WebElement guestCheckoutTab;

    @FindBy (xpath = "//input[@id='guest-email']")
    private WebElement guestEmailInput;

    @FindBy (xpath = "//input[@id='guest-first-name']")
    private WebElement guestFirstNameInput;

    @FindBy (xpath = "//input[@id='guest-last-name']")
    private WebElement guestLastNameInput;

    @FindBy (xpath = "//input[@data-test='guest-submit']")
    private WebElement guestSubmitButton;

    @FindBy (xpath = "//button[@data-test='proceed-2-guest']")
    private WebElement checkoutButton2;

    @FindBy (xpath = "//input[@id='street']")
    private WebElement streetInputGuest;

    @FindBy (xpath = "//input[@id='city']")
    private WebElement cityInputGuest;

    @FindBy (xpath = "//input[@id='state']")
    private WebElement stateInputGuest;

    @FindBy (xpath = "//input[@id='country']")
    private WebElement countryInputGuest;

    @FindBy (xpath = "//input[@id='postal_code']")
    private WebElement postalCodeInputGuest;

    @FindBy (xpath = "//button[@data-test='proceed-3']")
    private WebElement checkoutButton3;

    @FindBy (xpath = "//select[@id='payment-method']")
    private WebElement paymentMethod;

    @FindBy(xpath="//button[@data-test='finish']")
    private WebElement finishButton;

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

    public void changeQuantity(int newQuantity) {
        WebElement quantityField = new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(quantityInput));
        quantityField.clear();
        quantityField.sendKeys(String.valueOf(newQuantity));
        quantityField.sendKeys(Keys.ENTER);
    }

    public void waitUntilTotalChanges(double oldTotal) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("[data-test='cart-total']"), String.valueOf(oldTotal))));

    }

    public void checkout1() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(CheckoutButton1)).click();
        guestCheckoutTab.click();
    }

    public void fillGuestCheckoutForm(String email, String firstName, String lastName) {
        guestCheckoutTab.click();
        guestEmailInput.sendKeys(email);
        guestFirstNameInput.sendKeys(firstName);
        guestLastNameInput.sendKeys(lastName);
        guestSubmitButton.click();
    }

    public void checkout2() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(checkoutButton2)).click();
    }

    public void fillGuestAddressForm(String street, String city, String state, String country, String postalCode) {
        streetInputGuest.sendKeys(street);
        cityInputGuest.sendKeys(city);
        stateInputGuest.sendKeys(state);
        countryInputGuest.sendKeys(country);
        postalCodeInputGuest.sendKeys(postalCode);
        checkoutButton3.click();
    }

    public void selectPaymentMethod(String paymentMethodText) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(paymentMethod));
        Select select = new Select(paymentMethod);
        select.selectByVisibleText(paymentMethodText);
    }

    public int getQuantityInput() {
        return Integer.parseInt(quantityInput.getAttribute("value"));
    }

    public void setQuantityInput(WebElement quantityInput) {
        this.quantityInput = quantityInput;
    }

    public List<WebElement> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<WebElement> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public List<WebElement> getRemoveButtons() {
        return removeButtons;
    }

    public void setRemoveButtons(List<WebElement> removeButtons) {
        this.removeButtons = removeButtons;
    }

    public void setTotalPrice(WebElement totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<WebElement> getLinePrices() {
        return linePrices;
    }

    public void setLinePrices(List<WebElement> linePrices) {
        this.linePrices = linePrices;
    }

    public WebElement getCheckoutButton1() {
        return CheckoutButton1;
    }

    public void setCheckoutButton1(WebElement checkoutButton1) {
        CheckoutButton1 = checkoutButton1;
    }

    public WebElement getGuestCheckoutTab() {
        return guestCheckoutTab;
    }

    public void setGuestCheckoutTab(WebElement guestCheckoutTab) {
        this.guestCheckoutTab = guestCheckoutTab;
    }

    public WebElement getGuestEmailInput() {
        return guestEmailInput;
    }

    public void setGuestEmailInput(WebElement guestEmailInput) {
        this.guestEmailInput = guestEmailInput;
    }

    public WebElement getGuestFirstNameInput() {
        return guestFirstNameInput;
    }

    public void setGuestFirstNameInput(WebElement guestFirstNameInput) {
        this.guestFirstNameInput = guestFirstNameInput;
    }

    public WebElement getGuestLastNameInput() {
        return guestLastNameInput;
    }

    public void setGuestLastNameInput(WebElement guestLastNameInput) {
        this.guestLastNameInput = guestLastNameInput;
    }

    public WebElement getGuestSubmitButton() {
        return guestSubmitButton;
    }

    public void setGuestSubmitButton(WebElement guestSubmitButton) {
        this.guestSubmitButton = guestSubmitButton;
    }

    public WebElement getCheckoutButton2() {
        return checkoutButton2;
    }

    public void setCheckoutButton2(WebElement checkoutButton2) {
        this.checkoutButton2 = checkoutButton2;
    }

    public WebElement getStreetInputGuest() {
        return streetInputGuest;
    }

    public void setStreetInputGuest(WebElement streetInputGuest) {
        this.streetInputGuest = streetInputGuest;
    }

    public WebElement getCityInputGuest() {
        return cityInputGuest;
    }

    public void setCityInputGuest(WebElement cityInputGuest) {
        this.cityInputGuest = cityInputGuest;
    }

    public WebElement getStateInputGuest() {
        return stateInputGuest;
    }

    public void setStateInputGuest(WebElement stateInputGuest) {
        this.stateInputGuest = stateInputGuest;
    }

    public WebElement getCountryInputGuest() {
        return countryInputGuest;
    }

    public void setCountryInputGuest(WebElement countryInputGuest) {
        this.countryInputGuest = countryInputGuest;
    }

    public WebElement getPostalCodeInputGuest() {
        return postalCodeInputGuest;
    }

    public void setPostalCodeInputGuest(WebElement postalCodeInputGuest) {
        this.postalCodeInputGuest = postalCodeInputGuest;
    }

    public WebElement getCheckoutButton3() {
        return checkoutButton3;
    }

    public void setCheckoutButton3(WebElement checkoutButton3) {
        this.checkoutButton3 = checkoutButton3;
    }

    public WebElement getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(WebElement paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public WebElement getFinishButton() {
        return finishButton;
    }

    public void setFinishButton(WebElement finishButton) {
        this.finishButton = finishButton;
    }
}