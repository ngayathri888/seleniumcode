package junitprograms;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HerokuAppLoginTest {
    static WebDriver driver = null;
    static WebDriverWait wait = null;
    static WebElement userNameEditBox = null;

    @BeforeAll
    public static void launchApplication() {

    }

    @BeforeEach
    public void setUp() {

        //launch the chrome browser
        driver = new ChromeDriver();
        //maximize the window
        driver.manage().window().fullscreen();
        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10));
        //launch the url
        driver.get("https://the-internet.herokuapp.com/");
        //add explicitwait
        wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        wait.until(ExpectedConditions.titleIs("The Internet"));

        //validate the page title
        Assertions.assertEquals(driver.getTitle(), "The Internet");

        //click on form authentication
        driver.findElement(By.linkText("Form Authentication")).click();

        wait.until(ExpectedConditions.urlContains("/login"));
        WebElement loginPageHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='content']//h2")));
        Assertions.assertEquals(loginPageHeading.getText(), "Login Page");
        WebElement subHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".subheader")));
        userNameEditBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#username")));
        Assertions.assertTrue(userNameEditBox.isDisplayed());

    }

    @Test
    public void validCredentialTest() throws InterruptedException {
        System.out.println("started executing the login scenario with valid crendentials...");
        //clear the content in the edit box
        //userNameEditBox.clear();
        //enter the username value in username editbox
        //userNameEditBox.sendKeys("tomsmith");
        //WebElement passwordEditBox = driver.findElement(By.cssSelector("input[name='password']"));
        //clear the content on password edit box
        //passwordEditBox.clear();
        //enter the password for password editbox
        //passwordEditBox.sendKeys("SuperSecretPassword!");
        //click on login button
        doLogin("tomsmith","SuperSecretPassword!");
        //driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/secure"));
        verifyMessages("You logged into a secure area!");

        //WebElement sucmsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#flash")));
        //verify the succeess message
        //Assertions.assertEquals(sucmsg.getText().trim(), "You logged into a secure area!", "success message is not displdaye");
        //wait for the secure area heading
        WebElement secureAreaHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='example'] h2")));
        Assertions.assertEquals(secureAreaHeading.getText().trim(), "Secure Area");
        //click on logout button
        driver.findElement(By.xpath("//a[@href='/logout']")).click();
        Thread.sleep(2000);
        verifyMessages("You logged out of the secure area!");

    }

    @Test
    public void loginWithInvalidUserNameAndValidPasswordTest() {
        System.out.println("started executing the login scenario with invalid username and valid password...");
        doLogin("Ramesh", "SuperSecretPassword!");
        verifyMessages("Your username is invalid!");
    }

    @AfterEach
    public void tearDemo() {
        //clear the cookies
        driver.manage().deleteAllCookies();
        driver.quit();
    }



    private void doLogin(String username, String pwd) {
//clear the content in the editbox
        userNameEditBox.clear();
        //enter the userName value in userNameEditBox
        userNameEditBox.sendKeys(username);
        WebElement passwordEditBox = driver.findElement(By.cssSelector("input[name=password]"));
        //clear the content in passwordEditbox
        passwordEditBox.clear();
        //enter the password for password editbox
        passwordEditBox.sendKeys(pwd);
        //click on login button
        driver.findElement(By.cssSelector("button[type='submit']")).click();


    }
    private void verifyMessages(String expectedMsg){
        WebElement loggedoutSucMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='flash-messages']/div")));
        Assertions.assertTrue(loggedoutSucMsg.getText().trim().contains(expectedMsg));

    }
}




