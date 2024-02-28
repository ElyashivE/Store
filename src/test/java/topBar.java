import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class topBar extends func
{
    WebDriver driver;
    By header = By.xpath("//a[@id='nava']");
    By home = By.xpath("//a[text()='Home ']");
    By contact = By.xpath("//a[text()='Contact']");
    By aboutUs = By.xpath("//a[text()='About us']");
    By cart = By.xpath("//a[text()='Cart']");
    By login = By.xpath("//a[text()='Log in']");
    By signUp = By.xpath("//a[text()='Sign up']");
    By aboutUsBy = By.xpath("//*[@id='videoModal']//*[@class='modal-content']");
    By aboutUsCloseBy = By.xpath("//div[@id='videoModal']//button[text()='Close']");
    By loginBy = By.xpath("//*[@id='logInModal']//*[@class='modal-content']");
    By loginCloseBy = By.xpath("//div[@id='logInModal']//button[text()='Close']");
    By signingBy = By.xpath("//*[@id='signInModal']//*[@class='modal-content']");
    By signingCloseBy = By.xpath("//div[@id='signInModal']//button[text()='Close']");
    By contactBy = By.xpath("//*[@id='exampleModal']//*[@class='modal-content']");
    By contactCloseBy = By.xpath("//div[@id='exampleModal']//button[text()='Close']");
    By emailBy = By.xpath("//*[@id='exampleModal']//div[1]/input");
    By nameBy = By.xpath("//*[@id='exampleModal']//div[2]/input");
    By messageBy = By.xpath("//*[@id='exampleModal']//textarea");
    By sendBtnBy = By.xpath("//*[@id='exampleModal']//*[text()='Send message']");
    public topBar(WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        getElement(driver,header);
        getElement(driver,home);
        getElement(driver,contact);
        getElement(driver,aboutUs);
        getElement(driver,cart);
        getElement(driver,login);
        getElement(driver,signUp);
    }
//################################################## PAGE METHODS ######################################################
    public homePage goToHome()
    {
        getElement(driver, home).click();
        return new homePage(driver);
    }
    public cartPage goToCart()
    {
        getElement(driver, cart).click();
        return new cartPage(driver);
    }
//################################################### TESTS ############################################################
    public void homeButtonTest(ExtentTest test)
    {
        try
        {
            String expectedURL = "https://www.demoblaze.com/index.html";
            getElement(driver,home).click();
            String actualURL = driver.getCurrentUrl();
            Assert.assertTrue(printResultToER(test,actualURL,expectedURL,"We are expecting to got Home Page URL"));
            System.out.println("TEST PASSED - Got Home URL as Expected");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e.getMessage());
            exception(test,e);
        }
    }
    public void contactButtonTest(ExtentTest test, boolean toClose)
    {
        try
        {
            getElement(driver,contact).click();
            WebElement contactWin = waitVisibility(driver,getElement(driver, contactBy));
            WebElement closeBtn = getElement(driver,contactCloseBy);
            boolean isVisible = contactWin.isDisplayed();
            if(toClose)
                closeBtn.click();
            Assert.assertTrue(printResultToER(test,String.valueOf(isVisible),"true","We are expecting to contact popup"));
            System.out.println("TEST PASSED - We Able to Locate Contact Window");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void aboutUsButtonTest(ExtentTest test)
    {
        try
        {
            getElement(driver,aboutUs).click();
            WebElement aboutUsWin = waitVisibility(driver,getElement(driver, aboutUsBy));
            WebElement closeBtn = getElement(driver, aboutUsCloseBy);
            boolean isVisible = aboutUsWin.isDisplayed();
            closeBtn.click();
            Assert.assertTrue(printResultToER(test,String.valueOf(isVisible),"true","We are expecting to about up popup"));
            System.out.println("TEST PASSED - We Able to Locate About Us Window");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void cartButtonTest(ExtentTest test)
    {
        try
        {
            String expectedURL = "https://www.demoblaze.com/cart.html";
            getElement(driver,cart).click();
            String actualURL = driver.getCurrentUrl();
            Assert.assertTrue(printResultToER(test,actualURL,expectedURL,"We are expecting to got Cart Page URL"));
            System.out.println("TEST PASSED - Got Cart URL as Expected");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void loginButtonTest(ExtentTest test)
    {
        try
        {
            getElement(driver,login).click();
            WebElement loginWin = waitVisibility(driver,getElement(driver, loginBy));
            WebElement closeBtn = getElement(driver, loginCloseBy);
            boolean isVisible = loginWin.isDisplayed();
            closeBtn.click();
            Assert.assertTrue(printResultToER(test,String.valueOf(isVisible),"true","We are expecting to login popup"));
            System.out.println("TEST PASSED - We Able to Locate Log In Window");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void signingButtonTest(ExtentTest test)
    {
        try
        {
            getElement(driver,signUp).click();
            WebElement signingWin = waitVisibility(driver,getElement(driver, signingBy));
            WebElement closeBtn = getElement(driver, signingCloseBy);
            boolean isVisible = signingWin.isDisplayed();
            closeBtn.click();
            Assert.assertTrue(printResultToER(test,String.valueOf(isVisible),"true","We are expecting to sign in popup"));
            System.out.println("TEST PASSED - We Able to Locate Sign Out Window");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
    public void fillContactForm(ExtentTest test)
    {
        try
        {
            WebElement email = getElement(driver,emailBy);
            WebElement name = getElement(driver,nameBy);
            WebElement message = getElement(driver,messageBy);
            WebElement sendBtn = getElement(driver,sendBtnBy);

            email.sendKeys("testName@example.com");
            name.sendKeys("testName");
            message.sendKeys("Hello my name is testName...");
            sendBtn.click();

            Alert alert = driver.switchTo().alert();
            String actualAlertMsg = alert.getText();
            String expectedAlertMsg = "Thanks for the message!!";
            alert.accept();
            Assert.assertTrue(printResultToER(test,actualAlertMsg,expectedAlertMsg,"We are expecting to got Home Page URL"));
            System.out.println("TEST PASSED - We Got Alert Message as Expected");
        }
        catch (Exception e)
        {
            System.out.println("TEST FAILED!!!");
            System.out.println("With Error:" +e);
            exception(test,e);
        }
    }
}
