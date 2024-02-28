import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class cartPage extends topBar
{
    By productsHeaderBy = By.xpath("//h2[text()='Products']");
    By totalHeaderBy = By.xpath("//h2[text()='Total']");
    By productsBy = By.xpath("//tr[@class='success']");
    By productsTitleBy = By.xpath("//tr[@class='success']/td[2]");
    By productsPriceBy = By.xpath("//tr[@class='success']/td[3]");
    By totalPriceBy = By.xpath("//h3[@id='totalp']");
    By tableHeader = By.xpath("//thead");
    By placeOrder = By.xpath("//button[text()='Place Order']");
    By orderBy = By.xpath("//*[@id='orderModal']//*[@class='modal-content']");
    By orderPurchaseBy = By.xpath("//*[text()='Purchase']");
    By orderCloseBy = By.xpath("//div[@id='orderModal']//button[text()='Close']");
    By confirmMsgBy = By.xpath("//*[text()='Thank you for your purchase!']");
    By confirmDataBy = By.xpath("//p[contains(@class,'lead')]");
    public cartPage(WebDriver driver)
    {
        super(driver);
        getElement(driver,productsHeaderBy);
        getElement(driver,totalHeaderBy);
        getElement(driver,tableHeader);
        getElement(driver,placeOrder);
    }
//################################################## PAGE METHODS ######################################################
    public int getProductsInCart()
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        List<WebElement> elements = getElements(driver,productsBy);
        return elements.size();
    }
    public String getTitle(int i)
    {
        List<WebElement> name = getElements(driver,productsTitleBy);
        return name.get(i).getText();
    }
    public int getPrice(int i)
    {
        List<WebElement> Price = getElements(driver,productsPriceBy);
        String price = Price.get(i).getText();
        return Integer.parseInt(price);
    }
//############################################### TEST FUNCTIONS #######################################################
    public static Dictionary<String, String> getFakeCustomerDB()
    {
        Dictionary<String, String> customerDB = new Hashtable<>();
        customerDB.put("name", "John Doe");
        customerDB.put("country", "Israel");
        customerDB.put("city", "Jerusalem");
        customerDB.put("card", "1234 5678 1234 5678");
        customerDB.put("month", "12");
        customerDB.put("year", "2025");
        return customerDB;
    }
    public int priceChecker(String price, String confirmAmount)
    {
        int fails = 0;
        confirmAmount = confirmAmount.replace("Amount: ","");
        confirmAmount = confirmAmount.replace(" USD","");
        if(!confirmAmount.equals(price))
            fails ++;
        return fails;
    }
    public int nameChecker(String name, String confirmName)
    {
        int fails = 0;
        confirmName = confirmName.replace("Name: ","");
        if(!confirmName.equals(name))
            fails ++;
        return fails;
    }
    public int cardNumChecker(String cardNum, String confirmCardNum)
    {
        int fails = 0;
        confirmCardNum = confirmCardNum.replace("Card Number: ","");
        if(!confirmCardNum.equals(cardNum))
            fails ++;
        return fails;
    }
    public int dateChecker(String month, String year, String confirmDate)
    {
        int fails = 0;
        confirmDate = confirmDate.replace("Date: ","");
        String [] splitDate = confirmDate.split("/");
        String Sday = splitDate[0];
        String Smonth = splitDate[1];
        String Syear = splitDate[2];
        if (!month.equals(Smonth) || !year.equals(Syear))
            fails ++;
        return fails;
    }
//################################################### TESTS ############################################################
    public void compareItemVsCart(ExtentTest test, Dictionary<String,Integer> dict1, Dictionary<String,Integer> dict2)
    {
        try
        {
            Assert.assertTrue(printResultToERdict(test,dict1,dict2,"We are expecting to got Home Page URL"));
            System.out.println("TEST PASSED - The Items We Added to Cart Matched to The Cart");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void compareCalcPriceToTotal(ExtentTest test,int productsPrice)
    {
        try
        {
            WebElement totalPrice = getElement(driver,totalPriceBy);
            String totalP = totalPrice.getText();
            String calcP = String.valueOf(productsPrice);
            Assert.assertTrue(printResultToER(test,calcP,totalP,"We are expecting to get matched prices for the total and calculated"));
            System.out.println("TEST PASSED - The Calculated Price is Matched");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void getOrderForm(ExtentTest test)
    {
        try
        {
            WebElement placeOrderBtn = waitVisibility(driver,getElement(driver,placeOrder));
            placeOrderBtn.click();
            WebElement orderWin = waitVisibility(driver,getElement(driver, orderBy));
            WebElement closeBtn = waitVisibility(driver,getElement(driver, orderCloseBy));
            boolean isVisible = orderWin.isDisplayed();
            closeBtn.click();
            Assert.assertTrue(printResultToER(test,String.valueOf(isVisible),"true","We expecting to order form popup"));
            System.out.println("TEST PASSED - We Able to Locate Order Window");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void sendEmptyOrderForm(ExtentTest test)
    {
        try
        {
            WebElement placeOrderBtn = getElement(driver,placeOrder);
            placeOrderBtn.click();
            WebElement orderWin = waitVisibility(driver,getElement(driver, orderBy));
            WebElement purchaseBtn = waitVisibility(driver,getElement(driver, orderPurchaseBy));
            purchaseBtn.click();
            Alert alert = waitAlert(driver);
            String actualAlertMsg = alert.getText();
            String expectedAlertMsg = "Please fill out Name and Creditcard.";
            alert.accept();
            WebElement closeBtn = getElement(driver,orderCloseBy);
            closeBtn.click();
            Assert.assertTrue(printResultToER(test,actualAlertMsg,expectedAlertMsg,"We expecting to get alert with proper message when try to send form empty"));
            System.out.println("TEST PASSED - We Got Alert Message as Expected");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void fillOrderFormAndSend(ExtentTest test)
    {
        try
        {
            WebElement totalPrice = getElement(driver,totalPriceBy);
            String totalP = totalPrice.getText();
            WebElement placeOrderBtn = getElement(driver,placeOrder);
            placeOrderBtn.click();
            WebElement orderWin = waitVisibility(driver,getElement(driver, orderBy));
            Dictionary<String, String> customer = getFakeCustomerDB();
            Enumeration<String> keys = customer.keys();
            while (keys.hasMoreElements())
            {
                String key = keys.nextElement();
                String value = customer.get(key);
                getElement(driver,By.id(key)).sendKeys(value);
            }
            WebElement purchaseBtn = getElement(driver,orderPurchaseBy);
            purchaseBtn.click();
            WebElement confirmMsg = waitVisibility(driver,getElement(driver,confirmMsgBy));
            WebElement confirmData = waitVisibility(driver,getElement(driver,confirmDataBy));
            String data = confirmData.getText();
            String[] splitData = data.split("\n");
            String id = splitData[0];
            String amount = splitData[1];
            String card = splitData[2];
            String name = splitData[3];
            String date = splitData[4];

            int fails = 0;

            int priceFails = priceChecker(totalP,amount);
            printResultToER(test,String.valueOf(priceFails),String.valueOf(fails),"We are expecting that the prices are matched");

            int nameFails = nameChecker(customer.get("name"),name);
            printResultToER(test,String.valueOf(nameFails),String.valueOf(fails),"We are expecting that the names are matched");

            int cardFails = cardNumChecker(customer.get("card"),card);
            printResultToER(test,String.valueOf(cardFails),String.valueOf(fails),"We are expecting that the card numbers are matched");

            int dateFails = dateChecker(customer.get("month"),customer.get("year"),date);
            printResultToER(test,String.valueOf(dateFails),String.valueOf(fails),"We are expecting that the date are matched");

            fails = priceFails + nameFails + cardFails + dateFails;
            Assert.assertTrue(printResultToER(test, String.valueOf(fails), "0","Overall result for checking data"));
            System.out.println("TEST PASSED - All Data Are Matched");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
}
