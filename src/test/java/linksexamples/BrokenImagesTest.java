//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package linksexamples;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrokenImagesTest {
    static WebDriver driver = null;

    public BrokenImagesTest() {
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        new WebDriverWait(driver, Duration.ofMillis(30000L));
        driver.get("https://ecommerce-playground.lambdatest.io/index.php?oute=common/home");
        List<WebElement> imagesList = driver.findElements(By.tagName("img"));

        try {
            for(int i = 1; i < imagesList.size(); ++i) {
                System.out.println("***********************");
                String url = ((WebElement)imagesList.get(i)).getDomAttribute("src");
                if (url.startsWith("https") || url.startsWith("https")) {
                    validateUrl(url);
                }
            }
        } catch (IndexOutOfBoundsException var5) {
            System.out.println("in IndexoutofboundsException catch block");
            var5.printStackTrace();
            System.out.println(var5.getMessage());
        } catch (MalformedURLException var6) {
            System.out.println("in MalformedURLException catch block");
            var6.printStackTrace();
        } catch (Exception var7) {
            Exception ex = var7;
            ex.printStackTrace();
        }

        driver.quit();
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

