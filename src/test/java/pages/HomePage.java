package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage{

    private String url = "https://practicesoftwaretesting.com/";

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@data-test='nav-sign-in']")
    private WebElement signInButton;


    public void navigateToHomePage() {
        driver.get(url);
    }

    public void clickSignInButton() {
        signInButton.click();
    }


}
