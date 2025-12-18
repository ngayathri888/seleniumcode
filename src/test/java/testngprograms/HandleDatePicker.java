package testngprograms;

import org.junit.Assert;
import org.openqa.selenium.By;
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

public class HandleDatePicker {
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
    public void handleDatePickerTest() throws InterruptedException {
        Reporter.log("@Test  ",true);
        Reporter.log("launch the application",true);
        driver.get("https://jqueryui.com/datepicker/");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle("Datepicker | jQuery UI");
        Reporter.log("switch to the iframe",true);
        WebElement iframeElement=driver.findElement(By.className("demo-frame"));
        wutils.switchToFrame(iframeElement);
        Reporter.log("type the date value directly",true);
        WebElement datepickerEditbox=driver.findElement(By.id("datepicker"));
        wutils.setAttribute(datepickerEditbox,"value","01/11/2025");
        //datepickerEditbox.sendKeys("01/12/2025");
        datepickerEditbox.click();
        String targetMonth = "April";
        String targetYear = "2025";
        String targetDate = "25";
        selectMonthYear(targetMonth,targetYear);
        selectDate(targetDate);

       // selectDate("15");
        Thread.sleep(5000);

    }

    private void selectDate(String day){
        Reporter.log("wait for the calander:",true);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ui-datepicker-div")));
        Reporter.log("identify the table:",true);
       WebElement table=driver.findElement(By.className("ui-datepicker-calendar"));
        Reporter.log("fetch all the rows:",true);
        List<WebElement>tableRows=table.findElements(By.xpath("//tr"));
        Reporter.log("iterate the rows:",true);
        for(WebElement row:tableRows){
            //fetch all the columns for each row
            List<WebElement>cells=row.findElements(By.xpath("//td"));
            for(WebElement cell:cells){
                if(cell.getText().equals(day)){
                    driver.findElement(By.linkText(day)).click();
                }
            }


        }
        //switch back to normal
        wutils.switchtoDefaultFrame();

    }
    private void selectMonthYear(String expmonth,String expyear){
        while(true){
            String actMonthYear=driver.findElement(By.cssSelector(".ui-datepicker-title")).getText();
            //String actYear=driver.findElement(By.cssSelector(".ui-datepicker-year")).getText();

            if(actMonthYear.contains(expmonth)&&actMonthYear.contains(expyear)){
                break;

            }
            //click on next button
            driver.findElement(By.cssSelector(".ui-datepicker-next.ui-corner-all")).click();
        }
    }





    private void verifyHomePageTitle(String exptitle){
        wait.until(ExpectedConditions.titleIs(exptitle));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,exptitle);

    }
}
