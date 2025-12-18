//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package basicprograms;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LocatorsDemo {
    public LocatorsDemo() {
    }

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://ecommerce-playground.lambdatest.io/index.php?oute=common/home");
        String str = driver.getPageSource();
        String pwinId = driver.getWindowHandle();
        System.out.println("current window id is:" + pwinId);
        Set<String> handles = driver.getWindowHandles();
        System.out.println("all the window ids:" + String.valueOf(handles));
        WebElement wishlistIcon = driver.findElement(By.id("entry_217825"));
        WebElement searchEditbox = driver.findElement(By.name("search"));
        WebElement searchButton = driver.findElement(By.className("type-text"));
        WebElement imacLink = driver.findElement(By.linkText("iMac"));
        WebElement htcTouchIdLink = driver.findElement(By.partialLinkText("HTC Touch HD"));
        WebElement blogMenu = driver.findElement(By.xpath("//ul[@class='navbar-nav horizontal']/li[3]/a"));
        driver.close();
    }
}
