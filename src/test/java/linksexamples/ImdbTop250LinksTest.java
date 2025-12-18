package linksexamples;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Collections;
import java.util.List;

public class ImdbTop250LinksTest {
    static WebDriver driver = null;

    public static void main(String[] args) throws InterruptedException {
        //interface refver = new ChromeDriver();
        driver = new ChromeDriver();
        //maximize the window
        driver.manage().window().maximize();
        //open the rediff.com url in the browser
        driver.get("https://www.imdb.com/chart/top/");
        //fetch the page title
        String pgTitle = driver.getTitle();
        System.out.println("current page title is:" + pgTitle);
        //fetch current page url
        String abs = driver.getCurrentUrl();
        System.out.println("current page absolute url is:" + abs);
        //how can fetch current page window id
        String pid = driver.getWindowHandle();
        System.out.println("current page window id:" + pid);
        //fetch all 250 links into list
       // List<WebElement>top250LincksList = driver.findElements(By.xpath("//ul[contains(@class,'ipc-metadata-list')]/li[\"+i+\"]/div/div/div/div/div[2]/div[1]/a"));
        List<WebElement>top250LincksList = driver.findElements(By.xpath("//ul[contains(@class,'ipc-metadata-list')]/li/div/div/div/div/div[2]/div[1]/a"));
        /**
         * //ul[contains(@class,'ipc-metadata-list')]/li[1]/div/div/div/div/div[2]/div[1]/a
         * approach1)directly replace for loop i variable/any variable
         * //ul[contains(@class,'ipc-metadata-list')]//li["+i+"]/div/div/div/div/div[2]/div[1]/a
         * /approach 2: split the xpath and store in string variable
         * String xpath1 ="//ul[contains(@class,'ipc-metadata-list')]/li[";
         * String xpath2 ="]/div/div/div/div/div[2]/div[1]/a"
         * xpath1+i+xpath2=//ul[contains(@class,'ipc-metadata-list')]/li["+i+"]/div/div/div/div/div[2]/div[1]/a
         */
        String xpath1 = "//ul[contains(@class,'ipc-metadata-list')]/li[";
        String xpath2 = "]/div/div/div/div/div[2]/div[1]/a";
        //iterate the top 250 links
        for (int i = 1; i <top250LincksList.size()+1; i++) {
            System.out.println("print movie links length:" + top250LincksList.size());
            System.out.println("***************************************************");
            //fetch the movie name
            String movieName = driver.findElement(By.xpath(xpath1 + i + xpath2)).getText();
            System.out.println(i + " th movie name is:" + movieName);
            String movieUrl = driver.findElement(By.xpath(xpath1 + i + xpath2)).getDomAttribute("href");
            System.out.println(i + " th movie url is:" + movieUrl);

        }
            Thread.sleep(2000);
            //close the browser
            driver.quit();
        }


}
