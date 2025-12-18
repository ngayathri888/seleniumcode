//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package linksexamples;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrokenLinksTest {
    static WebDriver driver = null;

    public BrokenLinksTest() {
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000L));
        driver.get("https://testautomationpractice.blogspot.com/");
        wait.until(ExpectedConditions.titleContains("Automation Testing Practice"));
        List<WebElement> linksList = driver.findElements(By.tagName("a"));

        try {
            for(int i = 1; i < linksList.size(); ++i) {
                System.out.println("***********************");
                String url = ((WebElement)linksList.get(i)).getDomAttribute("href");
                if (!url.contains("#")) {
                    validateUrl(url);
                }
            }
        } catch (IndexOutOfBoundsException var5) {
            IndexOutOfBoundsException indx = var5;
            indx.printStackTrace();
            System.out.println(indx.getMessage());
        }

    }

    private static void validateUrl(String url) throws IOException {
        URL ul = new URL(url);
        HttpURLConnection hc = (HttpURLConnection)ul.openConnection();
        hc.connect();
        int statusCode = hc.getResponseCode();
        String respMsg = hc.getResponseMessage();
        if (statusCode == 200) {
            System.out.println(url + "is working fine:" + respMsg + " ::" + statusCode);
        } else if (statusCode >= 400) {
            System.out.println(url + "is not working/broken:" + respMsg + "::" + statusCode);
        }

        hc.disconnect();
    }
}

