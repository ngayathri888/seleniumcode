//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package basicprograms;

import java.io.PrintStream;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CookiesDemo {
    static WebDriver driver = null;

    public CookiesDemo() {
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
        Cookie ckObj = new Cookie("test", "hnagfeiqjte29iigwt");
        driver.manage().addCookie(ckObj);
        Set<Cookie> allCookiesCol = driver.manage().getCookies();
        System.out.println("no of cookies in the browser:" + allCookiesCol.size());
        Iterator var5 = allCookiesCol.iterator();

        while(var5.hasNext()) {
            Cookie ck = (Cookie)var5.next();
            System.out.println("*****************8");
            PrintStream var10000 = System.out;
            String var10001 = ck.getName();
            var10000.println(var10001 + ":::" + ck.getValue());
            var10000 = System.out;
            var10001 = ck.getDomain();
            var10000.println(var10001 + ":::" + ck.getPath());
            var10000 = System.out;
            var10001 = String.valueOf(ck.getExpiry());
            var10000.println(var10001 + ":::" + ck.getSameSite());
            driver.manage().deleteCookieNamed("test");
            driver.manage().deleteAllCookies();
            Set<Cookie> allCookiesCol1 = driver.manage().getCookies();
            System.out.println("no of cookies in the browser after delete all:" + allCookiesCol1.size());

        }
        driver.quit();
    }

}

