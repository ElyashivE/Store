import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Dictionary;
import java.util.List;


public class func extends MainTest
{
    public static WebElement getElement(WebDriver driver, By by)
    {
        WebElement element = driver.findElement(by);
        return element;
    }
    public static WebElement waitVisibility(WebDriver driver,WebElement Element)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOf(Element));
        return element;
    }
    public static Alert waitAlert(WebDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.alertIsPresent());
    }
    public static List<WebElement> getElements(WebDriver driver, By by)
    {
        List<WebElement> elements = driver.findElements(by);
        return elements;
    }
    public int replaceAndConvertPrice(String price)
    {
        price = price.replace("$","");
        price = price.replace(" *includes tax","").trim();
        return Integer.parseInt(price);
    }
    public static boolean printResultToER(ExtentTest test, String actualResult, String expectedResult, String testDescription)
    {
        boolean isEqual = actualResult.equals(expectedResult);
        if(isEqual)
            test.pass(MarkupHelper.createLabel("PASSED - "+testDescription, ExtentColor.GREEN));
        else
        {
            try
            {
                test.fail(MarkupHelper.createLabel("FAILED!!! - "+testDescription,ExtentColor.RED));
                //create screenshot file and retrieve the file path.
                String x = ExtentManager.CaptureScreen(driver);
                //add the screenshot file to the extent report.
                test.info("See Screen Below:<br>", MediaEntityBuilder.createScreenCaptureFromPath(x).build());
            }
            catch (Exception ex)
            {
                test.fail(MarkupHelper.createLabel("Unexpected issue when try to get screenshot",ExtentColor.RED));
            }
        }
        test.info("Actual Result: "+actualResult);
        test.info("Expected Result: "+expectedResult);
        return isEqual;
    }
    public static boolean printResultToERdict(ExtentTest test, Dictionary<String,Integer> actualResult, Dictionary<String,Integer> expectedResult, String testDescription)
    {
        boolean isEqual = actualResult.equals(expectedResult);
        if(isEqual)
            test.pass(MarkupHelper.createLabel("PASSED - "+testDescription, ExtentColor.GREEN));
        else
        {
            test.fail(MarkupHelper.createLabel("FAILED!!! - "+testDescription,ExtentColor.RED));
            try
            {
                //create screenshot file and retrieve the file path.
                String x = ExtentManager.CaptureScreen(driver);
                //add the screenshot file to the extent report.
                test.info("See Screen Below:<br>", MediaEntityBuilder.createScreenCaptureFromPath(x).build());
            }
            catch (Exception ex)
            {
                test.fail(MarkupHelper.createLabel("Unexpected issue when try to get screenshot",ExtentColor.RED));
            }
        }
        test.info("Actual Result: "+actualResult);
        test.info("Expected Result: "+expectedResult);
        return isEqual;
    }
    public static void exception(ExtentTest test, Exception e)
    {
        test.fail(MarkupHelper.createLabel("FAILED!!! With Exception: <br>"+e.getMessage(),ExtentColor.YELLOW));
        try
        {
            //create screenshot file and retrieve the file path.
            String x = ExtentManager.CaptureScreen(driver);
            //add the screenshot file to the extent report.
            test.info("See Screen Below:<br>", MediaEntityBuilder.createScreenCaptureFromPath(x).build());
        }
        catch (Exception ex)
        {
            test.fail(MarkupHelper.createLabel("Unexpected issue when try to get screenshot",ExtentColor.RED));
        }
    }
}
