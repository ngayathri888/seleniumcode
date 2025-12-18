package junitprograms;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RediffInternalFrameTest {
    static WebDriver driver=null ;
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
        driver.get("https://www.rediff.com/");
        //add explicitwait
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        wait.until(ExpectedConditions.titleContains("Rediffmail"));

        //validate the page title
        Assertions.assertTrue(driver.getTitle().contains("Rediffmail"));

    }

    @Test
    public void internalframeTest() throws InterruptedException {
        System.out.println("started executing the frames Test...");

        //fetch number of frames in the webpage
        List<WebElement>internalfrmsList = driver.findElements(By.tagName("iframe"));

        System.out.println("number of internal frames:"+internalfrmsList.size());
        //switch to the internal frames
        WebElement iframeElement = driver.findElement(By.id("moneyiframe"));
        driver.switchTo().frame(iframeElement);

        //type the value in search editbox
        driver.findElement(By.className("getquoteinput")).sendKeys("DSP BlackRock");
        Thread.sleep(5000);
    }

    @AfterAll
    public static void closeBrowser() {
        //close the webdriver
        driver.quit();

    }
}

