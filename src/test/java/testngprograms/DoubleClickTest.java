package testngprograms;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;

public class DoubleClickTest {
    private WebDriver driver=null;
    private WebDriverWait wait= null;

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void launchBrowser(@Optional("chrome") String browser){
        Reporter.log("@BeforeClass annotation will be executed before any test inside the current class", true);
        if(browser.equalsIgnoreCase("chrome")) {
            Reporter.log("launching chrome browser", true);
            driver = new ChromeDriver();
        }else if(browser.equalsIgnoreCase("firefox")){
            driver = new FirefoxDriver();
        }else if(browser.equalsIgnoreCase("edge")){
            driver = new EdgeDriver();
        }else if(browser.equalsIgnoreCase("safari")){
            driver = new SafariDriver();
        }
        Reporter.log("maximize the chrome browser",true);
        driver.manage().window().maximize();
        Reporter.log("add implicitWait",true);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Reporter.log("add explicitWait",true);
        wait=new WebDriverWait(driver,Duration.ofMillis(30000));
        Reporter.log("clear the cookies after each @Test method",true);
        driver.manage().deleteAllCookies();

    }
    @AfterClass(alwaysRun = true)
    public void closeBrowser(){
        Reporter.log("closing the browser",true);
        if(driver!=null){
            driver.quit();
        }
    }

    @Test(description = "double click test")
    public void doubleClickTest() throws InterruptedException {
        Reporter.log("@Test double click",true);
        Reporter.log("launch the application",true);
        driver.get("https://testautomationpractice.blogspot.com/");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle("Automation Testing Practice");
        Reporter.log("double click on the menu",true);
        WebElement copyTextBtn=driver.findElement(By.xpath("//button[normalize-space()='Copy Text']"));
        //fetch field1 value
        String field1InputValue=driver.findElement(By.xpath("//input[@id='field1']")).getDomAttribute("value");
        String field2InputValue=driver.findElement(By.xpath("//input[@id='field2']")).getDomAttribute("value");

        Reporter.log("field1 value is:"+field1InputValue,true);
        Reporter.log("field2 value is:"+field2InputValue,true);
        Assert.assertEquals(field1InputValue,field2InputValue);



        //create object for actions class
        Actions act=new Actions(driver);
        act.doubleClick(copyTextBtn).perform();
        //fetch field1 value

        
        wait.until(ExpectedConditions.titleIs("Mac"));
        Assert.assertEquals(driver.getTitle(),"Mac");
        Assert.assertTrue("Mac heading is not present", driver.findElement(By.xpath("//h2[normalize-space()='Mac']")).isDisplayed());

        Thread.sleep(3000);




    }


    private void verifyHomePageTitle(String exptitle){
        wait.until(ExpectedConditions.titleIs(exptitle));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,exptitle);

    }

}
