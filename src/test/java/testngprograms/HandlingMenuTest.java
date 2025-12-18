package testngprograms;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;

public class HandlingMenuTest {
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

    @Test(description = "handling menus test")
    public void menuHandlingTest() throws InterruptedException {
        Reporter.log("@Test Handle menus",true);
        Reporter.log("launch the application",true);
        driver.get("https://tutorialsninja.com/demo/index.php");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle("Your Store");
        Reporter.log("mouse over on the menu",true);
        WebElement desktopMenu=driver.findElement(By.xpath("//a[normalize-space()='Desktops']"));


        //create object for actions class
        Actions act=new Actions(driver);
        act.moveToElement(desktopMenu).perform();
        WebElement macSubMenu=driver.findElement(By.xpath("//a[normalize-space()='Mac (1)']"));
        wait.until(ExpectedConditions.visibilityOf(macSubMenu));
        act.moveToElement(macSubMenu).perform();
        act.click().perform();
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
