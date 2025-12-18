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

public class FileUploadUsingRobot {
    private WebDriver driver=null;
    private WebDriverWait wait= null;
    private WebDriverUtils wutils;

    String filepath="C:\\Users\\Admin\\Desktop\\data.xlsx";

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
    public void easyUploadTest() throws InterruptedException {
        Reporter.log("@Test  ",true);
        Reporter.log("launch the application",true);
        driver.get("https://easyupload.io/");
        Reporter.log("wait for the page title",true);
        verifyHomePageTitle("Upload Files | Free File Upload and Transfer Up To 10 GB");

        Reporter.log("wait for the upload files:",true);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='upload'] h1")));

        Reporter.log("click on filechooser..click here or drop files to upload or transfer",true);
        driver.findElement(By.xpath("//*[@id='dropzone']/div[2]/button")).click();
        Reporter.log("call the robote class method to handle file upload");
        wutils.uploadFileWithRobot(filepath);
        WebElement uploadBtn=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("upload")));
        uploadBtn.click();
        WebElement uploadSucMsg=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[@id='upload-label-1']")));
        Assert.assertEquals(uploadSucMsg.getText(),"Upload Success!");


}

    private void verifyHomePageTitle(String exptitle){
        wait.until(ExpectedConditions.titleIs(exptitle));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,exptitle);

    }
}
