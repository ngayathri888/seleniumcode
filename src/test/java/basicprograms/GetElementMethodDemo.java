//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package basicprograms;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetElementMethodDemo {
    static WebDriver driver = null;

    public GetElementMethodDemo() {
    }

    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000L));
        driver.get("https://www.rediff.com/");
        wait.until(ExpectedConditions.titleContains("Rediffmail"));
        String pgTitle = driver.getTitle();
        System.out.println("current page title:+pgTitle");
        String firstHdlineText = driver.findElement(By.xpath("//div[@class='col_text']/h2[1]/a")).getText();
        WebElement createAccLink = driver.findElement(By.linkText("Create Account"));
        String tooltipprop = createAccLink.getDomProperty("title");
        System.out.println("tolltip by dom property function is:" + tooltipprop);
        String tooltipAttr = createAccLink.getDomProperty("title");
        System.out.println("tolltip by attribute is:" + tooltipAttr);
        String url = createAccLink.getDomAttribute("href");
        System.out.println("url of the creataccount:" + url);
        WebElement logo = driver.findElement(By.cssSelector("img[alt='logo"));
        String logoColor = logo.getCssValue("color");
        System.out.println("color ofthe logo is:" + logoColor);
        String fSize = logo.getCssValue("font-Size");
        System.out.println("font-size of the logo is:" + fSize);
        String fFamily = logo.getCssValue("font-family");
        System.out.println("font-family of the logo is:" + fFamily);
        System.out.println("logo x coordinate is:" + logo.getRect().getX());
        System.out.println("logo x coordinate is:" + logo.getRect().getY());
        System.out.println("logo height is:" + logo.getRect().getHeight());
        System.out.println("logo width is:" + logo.getRect().getWidth());
        System.out.println("logo tag name:" + logo.getTagName());
        driver.quit();
    }
}

