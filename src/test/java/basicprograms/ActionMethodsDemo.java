//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package basicprograms;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionMethodsDemo {
    static WebDriver driver = null;

    public ActionMethodsDemo() {
    }

    public static void main(String[] args) {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10L));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000L));
        driver.get("https://testautomationpractice.blogspot.com/");
        wait.until(ExpectedConditions.titleIs("Automation Testing Practice"));
        String pgTitle = driver.getTitle();
        System.out.println("current page title:+pgTitle");
        driver.findElement(By.id("name")).sendKeys(new CharSequence[]{"gayathri"});
        WebElement emailEditbox = driver.findElement(By.cssSelector("input[placeholder='Enter EMail'"));
        emailEditbox.sendKeys(new CharSequence[]{"ngayathri888@gmail.com"});
        driver.findElement(By.xpath("//input[@id='phone']")).sendKeys(new CharSequence[]{"568987645678"});
        driver.findElement(By.cssSelector("#textarea")).sendKeys(new CharSequence[]{"Bangalore India-560048"});
        WebElement maleRadioBtn = driver.findElement(By.id("male"));
        if (maleRadioBtn.isDisplayed() && !maleRadioBtn.isSelected()) {
            maleRadioBtn.click();
        }

        WebElement saturdayCheckbox = driver.findElement(By.cssSelector("input[value='saturday']"));
        if (saturdayCheckbox.isDisplayed() && !saturdayCheckbox.isSelected()) {
            saturdayCheckbox.click();
        }

        selectOption(By.id("country"), "India");
        selectOption(By.id("country"), "United States");
        WebElement multiColorsDropdown = driver.findElement(By.id("colors"));
        Select sel = new Select(multiColorsDropdown);
        sel.selectByVisibleText("Green");
        System.out.println("selected based on visible text:" + sel.getFirstSelectedOption().getText());
        sel.selectByValue("yellow");
        System.out.println("selected based on value attribute:" + sel.getFirstSelectedOption().getText());
        sel.selectByIndex(1);
        System.out.println("selected based on index:" + sel.getFirstSelectedOption().getText());
        sel.selectByIndex(0);
        System.out.println("selected based on index:" + sel.getFirstSelectedOption().getText());
        List<WebElement> selectedOptionsList = sel.getAllSelectedOptions();
        System.out.println("selected options size is:" + selectedOptionsList.size());
        Iterator var9 = selectedOptionsList.iterator();

        while(var9.hasNext()) {
            WebElement element = (WebElement)var9.next();
            System.out.println(element.getText());
        }

        sel.deselectByVisibleText("Green");
        sel.deselectByValue("yellow");
        sel.deselectByIndex(0);
        List<WebElement> selectedOptionsList1 = sel.getAllSelectedOptions();
        System.out.println("selected options size after deselected 3 options then remaining is:" + selectedOptionsList1.size());
        sel.deselectByValue("white");
        sel.deselectByValue("blue");
        List<WebElement> selectedOptionsList2 = sel.getAllSelectedOptions();
        System.out.println("selected 2 more extra after deselection is:" + selectedOptionsList2.size());
        sel.deselectAll();
        List<WebElement> selectedOptionsList3 = sel.getAllSelectedOptions();
        System.out.println("selected options  after deselecteall is:" + selectedOptionsList3.size());
        List<WebElement> msOptionList = sel.getOptions();
        sel.selectByIndex(msOptionList.size() - 1);
        System.out.println("last selected option is:" + sel.getFirstSelectedOption().getText());
        driver.quit();
    }

    private static void selectOption(By by, String optiontoSelect) {
        WebElement countryDropdown = driver.findElement(by);
        List<WebElement> optionsList = countryDropdown.findElements(By.tagName("option"));
        Iterator var4 = optionsList.iterator();

        while(var4.hasNext()) {
            WebElement option = (WebElement)var4.next();
            String optionText = option.getText();
            System.out.println("dropdown option is:" + optionText);
            if (optionText.equalsIgnoreCase(optiontoSelect)) {
                option.click();
                break;
            }
        }

    }
}
