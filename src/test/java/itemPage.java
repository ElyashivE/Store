import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class itemPage extends topBar
{
    By itemName = By.xpath("//h2");
    By itemPrice = By.xpath("//h3");
    By itemDescription = By.xpath(("//div[@class='description description-tabs']"));
    By itemPicture = By.xpath("//div[@class='item active']/img");
    By addToCartBtn = By.xpath("//a[text()='Add to cart']");
    public itemPage(WebDriver driver)
    {
        super(driver);
        getElement(driver,itemName);
        getElement(driver,itemPrice);
        getElement(driver,itemDescription);
        getElement(driver,itemPicture);
        getElement(driver,addToCartBtn);
    }
//################################################## PAGE METHODS ######################################################
    public String getItemName()
    {
        WebElement name = waitVisibility(driver,getElement(driver,itemName));
        return name.getText();
    }
    public int getPrice()
    {
        WebElement Price = waitVisibility(driver,getElement(driver,itemPrice));
        String price = Price.getText();
        return replaceAndConvertPrice(price);
    }
//################################################### TESTS ############################################################
    public void addToCart(ExtentTest test)
    {
        try
        {
            WebElement addItem = getElement(driver,addToCartBtn);
            addItem.click();
            Alert alert = waitAlert(driver);
            String actualAlertMsg = alert.getText();
            String expectedAlertMsg = "Product added";
            driver.switchTo().alert().accept();
            Assert.assertTrue(printResultToER(test,actualAlertMsg,expectedAlertMsg,"We expecting to get alert message each item added"));
            System.out.println("TEST PASSED - Got Alert Message as Expected");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }

    }
}
