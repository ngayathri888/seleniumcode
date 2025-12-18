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
import java.util.List;

public class HerokuAppFramesTest {
    static WebDriver driver = null;
    static WebDriverWait wait = null;
    @BeforeAll
    public static void launchApplication() {
        //launch the chrome browser
        driver = new ChromeDriver();
        //maximize the window
        driver.manage().window().fullscreen();
        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //launch the url
        driver.get("https://the-internet.herokuapp.com/");
        //add explicitwait
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        wait.until(ExpectedConditions.titleIs("The Internet"));

        //validate the page title
        Assertions.assertEquals(driver.getTitle(), "The Internet");

        //click on frames
        driver.findElement(By.linkText("Frames")).click();


    }

    @Test
    public void framesTest() throws InterruptedException {
        System.out.println("started executing the frames Test...");
        wait.until(ExpectedConditions.urlContains("/frames"));
        WebElement framesPageHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='example'] h3")));
        Assertions.assertEquals(framesPageHeading.getText(), "Frames");

        //click on Nested Frames
        driver.findElement(By.linkText("Nested Frames")).click();
        wait.until(ExpectedConditions.urlContains("/nested_frames"));
        //fetch number of frames in the webpage
        List<WebElement>frmsList=driver.findElements(By.tagName("frame"));


        System.out.println("number of frames:"+frmsList.size());
        //switch to the top frames
        driver.switchTo().frame("frame-top");
        //fetch all the childframes in the webpage
        List<WebElement>childfrmsList =driver.findElements(By.tagName("frame"));
        System.out.println("child frames list is:"+childfrmsList.size());
        //switch to the middle frame
        driver.switchTo().frame("frame-middle");

        //fetch the MIDDLE text
        String middleText=driver.findElement(By.id("content")).getText();
        Assertions.assertEquals(middleText,"MIDDLE");
        //switch back to previous frame
        driver.switchTo().defaultContent();

        //navigate to previous page
        driver.navigate().back();


    }

    @AfterAll
    public static void closeBrowser() {
        //close the webdriver
        driver.quit();

    }


}

