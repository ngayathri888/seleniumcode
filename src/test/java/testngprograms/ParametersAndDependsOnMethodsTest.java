package testngprograms;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ParametersAndDependsOnMethodsTest {
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

    @Test(description = "homepage test")
    public void homePageTest(){
        Reporter.log("@Test https://demo.opencart.com/ ",true);
        Reporter.log("launch the application",true);
        driver.get("https://tutorialsninja.com/demo/index.php");
        Reporter.log("wait for the page title",true);
        verifyHomePageTtitle();
        Reporter.log("click on my account menu");
        WebElement myAccountMenu=driver.findElement(By.xpath("(//div[@id='top-links']/ul/li[2]/a/span)[1]"));
        JavascriptExecutor jsx=(JavascriptExecutor)driver;
        jsx.executeScript("arguments[0].click();",myAccountMenu);
        Reporter.log("wait for the login link is visible",true);
        WebElement loginLink=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Login']")));
        Reporter.log("click on login link ",true);
        loginLink.click();
        wait.until(ExpectedConditions.titleContains("Account Login"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[normalize-space()='Returning Customer']")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Returning Customer']")));
    }


    @Parameters({"uname","pwd"})
    @Test(description="login to the app",dependsOnMethods = {"homePageTest"})
    public void doLoginTest(String uname,String pwd) throws InterruptedException {
        Reporter.log("@Test:do login Test", true);
        WebElement emailEditbox = driver.findElement(By.id("input-email"));
        Reporter.log("clear the content in Emaileditbox", true);
        emailEditbox.clear();
        Reporter.log("type the content in Emaileditbox", true);
        emailEditbox.sendKeys(uname);

        WebElement pwdEditbox = driver.findElement(By.name("password"));
        Reporter.log("clear the content in passwordeditbox", true);
        pwdEditbox.clear();
        Reporter.log("type the content in pwdeditbox", true);
        pwdEditbox.sendKeys(pwd);

        Reporter.log("click on login button");
        driver.findElement(By.cssSelector("input[value='Login']")).click();
        wait.until(ExpectedConditions.titleIs("My Account"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='My Account']")));

    }
    // @Test(description="fetch all buttons",priority=1,enabled=false,groups={"regression"})
    @Test(description="dologout test",dependsOnMethods = {"homePageTest"})
    public void doLogoutTest() throws InterruptedException {
        Reporter.log("@Test:doLogoutTest", true);
        Reporter.log("click on my account menu", true);
        WebElement myAccounMenu =wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//span[normalize-space()='My Account'])[1]")));
        myAccounMenu.click();

        Reporter.log("click on logout link under my account menu", true);
        WebElement logoutLink=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[normalize-space()='Logout'])[1]")));
        logoutLink.click();
        Reporter.log("wait for the account logout title", true);
        wait.until(ExpectedConditions.titleIs("Account Logout"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='content'] h1")));
        Reporter.log("click on continue button", true);
        driver.findElement(By.cssSelector(".btn.btn-primary")).click();
        //wait.until(ExpectedConditions.titleIs("Account Logout"));
        verifyHomePageTtitle();


        }
        private void verifyHomePageTtitle(){
            wait.until(ExpectedConditions.titleIs("Your Store"));
            String title=driver.getTitle();
            Reporter.log("current page title is:"+title,true);
            Assert.assertEquals(title,"Your Store");

        }

    }



