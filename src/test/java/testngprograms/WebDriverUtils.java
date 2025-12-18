package testngprograms;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WebDriverUtils {
    WebDriver driver;

    public WebDriverUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Taking the entire browser screenshot
     *
     * @param screenName
     * @throws IOException
     */
    public void captureScreenshot(String screenName) throws IOException {

        // take screenshot
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // create Object for Date class
        Date d = new Date();
        screenName = screenName + "-" + d.toString().replace(":", "-").replace(" ", "-") + ".jpg";

        // copy the file name under project directory
        FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\src\\screenshots\\" + screenName));

    }

    /**
     * taking the element screenshot
     *
     * @param element
     * @param screenName
     * @throws IOException
     */
    public void captureScreenshot(WebElement element, String screenName) throws IOException {

        // take screenshot
        File src = ((TakesScreenshot) element).getScreenshotAs(OutputType.FILE);

        // create Object for Date class
        Date d = new Date();
        screenName = screenName + "-" + d.toString().replace(":", "-").replace(" ", "-") + ".jpg";

        // copy the file name under project directory
        FileUtils.copyFile(src, new File(System.getProperty("user.dir") + "\\src\\screenshots\\" + screenName));

    }

    public void switchToFrame(int frame) {
        try {
            driver.switchTo().frame(frame);
            System.out.println("Navigated to frame with id " + frame);
        } catch (NoSuchFrameException e) {
            System.out.println("Unable to locate frame with id " + frame + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Unable to navigate to frame with id " + frame + e.getStackTrace());
        }
    }

    public void switchToFrame(String frame) {
        try {
            driver.switchTo().frame(frame);
            System.out.println("Navigated to frame with name " + frame);
        } catch (NoSuchFrameException e) {
            System.out.println("Unable to locate frame with id " + frame + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Unable to navigate to frame with id " + frame + e.getStackTrace());
        }
    }

    public void switchToFrame(WebElement frameElement) {
        try {
            if (frameElement.isDisplayed()) {
                driver.switchTo().frame(frameElement);
                System.out.println("Navigated to frame with element " + frameElement);
            } else {
                System.out.println("Unable to navigate to frame with element " + frameElement);
            }
        } catch (NoSuchFrameException e) {
            System.out.println("Unable to locate frame with element " + frameElement + e.getStackTrace());
        } catch (StaleElementReferenceException e) {
            System.out.println(
                    "Element with " + frameElement + "is not attached to the page document" + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Unable to navigate to frame with element " + frameElement + e.getStackTrace());
        }
    }

    public void switchToFrame(String ParentFrame, String ChildFrame) {
        try {
            driver.switchTo().frame(ParentFrame).switchTo().frame(ChildFrame);
            System.out.println("Navigated to innerframe with id " + ChildFrame + "which is present on frame with id"
                    + ParentFrame);
        } catch (NoSuchFrameException e) {
            System.out
                    .println("Unable to locate frame with id " + ParentFrame + " or " + ChildFrame + e.getStackTrace());
        } catch (Exception e) {
            System.out.println("Unable to navigate to innerframe with id " + ChildFrame
                    + "which is present on frame with id" + ParentFrame + e.getStackTrace());
        }
    }

    public void switchtoDefaultFrame() {
        try {
            driver.switchTo().defaultContent();
            System.out.println("Navigated back to webpage from frame");
        } catch (Exception e) {
            System.out.println("unable to navigate back to main webpage from frame" + e.getStackTrace());
        }
    }

    // File upload by Robot Class
    /**
     * below method will upload given file location using Java Robot class
     *
     * @param filePath
     */
    public static void uploadFileWithRobot(String filePath) {
        // create object for StringSelection
        StringSelection stringSelection = new StringSelection(filePath);
        // copying the file location to system clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        // Create Object for robot class
        Robot robot = null;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        robot.delay(250);
        // pressing the ENTER key and releasing
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        // pressing the CONTROL+V key and releasing the CONTROL+V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        // pressing the ENTER key and releasing
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(150);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    /**
     * this method downloads the files in firefox browser
     *
     * @param path
     */

    public static FirefoxOptions downloadFileUsingFirefox(String path) {
        // create object for FirefoxOptions class
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.download.dir", path);

        // File type of the downloaded file
        options.addPreference("browser.helperApps.neverAsk.saveToDisk",
                "image/jpeg, application/pdf, application/octet-stream,application/zip");
        options.addPreference("browser.download.manager.showWhenStarting", false);
        options.addPreference("pdfjs.disabled", true);
        return options;
    }

    /**
     * this method download the file using chrome browse
     *
     * @param path
     * @return
     */

    public static ChromeOptions downloadFileUsingChrome(String path) {
        // DOWLOADD CODE
        // craete object for ChromeOptions class
        ChromeOptions options = new ChromeOptions();
        // create HashMap object
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.popups", true);
        prefs.put("download.default_directory", path);
        options.setExperimentalOption("prefs", prefs);
        // set the chromedriver.exe path
        return options;
    }

    /**
     * this method highlights the given element
     *
     * @param driver
     * @param element
     * @throws InterruptedException
     */
    public static void highLightElement1(WebDriver driver, WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver; // downcasting

        js.executeScript("arguments[0].setAttribute('style','background: yellow; border: solid 5px red')", element);

        Thread.sleep(5000);

        js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element);

    }

    /**
     * this method types given keyword without using sendKeys()
     *
     * @param element
     * @param attributeName
     * @param value
     */
    public static void setAttribute(WebElement element, String attributeName, String value) {
        WrapsDriver wrappedElement = (WrapsDriver) element;
        JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
        js.executeScript("arguments[0].setAttribute(arguments[1],arguments[2])", element, attributeName, value);
    }

    public static void scrollForElement(WebDriver driver, WebElement elementname) {
        // scroll for this element
        JavascriptExecutor jsx = (JavascriptExecutor) driver;
        jsx.executeScript("arguments[0].scrollIntoView(true);", elementname);
    }

    public static void safeJavaScriptClick(WebDriver driver, WebElement element) {

        try {
            if (element.isEnabled() && element.isDisplayed()) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
            }

        } catch (StaleElementReferenceException se) {
            System.out.println("Element is no more attached to the DOM:");
            se.printStackTrace();
        } catch (NoSuchElementException ne) {
            System.out.println("No Element present in the DOM:");
            ne.printStackTrace();

        } catch (Exception e) {
            System.out.println("Exception is different:");
            e.printStackTrace();
        }

    }
    /**
     * example: "document.body.style.zoom='400.0%'"
     * @param zoomPercentage
     * valid for chrome/edge/safari
     *
     */
    public void zoomChromeEdgeSafari(String zoomPercentage){
        String zoom="document.body.style.zoom='"+zoomPercentage+"%'";
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript(zoom);
    }
    /**
     * example: "document.body.style.MozTransform='scale(0.5)'"
     * @param zoomPercentage
     * valid for firefox
     *
     */
    public void zoomFirefox(String zoomPercentage){
        String zoom="document.body.style.MozTransform='scale("+zoomPercentage+")'";
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript(zoom);
    }

}
