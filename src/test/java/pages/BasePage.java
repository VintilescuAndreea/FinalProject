package pages;

import org.openqa.selenium.WebDriver;

public class BasePage {

    public WebDriver driver;
    protected BasePage (WebDriver driver) {
        this.driver = driver;
    }
}
