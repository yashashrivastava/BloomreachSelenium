package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ContentPage {
    private Logger logger = LoggerFactory.getLogger(ContentPage.class);

    private WebDriver driver;

    @FindBy(xpath = "//div[contains(text(), 'Content')]")
    private WebElement contentMenu ;

    @FindBy(xpath = "//a[@title='Add root folder']")
    private WebElement addRootFolder;

    @FindBy(xpath = "//input[@value name='name-url:name']")
    private WebElement folderName;

    @FindBy(xpath = "//input[contains(@class, 'btn btn-br-primary qa-dialog-btn-ok')]")
    private WebElement okButton;


    public ContentPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    private Optional<WebElement> getTestFolder(){
        try {
            return  Optional.ofNullable(driver.findElement(By.xpath("//a[@title='Test001']")));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    private Random random = new Random();

    public void createFolder(){
        Actions actions = new Actions(driver);
        actions.moveToElement(contentMenu).click().perform();

        driver.switchTo().frame(driver.findElement(By.xpath("//brna-client-app/iframe")));

        WebElement testFolderNew = getTestFolder().orElseGet(() -> {
            addRootFolder.click();
            folderName.sendKeys("Test001");
            okButton.click();
            return getTestFolder().get();
        });
    }

    public void createDocument() throws InterruptedException {
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement testFolder = getTestFolder().orElseThrow(() -> new RuntimeException(("Folder must be created by now.")));
        actions.moveToElement(testFolder).click().perform();
        WebElement dots = driver.findElement(By.xpath("//a[@title='Test001']/ancestor::div/a[contains(@class,'hippo-tree-dropdown-icon-container')]"));
        wait.until(ExpectedConditions.visibilityOf(dots));
        Thread.sleep(100);
        driver.findElement(By.xpath("//a[@title='Test001']/ancestor::div/a[contains(@class,'hippo-tree-dropdown-icon-container')]")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//span[contains(text(),'Add new document.')]")).click();
        driver.findElement(By.xpath("//input[contains(@name,'name-url')]")).sendKeys("New Document"+ random.nextInt(9999));
        Select languageDropDown = new Select(driver.findElement(By.xpath("//select[contains(@name,'prototype')]")));
        languageDropDown.selectByValue("hippogogreen:blogitem");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[contains(@value,'OK')]")).click();

        Thread.sleep(1000);


        WebElement docTitle = driver.findElement(By.xpath("//div[@class='tabpanel']//div[@style='display: block;']/descendant::h3[contains(@class,'title')]//parent::div/descendant::input"));

        docTitle.sendKeys("Some Title");

        WebElement doneButton = driver.findElement(By.xpath("//span[@title = 'Save changes and stop editing'][text() = 'Done']"));
        System.out.println("Current value of done is: " + doneButton.getText());

       JavascriptExecutor executor = (JavascriptExecutor)driver; executor. executeScript("arguments[0]. click();", doneButton);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }



}
