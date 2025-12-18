//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package basicprograms;

import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstSeleniumTest {
    public FirstSeleniumTest() {
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        String pgTitle = driver.getTitle();
        System.out.println("current page title is:" + pgTitle);
        String absUrl = driver.getCurrentUrl();
        System.out.println("current page absolute url is:" + absUrl);
        String src = driver.getPageSource();
        String pwinId = driver.getWindowHandle();
        System.out.println("current window id is:" + pwinId);
        Set<String> handles = driver.getWindowHandles();
        System.out.println("all the window ids:" + String.valueOf(handles));
        driver.close();
    }
}

