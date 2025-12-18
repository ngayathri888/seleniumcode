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

public class HandleWebTables {
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
    public void handleWebTablesTest() throws InterruptedException {
        Reporter.log("@Test  ",true);
        Reporter.log("launch the application",true);
        driver.get("https://the-internet.herokuapp.com/tables");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle("The Internet");

        Reporter.log("wait for the web tables header:",true);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.example >h3")));
        Reporter.log("fetch all column headings:",true);
        List<WebElement>columnHeadings=driver.findElements(By.xpath("//table[@id='table1']//thead/tr/th"));
        Reporter.log("no of columns in the headings"+columnHeadings.size(),true);
        for(WebElement col:columnHeadings){

            Reporter.log("colum heading name is:"+col.getText());

        }
       //fetch all the rows in the table
        List<WebElement>rowsList=driver.findElements(By.xpath("//table[@id='table1']/tbody/tr"));
        Reporter.log("no of rows in table:"+rowsList.size());
        for(WebElement row:rowsList){
            Reporter.log("***********",true);

            //fetch all the cels for each row
            List<WebElement>columsList=row.findElements(By.xpath("//table[@id='table1']/tbody/tr/td"));
            for(WebElement cell:columsList){
                Reporter.log(cell.getText(),true);

            }
        }
        Reporter.log("***********using normal for loop***********",true);
        for(int i=1;i<=rowsList.size();i++){
            Reporter.log("***********",true);
            //fetch all the cels for each row
            List<WebElement>columsList=driver.findElements(By.xpath("//table[@id='table1']/tbody/tr[1]/td"));
            Reporter.log("cell size is:"+columsList.size());
            for(int j=1;j<columsList.size();j++){
                Reporter.log(i+" th row "+j+"column cell data:"+getCellData(i,j),true);
            }

        }

    }
public String getCellData(int row,int col){
        //parametrization of xpaths
        return  driver.findElement(By.xpath("//table[@id='table1']/tbody/tr["+row+"]/td["+col+"]")).getText();
}

    private void verifyHomePageTitle(String exptitle){
        wait.until(ExpectedConditions.titleIs(exptitle));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,exptitle);

    }
}
