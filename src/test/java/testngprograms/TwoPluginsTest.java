package testngprograms;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class TwoPluginsTest {
    private WebDriver driver=null;
    private WebDriverWait wait= null;

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void launchBrowser(@Optional("chrome") String browser){
        Reporter.log("@BeforeClass annotation will be executed before any test inside the current class", true);
        if(browser.equalsIgnoreCase("chrome")) {
            Reporter.log("launching chrome browser", true);
            ChromeOptions options=new ChromeOptions();
            options.setAcceptInsecureCerts(true);
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

    @Test(description = "two plugs test")
    public void twoPlugsTest(){
        Reporter.log("@Test  ",true);
        Reporter.log("launch the application",true);
        driver.get("https://www.twoplugs.com/");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle("twoPLUGS - A plug for your Service and another for your Need");
        Reporter.log("click on live posting",true);
       driver.findElement(By.xpath("//a[normalize-space()='Live Posting']"));

        Reporter.log("wait for the suggestions visible",true);
        WebElement locationeditbox=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='autocomplete']")));
        locationeditbox.clear();
        locationeditbox.sendKeys("Toronto");
        String optionToSelect="Toronto, OH, USA";
        String suggestion;
        do{
            locationeditbox.sendKeys(Keys.ARROW_DOWN);
            suggestion=locationeditbox.getDomProperty("value");
            if(suggestion.equalsIgnoreCase(optionToSelect)){
                locationeditbox.sendKeys((Keys.ENTER));
                break;
            }
        }while(!suggestion.isEmpty());

    }


    private void verifyHomePageTitle(String exptitle){
        wait.until(ExpectedConditions.titleIs(exptitle));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,exptitle);

    }

}
