//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package basicprograms;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScrollAndTakesScreenshot {
    static WebDriver driver = null;

    public ScrollAndTakesScreenshot() {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000L));
        driver.get("https://www.amazon.in/");
        wait.until(ExpectedConditions.titleContains("Amazon"));
        WebElement searchEditbox = driver.findElement(By.id("twotabsearchtextbox"));
        searchEditbox.sendKeys(new CharSequence[]{"iphone 15"});
        searchEditbox.sendKeys(new CharSequence[]{Keys.ENTER});
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'s-breadcrumb')]/div/h2")));
        wait.until(ExpectedConditions.titleContains("Amazon.in : iphone 15"));
        captureScreenshot("AfterSearch");
        JavascriptExecutor jsx = (JavascriptExecutor)driver;
        jsx.executeScript("window.scrollBy(0,5000)", new Object[]{""});
        captureScreenshot("AfterScrollDown");
        jsx.executeScript("window.scrollBy(0,-4000)", new Object[]{""});
        captureScreenshot("AfterScrollup");
        WebElement backToTop = driver.findElement(By.cssSelector("#navBackToTop"));
        jsx.executeScript("arguments[0].scrollIntoView(true);", new Object[]{backToTop});
        captureScreenshot("AfterScrollToTop");
        backToTop.click();
        jsx.executeScript("arguments[0].click()", new Object[]{backToTop});
        captureScreenshot("AfterScrollToTOP");
        Thread.sleep(2000L);
        WebElement amazonlogo = driver.findElement(By.id("nav-logo-sprites"));
        captureScreenshot(amazonlogo, "logoscreenshot");

        int i;
        for(i = 1; i < 10; ++i) {
            driver.findElement(By.tagName("body")).sendKeys(new CharSequence[]{Keys.DOWN});
        }

        captureScreenshot("scrollDownByKeys");
        Thread.sleep(2000L);

        for(i = 1; i < 10; ++i) {
            driver.findElement(By.tagName("body")).sendKeys(new CharSequence[]{Keys.UP});
        }

        captureScreenshot("scrollUPByKeys");
        Thread.sleep(2000L);
        driver.quit();
    }

    private static void captureScreenshot(String screenName) throws IOException {
        File src = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Date d = new Date();
        screenName = screenName + "-" + d.toString().replace(":", "-").replace(" ", "-") + ".jpg";
        String var10003 = System.getProperty("user.dir");
        FileUtils.copyFile(src, new File(var10003 + "\\src\\screenshots\\" + screenName));
    }

    private static void captureScreenshot(WebElement element, String screenName) throws IOException {
        File src = (File)element.getScreenshotAs(OutputType.FILE);
        Date d = new Date();
        screenName = screenName + "-" + d.toString().replace(":", "-").replace(" ", "-") + ".jpg";
        String var10003 = System.getProperty("user.dir");
        FileUtils.copyFile(src, new File(var10003 + "\\src\\screenshots\\" + screenName));
    }
}
