import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class MainTest
{
    static Dictionary<String, Integer> myItems = new Hashtable<>();
    static WebDriver driver;
    homePage homePage_obj;
    itemPage itemPage_obj;
    cartPage cartPage_obj;

    static ExtentReports extent;
    static ExtentTest Test01,Test02,Test03,Test04,Test05,Test06,Test07,Test08,Test09;

    @BeforeTest
    public void initSetup()
    {
        driver = new ChromeDriver();
        driver.get("https://www.demoblaze.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        extent = ExtentManager.GetExtent();
        Test01 = ExtentManager.createTest("Test 01 - Verify search results for Phones", "We are sorting the list by category Phones and compare results to DB");
        Test02 = ExtentManager.createTest("Test 02 - Verify search results for Laptops", "We are sorting the list by category Laptops and compare results to DB");
        Test03 = ExtentManager.createTest("Test 03 - Verify search results for Monitors", "We are sorting the list by category Monitors and compare results to DB");
        Test04 = ExtentManager.createTest("Test 04 - Verify proper navigation using navigation bar", "We are using the navigation bar to move to different URLs and get proper popup window");
        Test05 = ExtentManager.createTest("Test 05 - Send contact Message", "We are expecting to get alert massage when contact message is sent");
        Test06 = ExtentManager.createTest("Test 06 - Adding items to cart", "We are adding all items to cart from Category Monitors");
        Test07 = ExtentManager.createTest("Test 07 - Compare items vs cart", "We are expecting that all the items we added will shown in the cart (comparing name and price)");
        Test08 = ExtentManager.createTest("Test 08 - Compare total price is proper calculated", "We are comparing the total price with our price calculation)");
        Test09 = ExtentManager.createTest("Test 09 - Approve purchase", "We are expecting to get alert message when try to approve purchase with empty fields and approve purchase and validate data and price");
    }
    @AfterTest
    public void tearDown()
    {
        driver.close();
        driver.quit();
        extent.flush();
    }
    @Test(priority = 1)
    public void verifySearchResultsPhones() throws InterruptedException
    {
        homePage_obj = new homePage(driver);
        // sort results by category phones
        String category = homePage_obj.clickPhones();
        Thread.sleep(1000);
        // compare elements on page to database
        homePage_obj.verifyElementsOnPage(Test01,category);
    }
    @Test(priority = 2)
    public void verifySearchResultsLaptops() throws InterruptedException
    {
        // sort results by category laptops
        String category = homePage_obj.clickLaptops();
        Thread.sleep(1000);
        // compare elements on page to database
        homePage_obj.verifyElementsOnPage(Test02,category);
    }
    @Test(priority = 3)
    public void verifySearchResultsMonitors() throws InterruptedException
    {
        // sort results by category monitors
        String category = homePage_obj.clickMonitors();
        Thread.sleep(1000);
        // compare elements on page to database
        homePage_obj.verifyElementsOnPage(Test03,category);
    }
    @Test(priority = 4)
    public void navigationBar()
    {
        // verify correct URL
        homePage_obj.homeButtonTest(Test04);
        // verify popup is visible and close pop up
        homePage_obj.contactButtonTest(Test04,true);
        // verify popup is visible
        homePage_obj.aboutUsButtonTest(Test04);
        // verify correct URL
        homePage_obj.cartButtonTest(Test04);
        // verify popup is visible
        homePage_obj.loginButtonTest(Test04);
        // verify popup is visible
        homePage_obj.signingButtonTest(Test04);
    }
    @Test(priority = 5)
    public void sendContactMessage()
    {
        // verify popup is visible, and we leave open for next test
        homePage_obj.contactButtonTest(Test05,false);
        // verify alert after filling form and send
        homePage_obj.fillContactForm(Test05);
    }
    @Test(priority = 6)
    public void addItemToCart() throws InterruptedException
    {
        // go to home page to start from fresh
        homePage_obj.goToHome();
        // sort results by category monitors
        homePage_obj.clickMonitors();
        Thread.sleep(1000);
        // get the size of the results after sorting
        List<WebElement> Results = homePage_obj.returnResults();
        // loop iteration will be change according to results
        for (int i = 0; i <= Results.size()-1; i++)
        {
            // move to item page by click element
            itemPage_obj = homePage_obj.selectItems(i,Results);
            //fetch item name and price from item page
            String name = itemPage_obj.getItemName();
            int price = itemPage_obj.getPrice();
            // add item name and price to dict
            myItems.put(name,price);
            // add item to cart and verify proper alert message
            itemPage_obj.addToCart(Test06);
            // no need to perform on last iteration
            if (i != Results.size()-1)
            {
                homePage_obj.goToHome();
                homePage_obj.clickMonitors();
                Thread.sleep(1000);
                //reload results
                Results = homePage_obj.returnResults();
            }
        }
    }
    @Test(priority = 7)
    public void compareItemsAddedToCart()
    {
        Dictionary<String, Integer> cartItems = new Hashtable<>();
        // go to cart
        cartPage_obj = itemPage_obj.goToCart();
        // get elements from cart
        int itemsInCart = cartPage_obj.getProductsInCart();
        // loop on the items in cart page
        for (int i = 0; i < itemsInCart; i++)
        {
            // fetch item name and price and put in dict
            String title = cartPage_obj.getTitle(i);
            int price = cartPage_obj.getPrice(i);
            cartItems.put(title,price);
        }
        // verify dict's are equal
        cartPage_obj.compareItemVsCart(Test07, cartItems,myItems);
    }
    @Test(priority = 8)
    public void checkProductPriceInCart()
    {
        int sum = 0;
        // get elements from cart
        int itemsInCart = cartPage_obj.getProductsInCart();
        for (int i = 0; i < itemsInCart; i++)
        {
            // get elements price from cart and sum them
            int price = cartPage_obj.getPrice(i);
            sum = sum + price;
        }
        // verify that items price sum and total price are the same
        cartPage_obj.compareCalcPriceToTotal(Test08, sum);
    }
    @Test(priority = 9)
    public void approveOrder()
    {
        // verify order form is visible
        cartPage_obj.getOrderForm(Test09);
        // verify proper alert message when send form empty
        cartPage_obj.sendEmptyOrderForm(Test09);
        // verify costumer summery data is the same as the costumer database (date will fail month and year are not updated)
        cartPage_obj.fillOrderFormAndSend(Test09);
    }
}
