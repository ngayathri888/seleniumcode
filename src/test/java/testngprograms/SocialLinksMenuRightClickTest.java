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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class SocialLinksMenuRightClickTest {
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

    @Test(description = "context click and Menu test")
    public void contextClickMenuTest(){
        Reporter.log("@Test  ",true);
        Reporter.log("launch the application",true);
        driver.get("https://qaplayground.dev/apps/context-menu/");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle("Right-Click Context Menu");
        Reporter.log("fetch all the elements into list collection",true);
       List<WebElement> elements=driver.findElements(By.cssSelector("ul.share-menu li span"));
       Reporter.log("iterate all the elements",true);
       Actions act=new Actions(driver);
       for(WebElement element:elements){
           //perform the right click operation on the page
           act.contextClick(driver.findElement(By.tagName("body"))).perform();
           //move the cursor to share element
           act.moveToElement(driver.findElement(By.cssSelector("li.menu-item.share"))).perform();
           //click on each element
           element.click();
           String msgLocator="p#msg";
           wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(msgLocator)));
           if(element.getText().equalsIgnoreCase("Twitter")){
               String twitterMsg=driver.findElement(By.cssSelector(msgLocator)).getText();
               String expMsg="Menu item Twitter clicked";
               Assert.assertEquals(twitterMsg,expMsg);
               System.out.println("Twitter link is verified");
           }else if(element.getText().equalsIgnoreCase("Instagram")){
               String twitterMsg=driver.findElement(By.cssSelector(msgLocator)).getText();
               String expMsg="Menu item Instagram clicked";
               Assert.assertEquals(twitterMsg,expMsg);
               System.out.println("Instagram link is verified");
           }else if(element.getText().equalsIgnoreCase("Dribble")){
               String twitterMsg=driver.findElement(By.cssSelector(msgLocator)).getText();
               String expMsg="Menu item Dribble clicked";
               Assert.assertEquals(twitterMsg,expMsg);
               System.out.println("Dribble link is verified");
           }else if(element.getText().equalsIgnoreCase("Telegram")){
               String twitterMsg=driver.findElement(By.cssSelector(msgLocator)).getText();
               String expMsg="Menu item Telegram clicked";
               Assert.assertEquals(twitterMsg,expMsg);
               System.out.println("Telegram link is verified");
           }
       }


    }


    private void verifyHomePageTitle(String exptitle){
        wait.until(ExpectedConditions.titleIs(exptitle));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,exptitle);

    }

}
