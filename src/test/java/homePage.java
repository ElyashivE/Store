import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class homePage extends topBar
{
    By phones = By.xpath("//a[text()='Phones']");
    By laptops = By.xpath("//a[text()='Laptops']");
    By monitors = By.xpath("//a[text()='Monitors']");
    By results = By.xpath("//h4[@class='card-title']");

    public homePage(WebDriver driver)
    {
        super(driver);

        // verify element after create the object
        getElement(driver,phones);
        getElement(driver,laptops);
        getElement(driver,monitors);
        getElement(driver,results);
    }
//################################################## PAGE METHODS ######################################################
    public String clickPhones()
    {
        WebElement Phones = waitVisibility(driver,getElement(driver,phones));
        Phones.click();
        return Phones.getText();
    }
    public String clickLaptops()
    {
        WebElement Laptops = getElement(driver,laptops);
        Laptops.click();
        return Laptops.getText();
    }
    public String clickMonitors()
    {
        WebElement Monitors = getElement(driver,monitors);
        Monitors.click();
        return Monitors.getText();
    }
    public List<WebElement> returnResults()
    {
        return getElements(driver,results);

    }
    public itemPage selectItems(int item, List<WebElement> result)
    {
        result.get(item).click();
        return new itemPage(driver);
    }
//############################################### TEST FUNCTIONS #######################################################
    public List<String> _getDB(String cat)
    {
        List<String> list = null;
        switch (cat)
        {
            case "Phones" -> list = Arrays.asList("Samsung galaxy s6", "Samsung galaxy s7", "Nokia lumia 1520", "Iphone 6 32gb", "Nexus 6", "Sony xperia z5", "HTC One M9");
            case "Laptops" -> list = Arrays.asList("Sony vaio i5", "Sony vaio i7", "MacBook air", "MacBook Pro",  "Dell i7 8gb", "2017 Dell 15.6 Inch");
            case "Monitors" -> list = Arrays.asList("Apple monitor 24", "ASUS Full HD");
        }
        return list;
    }
//################################################### TESTS ############################################################
    public void verifyElementsOnPage(ExtentTest test, String categorySelected)
    {
        try
        {
            int fails = 0;
            List<String> listDB = _getDB(categorySelected);
            List<WebElement> Results = getElements(driver,results);
            for (int i = 0; i < Results.size(); i++)
            {
                String result = Results.get(i).getText();
                boolean isFound = listDB.contains(result);
                if(!isFound)
                {
                    System.out.println(result);
                    fails++;
                }
            }
            Assert.assertTrue(printResultToER(test,String.valueOf(fails),String.valueOf(0),"We are sorting the list by "+categorySelected+" and compare results to DB"));
            System.out.println("TEST PASSED - sorting result by category "+categorySelected);
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
}
