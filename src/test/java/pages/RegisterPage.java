package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends BasePage{

    public RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="first_name")
    private WebElement firstNameInput;

    @FindBy(id="last_name")
    private WebElement lastNameInput;

    @FindBy(id = "dob")
    private WebElement dobInput;

    @FindBy(id="street")
    private WebElement streetInput;

    @FindBy(id="postal_code")
    private WebElement postalCodeInput;

    @FindBy(id="city")
    private WebElement cityInput;

    @FindBy(id="state")
    private WebElement stateInput;

    @FindBy(xpath = "//select[@id='country']")
    private WebElement countrySelect;

    @FindBy(id="phone")
    private WebElement phoneInput;

    @FindBy(id="email")
    private WebElement emailInput;

    @FindBy(id="password")
    private WebElement passwordInput;

    @FindBy(css = "button[data-test='register-submit']")
    private WebElement registerButton;


    public void registerUser(String firstName, String lastName, String dob, String street, String postalCode, String city, String state, String country, String phone, String email, String password) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        dobInput.sendKeys(dob);
        streetInput.sendKeys(street);
        postalCodeInput.sendKeys(postalCode);
        cityInput.sendKeys(city);
        stateInput.sendKeys(state);
        countrySelect.sendKeys(country);
        phoneInput.sendKeys(phone);
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        registerButton.click();
    }
}
