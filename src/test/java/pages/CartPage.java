package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "h1")
    private WebElement pageHeading;

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getHeadingText() {
        return pageHeading.getText();
    }
}