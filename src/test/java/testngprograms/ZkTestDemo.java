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

public class ZkTestDemo {
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

    @BeforeMethod(alwaysRun = true)
    public void launchApplication(){
        Reporter.log("@BeforeMethod each test before",true);
        Reporter.log("launch the application",true);
        driver.get("https://www.zkoss.org/zkdemo/input");
        Reporter.log("wait for the page title",true);
        wait.until(ExpectedConditions.titleIs("ZK Live Demo - Input"));
        String title=driver.getTitle();
        Reporter.log("current page title is:"+title,true);
        Assert.assertEquals(title,"ZK Live Demo - Input");
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies(){
        Reporter.log("@AfterMethod each test after",true);
        Reporter.log("clear the cookies after each @Test method",true);
        driver.manage().deleteAllCookies();
    }

    @Test(description="xpost popup handling",priority=1,groups={"regression"})
    public void xpostPopupTest() throws InterruptedException {
        Reporter.log("@Test:xpostPopupTest", true);
        //String linkName = "Date and Time Picker";
        navigateToControls("Date and Time Picker");
        Reporter.log("switch to the iframe");
        driver.switchTo().frame(driver.findElement(By.id("twitter-widget-0")));
        WebElement twitterLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'tweet')]")));
        twitterLink.click();
        Reporter.log("Fetch all the window", true);
        //how to handle multiple windows:getWindowHandles()
        Set<String> handles = driver.getWindowHandles();
        //print the all window handles
        Reporter.log("all the window ids are" + handles, true);
        //iterate all the handles
        Iterator<String> it = handles.iterator();
        String pid = it.next();
        Reporter.log("parent window id:" + pid, true);
        String chid = it.next();
        Reporter.log("child window id:" + chid, true);
        Reporter.log(" switching to child window ", true);
        driver.switchTo().window(chid);
        try {
            WebElement loginBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Log in'])[1]")));
            Reporter.log("click on login button", true);
            //loginBtn.click();
            JavascriptExecutor jsx = (JavascriptExecutor) driver;
            jsx.executeScript("arguments[0].click();", loginBtn);
        } catch (ElementClickInterceptedException ex) {
            ex.printStackTrace();
        }
        Reporter.log("wait for the sign in to x header", true);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//span[text()='Sign in to X'])[1]")));
        Reporter.log("type the username in editbox", true);
        driver.findElement(By.xpath("//input[@autocomplete='username']")).sendKeys("ngayathri888");
        Reporter.log("close the chaild window");
        driver.close();
        Reporter.log("switch back to parent window");
        driver.switchTo().window(pid);
        driver.switchTo().defaultContent();


        WebElement dateEditbox = driver.findElement(By.className("z-datebox-input"));
        dateEditbox.clear();
        String expectedvalueToInput = "12/30/2024";
        dateEditbox.sendKeys(expectedvalueToInput);
        dateEditbox.sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        Reporter.log("fetch the typed value from editbox", true);
        String typedValue = dateEditbox.getDomAttribute("value");
        Reporter.log("typed value:" + typedValue, true);
        // Assert.assertEquals(expectedvalueToInput, typedValue);


    }
    //@Test(description="fetch all buttons",priority=1,enabled=false,groups={"smoke"})
    @Test(description="fetch all buttons",priority=1,groups={"smoke"})
    public void buttonTest() throws InterruptedException {
        Reporter.log("@Test:Buttontest", true);
        //String linkName = "Button";
        navigateToControls("Button");
        Reporter.log("fetch all the buttons",true);
        List<WebElement> btnsList=driver.findElements(By.className("z-button"));
        Reporter.log("number of buttons:"+btnsList.size());
        for(int i=0;i<btnsList.size();i++){
            Reporter.log(btnsList.get(i).getText());

        }
    }
    // @Test(description="fetch all checkboxes",priority=1,timeOut = 2000)
    @Test(description="fetch all checkboxes",priority=1,groups={"smoke"})
    public void checkboxTest() throws InterruptedException {
        Reporter.log("@Test:checkboxtest", true);
        //String linkName ="Checkbox" ;
        navigateToControls("Checkbox");
        Reporter.log("fetch all the checkboxes",true);
        List<WebElement>checkboxesList=driver.findElements(By.xpath("//input[@type='checkbox']"));
        Reporter.log("number of checkboxes:"+checkboxesList.size());
        WebElement selectedCheckboxes=null;
        for(int i=0;i<checkboxesList.size();i++){
            if(!checkboxesList.get(i).isSelected()){
                checkboxesList.get(i).click();
            }
            selectedCheckboxes= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'You have selected')]/following::span[1]")));

        }
        Reporter.log("fetch all the checkboxes which are selected:"+selectedCheckboxes.getText());

    }
    @Test(description="select radio buttons",priority=1,invocationCount=3,groups={"regression"})
    public void radioBtnTest() throws InterruptedException {
        Reporter.log("@Test:radioBtnTest", true);

        navigateToControls("Radio Button");
        Reporter.log("fetch all the radiobuttons",true);
        //select performance radio button
        driver.findElement(By.xpath("//label[text()='Performance']/preceding::input[1]")).click();
        WebElement featureselectedRadioBtn= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Feature')]/following::span[1]")));
        Reporter.log("fetch selected Feature value:"+featureselectedRadioBtn.getText());

        //select Forum radio button
        driver.findElement(By.xpath("//label[text()='Forum']/preceding::input[1]")).click();
        WebElement forumselectedRadioBtn= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Web Site')]/following::span[1]")));
        Reporter.log("fetch selected Feature value:"+forumselectedRadioBtn.getText());

        //select developer guide radio button
        driver.findElement(By.xpath("//label[text()='Developer Guide']/preceding::input[1]")).click();
        WebElement documentselectedRadioBtn= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Documentation')]/following::span[1]")));
        Reporter.log("fetch selected document value:"+documentselectedRadioBtn.getText());

    }

    private void navigateToControls(String linkName){
        Reporter.log("click on " + linkName + " link in left panel", true);
        driver.findElement(By.linkText(linkName)).click();
        wait.until(ExpectedConditions.titleContains("ZK Live Demo - "+linkName));
        Assert.assertEquals(driver.getTitle(), "ZK Live Demo - "+linkName);
        Reporter.log("verify the breadcrumb", true);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("breadCrumb")));
        WebElement breadCrumbText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//nav[@id='breadCrumb']/a[2]")));
        Assert.assertEquals(breadCrumbText.getText(), linkName);
    }
}
