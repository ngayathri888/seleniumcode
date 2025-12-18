package testngprograms;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class GoogleSugestionsTest {
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

    @Test(description = "google suggestions test")
    public void homePageTest(){
        Reporter.log("@Test https://google.com/ ",true);
        Reporter.log("launch the application",true);
        driver.get("https://google.com/");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle();
        Reporter.log("Type the value in search editbox",true);
        String keyword="selenium";
        WebElement searchEditbox=driver.findElement(By.name("q"));
        searchEditbox.clear();
        searchEditbox.sendKeys(keyword);
        Reporter.log("wait for the suggestions visible",true);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']/li")));
        List<WebElement>suggestions=driver.findElements(By.xpath("//ul[@role='listbox']/li"));
        boolean isAvailable=false;
        for(WebElement sugg:suggestions){
            String suggestionText=sugg.getText();
            Reporter.log("sugggestions:"+suggestionText);
            if(suggestionText.contains(keyword)){
                isAvailable = true;
                Assert.assertTrue("Typed keyword is not present in suggestionlist", isAvailable);

            }
        }

    }


    private void verifyHomePageTitle(){
        wait.until(ExpectedConditions.titleIs("Google"));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,"Google");

    }

}
