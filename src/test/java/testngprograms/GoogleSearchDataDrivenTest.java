package testngprograms;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;

public class GoogleSearchDataDrivenTest {

    private WebDriver driver=null;
    private WebDriverWait wait= null;

    @BeforeSuite
    public void beforeSuite() {
        Reporter.log("@BeforeSuite annotation will be executed before all the tests inside suite", true);
    }

    @AfterSuite
    public void afterSuite() {
        Reporter.log("@AfterSuite annotation will be executed after all the tests inside the suite", true);
    }

    @BeforeTest
    public void beforeTest(){
        Reporter.log("@BeforeTest annotation will be executed before any test inside the tag have run", true);
        Reporter.log("this is  test tag level annotation",true);

    }
    @AfterTest
    public void afterTest(){
        Reporter.log("@AfterTest annotation will be executed after all tests inside the tag have run", true);
        Reporter.log("this is test tag level annotation",true);

    }
    @BeforeClass
    public void launchBrowser(){
        Reporter.log("@BeforeClass annotation will be executed before any test inside the current class", true);
        Reporter.log("launching chrome browser",true);
        driver=new ChromeDriver();
        Reporter.log("maximize the chrome browser",true);
        driver.manage().window().maximize();
        Reporter.log("add implicitWait",true);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Reporter.log("add explicitWait",true);
        wait=new WebDriverWait(driver,Duration.ofMillis(30000));

    }
    @AfterClass
    public void closeBrowser(){
        Reporter.log("closing the browser",true);
        if(driver!=null){
            driver.quit();
        }
    }

    @BeforeMethod
    public void launchApplication(){
        Reporter.log("@BeforeMethod each test before",true);
        Reporter.log("launch the application",true);
        driver.get("https://www.google.com/");
        Reporter.log("wait for the page title",true);
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        wait.until(ExpectedConditions.titleIs("Google"));
        Assert.assertEquals(title,"Google");
    }
    @AfterMethod
    public void clearCookies(){
        Reporter.log("@AfterMethod each test after",true);
        Reporter.log("clear the cookies after each @Test method",true);
        driver.manage().deleteAllCookies();
    }
    @Test(dataProvider="googleData")
    public void searchTest(String keyword){
        Reporter.log("@Test:",true);
        Reporter.log("search keyword is:"+keyword,true);
        Reporter.log("Type the keyword in search editbox",true);
        WebElement searchEditbox=driver.findElement(By.name("q"));
        searchEditbox.clear();
        searchEditbox.sendKeys(keyword);
        Reporter.log("press enter key",true);
        searchEditbox.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.titleIs(keyword+ " - Google Search"));


    }
    @DataProvider
    public Object[][] googleData(){
        Object[][] data=new Object[3][1];
        //first row
        data[0][0]="Selenium";
        //2nd row
        data[1][0]="plywright";
        //3rd row
        data[2][0]="Cypress";
        return data;

    }
}


















