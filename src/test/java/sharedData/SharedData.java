package sharedData;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import utils.LogUtility;

import java.time.Duration;

public class SharedData {
    private WebDriver driver;
    private String testName;

    @BeforeMethod
    public void prepareEnvironment() {
        testName = this.getClass().getSimpleName();
        driver = new ChromeDriver();
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LogUtility.startTest(testName);
    }

    @AfterMethod
    public void cleareEnvironment() {
        driver.quit();
        LogUtility.finishTest(testName);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}