package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

import static org.junit.Assert.assertEquals;

public class RegisterMailChimpStepdefs {
    private WebDriver driver;

    @Before
    public void SetUp() {
    }

    @After
    public void TearDown() {
        driver.close();
        driver.quit();
    }

    @Given("I have opened my {string} and I'm on the MailChimp sign up page")
    public void iHaveOpenedMyAndIMOnTheMailChimpSignUpPage(String browser) throws InterruptedException {
        if (browser.equalsIgnoreCase("brave")) {
            System.setProperty("webdriver.chrome.driver", "G:\\Selenium\\chromedriver_win32\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
            options.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.get("https://login.mailchimp.com/signup/");
            Thread.sleep(1000);
        } else if (browser.equalsIgnoreCase("edge")) {
            //System.setProperty("webdriver.edge.driver", "C:/WebDrivers/msedgedriver.exe");
            driver = new EdgeDriver();

            driver.manage().window().maximize();
            driver.get("https://login.mailchimp.com/signup/");
            Thread.sleep(1000);
        }
    }

    @Given("I have entered my {string} and my {string} and my {string}")
    public void iHaveEnteredMyAndMyAndMy(String email, String username, String password) throws InterruptedException {
        WebElement mail = driver.findElement(By.name("email"));
        mail.sendKeys(email);
        Thread.sleep(1000);
        WebElement user = driver.findElement(By.name("username"));
        user.sendKeys("");
        user.clear();
        user.sendKeys("admin" + username);
        Thread.sleep(1000);
        WebElement pwd = driver.findElement(By.name("password"));
        pwd.sendKeys(password);
        Thread.sleep(1000);
    }

    @When("I click the Sign Up button")
    public void iClickTheSignUpButton() throws InterruptedException {
        Thread.sleep(2000);
        WebElement button = driver.findElement(By.cssSelector("#create-account"));
        System.out.println("TEST!!!");
        Thread.sleep(2000);
        button.click();
        System.out.println("CLICK!!!");
        Thread.sleep(2000);

    }

    @Then("I have {string} to register")
    public void iHaveToRegister(String succeeded) throws InterruptedException {
       if (succeeded.equalsIgnoreCase("yes")) {

            String expectedTitle = "Success | Mailchimp";
            String actualTitle = driver.getTitle();
            assertEquals(expectedTitle, actualTitle);

        }else if (succeeded.equalsIgnoreCase("no")) {

            WebElement errorMsg = driver.findElement(By.cssSelector(".padding--lv3 > li"));
            String actualText = errorMsg.getText();
           String expectedTitle = "Please check your entry and try again.";
          assertEquals(expectedTitle, actualText);
        }
    }
}
