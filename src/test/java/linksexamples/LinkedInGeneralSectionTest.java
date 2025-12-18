//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package linksexamples;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LinkedInGeneralSectionTest {
    static WebDriver driver = null;

    public LinkedInGeneralSectionTest() {
    }

    public static void main(String[] args) throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000L));
        driver.get("https://www.linkedin.com/");
        wait.until(ExpectedConditions.titleContains("LinkedIn: Log In or Sign Up"));
        List<WebElement> genSecLinksList = driver.findElements(By.xpath("//div[contains(@class,'w-full flex justify-end pl')]/div[1]/ul/li/a"));
        String startXpath = "//div[contains(@class,'w-full flex justify-end pl')]/div[1]/ul/li[";
        String endXpath = "]/a";

        for(int i = 1; i <= genSecLinksList.size(); ++i) {
            System.out.println("***********************");
            String linkName = driver.findElement(By.xpath(startXpath + i + endXpath)).getText();
            System.out.println("link name is:" + linkName);
            String url = driver.findElement(By.xpath(startXpath + i + endXpath)).getDomAttribute("href");
            System.out.println(linkName + "url is:" + url);
            driver.findElement(By.xpath(startXpath + i + endXpath)).click();
            Thread.sleep(2000L);
            System.out.println(linkName + "independent title is:" + driver.getTitle());
            Thread.sleep(2000L);
            driver.navigate().back();
        }

        driver.quit();
    }
}

