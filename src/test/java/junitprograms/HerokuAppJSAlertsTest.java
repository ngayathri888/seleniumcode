package junitprograms;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HerokuAppJSAlertsTest {
    static WebDriver driver=null ;
    static WebDriverWait wait = null;
    @BeforeAll
    public static void launchApplication() {
        //launch the chrome browser
        driver = new ChromeDriver();
        //maximize the window
        driver.manage().window().fullscreen();
        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10));
        //launch the url
        driver.get("https://the-internet.herokuapp.com/");
        //add explicitwait
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        wait.until(ExpectedConditions.titleIs("The Internet"));

        //validate the page title
        Assertions.assertEquals(driver.getTitle(), "The Internet");

        //click on javascript alerts
        driver.findElement(By.linkText("JavaScript Alerts")).click();


    }
    @BeforeEach
    public void setUp() {
        wait.until(ExpectedConditions.urlContains("/javascript_alerts"));
        WebElement alertPageHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='example'] h3")));
        Assertions.assertEquals(alertPageHeading.getText(), "JavaScript Alerts");
        WebElement subHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='example']/p)[1]")));
        Assertions.assertTrue(subHeader.isDisplayed(),"subheader text is not present");

    }
    @Test
    public void simpleAlertTest() throws InterruptedException {
        System.out.println("started executing the simple alertTest...");
        //click on Jsalert button
        driver.findElement(By.cssSelector("button[onclick='jsAlert()']")).click();
        wait.until(ExpectedConditions.alertIsPresent());

        //switch to alert
        Alert alt=driver.switchTo().alert();
        System.out.println("alert message is:"+alt.getText());
        //click on ok button
        alt.accept();
        verifyMessages("You successfully clicked an alert");

    }

    @Test
    public void confirmationAlertTest(){
        System.out.println("started executing the confirmationAlertTest...");
        //click on confirm alert button
        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Confirm']")).click();
        Alert conf=driver.switchTo().alert();
        System.out.println("confirmation message is:"+conf.getText());
        //click on ok button
        conf.accept();
        verifyMessages("You clicked: Ok");
        //click on confirm alert button
        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Confirm']")).click();
        Alert conf1=driver.switchTo().alert();
        System.out.println("confirmation message is:"+conf.getText());
        //click on cancel button
        conf.dismiss();
        verifyMessages("You clicked: Cancel");

    }
    @Test
    public void promptAlertTest(){
        System.out.println("started executing the promptAlertTest...");
        //click on confirm alert button
        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Prompt']")).click();
        Alert prompt=driver.switchTo().alert();
        System.out.println("confirmation message is:"+prompt.getText());

        //type the value in editbox
        prompt.sendKeys("Webdriver");

        //click on ok button
        prompt.accept();
        verifyMessages("You entered: Webdriver");
        //click on confirm alert button
        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Prompt']")).click();
        Alert prompt1=driver.switchTo().alert();
        System.out.println("confirmation message is:"+prompt1.getText());
        prompt1.sendKeys("Test");
        //click on cancel button
        prompt1.dismiss();
        verifyMessages("You entered: null");
    }
    @AfterEach
    public void tearDemo() {
        //clear the cookies
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    public static void closeBrowser() {
        //close the webdriver
        driver.quit();

    }
    private void verifyMessages(String expectedMsg){
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='result']")));
        Assertions.assertTrue(result.getText().trim().contains(expectedMsg));

    }




}

