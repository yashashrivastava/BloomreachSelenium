package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class LoginPage {

    private Logger logger = LoggerFactory.getLogger(LoginPage.class);

    private WebDriver driver;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement UserName;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement Password;

    @FindBy(xpath = "//select[@name='locale']")
    private WebElement languageDropDown;

    @FindBy(xpath = "//button[@name='p::submit']")
    private WebElement loginButton;

    // Initializing the Page Objects:
    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login(String authorUerName, String authorPassword){
        UserName.sendKeys(authorUerName);
        Password.sendKeys(authorPassword);
        Select languageDropDown = new Select(this.languageDropDown);
        languageDropDown.selectByValue("en");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'entry last')]")));
    }
}
