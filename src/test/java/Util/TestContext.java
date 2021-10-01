package Util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestContext {
    private WebDriver driver;
    private Properties prop;

    public WebDriver getDriver() {
        return driver;
    }

    public Properties getProp() {
        return prop;
    }

    public TestContext(){
        try {
            prop = new Properties();
            FileInputStream files = new FileInputStream("src/test/resources/config.properties");
            prop.load(files);
            initializeBrowser();
        } catch (IOException e) {
            e.getMessage();
        }
    }
    private void initializeBrowser(){
        String browserName = prop.getProperty("browser");
        if(browserName.equals("chrome")){
            System.setProperty("webdriver.chrome.driver", "src/test/resources/chrome/chromedriver");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        }
    }


    public void logOut() throws InterruptedException {
        driver.switchTo().defaultContent();
        Thread.sleep(100);
        Actions actions = new Actions(driver);
        WebElement userAccountMenu = driver.findElement(By.xpath("//div[contains(text(), 'User account')]"));
        actions.moveToElement(userAccountMenu).click().perform();
        WebElement logOutOption = driver.findElement(By.linkText("Logout"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(logOutOption));
        logOutOption.click();
    }


    public void quitBrowser(){
        driver.quit();
    }
}
