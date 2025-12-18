package testngprograms;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
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

import java.io.IOException;
import java.time.Duration;

public class ShadowRootDemoTest {
    private WebDriver driver=null;
    private WebDriverWait wait= null;
    private WebDriverUtils wutils;



    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void launchBrowser(@Optional("chrome") String browser){
        Reporter.log("@BeforeClass annotation will be executed before any test inside the current class", true);
        if(browser.equalsIgnoreCase("chrome")) {
            Reporter.log("launching chrome browser", true);
            ChromeOptions options=new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("diable-infobars");
            options.addArguments("---disable-extensions");
            //options.addArguments("---headless");
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
        wutils=new WebDriverUtils(driver);

    }
    @AfterClass(alwaysRun = true)
    public void closeBrowser(){
        Reporter.log("closing the browser",true);
        if(driver!=null){
            driver.quit();
        }
    }

    @Test
    public void shadowRootTest(@Optional("chrome") String browser) throws InterruptedException, IOException {
        Reporter.log("@Test  ",true);
        Reporter.log("launch the application",true);
        driver.get("chrome://downloads/");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle("Download history");
        Reporter.log("wait for the upload files:",true);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='upload'] h1")));

        Reporter.log("find the shadow root1 element",true);
        WebElement root1=driver.findElement(By.cssSelector("downloads-manager"));
        Reporter.log("get the shadowroot element using getShadowRoot() ");
        SearchContext shadowRoot1=root1.getShadowRoot();

        Reporter.log("find the shadow root1 element",true);
        WebElement root2=shadowRoot1.findElement(By.cssSelector("downloads-toolbar"));
        Reporter.log("get the shadowroot element using getShadowRoot() ");
        SearchContext shadowRoot2=root2.getShadowRoot();

        Reporter.log("find the shadow root1 element",true);
        WebElement root3=shadowRoot2.findElement(By.cssSelector("cr-infinite-list"));
        Reporter.log("get the shadowroot element using getShadowRoot() ");
        SearchContext shadowRoot3=root3.getShadowRoot();

        WebElement root4=shadowRoot3.findElement(By.cssSelector("cr-lazy-list"));
        Reporter.log("get the shadowroot element using getShadowRoot() ");
        SearchContext shadowRoot4=root4.getShadowRoot();
        shadowRoot4.findElement(By.id("searchInput")).sendKeys("autoit-v3-setup.zip");
        Thread.sleep(5000);



}

    private void verifyHomePageTitle(String exptitle){
        wait.until(ExpectedConditions.titleIs(exptitle));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,exptitle);

    }
}
