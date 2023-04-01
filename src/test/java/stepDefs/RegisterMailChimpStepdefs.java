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
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class RegisterMailChimpStepdefs {
    private WebDriver driver;
    private WebDriver wait;

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

        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format date and time as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTimeString = currentDateTime.format(formatter);

        // Use the formatted date and time as a username
        String usernameTime = "user_" + dateTimeString;
        //System.out.println("Username: " + username);

        WebElement mail = driver.findElement(By.name("email"));
        mail.sendKeys(email);
        Thread.sleep(1000);
        WebElement user = driver.findElement(By.name("username"));
        user.sendKeys("");
        user.clear();
        user.sendKeys(username + dateTimeString);
        Thread.sleep(1000);
        WebElement pwd = driver.findElement(By.name("password"));
        pwd.sendKeys(password);
        Thread.sleep(1000);

    }

    @When("I click the Sign Up button")
    public void iClickTheSignUpButton() throws InterruptedException {

        WebElement button = driver.findElement(By.id("create-account-enabled"));
        Thread.sleep(2000);
        //button.click();
        Actions actions = new Actions(driver);
        actions.moveToElement(button).click().perform();
        Thread.sleep(20000);

    }

    @Then("I have {string} to register")
    public void iHaveToRegister(String succeeded) throws InterruptedException {
        if (succeeded.equalsIgnoreCase("yes")) {

            String expected = "Success | Mailchimp";
            String actual = driver.getTitle();
            assertEquals(expected, actual);

        } else if (succeeded.equalsIgnoreCase("no, long Username")) {

            WebElement errorMsg = driver.findElement(By.cssSelector(".invalid-error"));
            String actual = errorMsg.getText();
            String expected = "Enter a value less than 100 characters long";
            assertEquals(expected, actual);
        } else if (succeeded.equalsIgnoreCase("no, Username taken")) {

            WebElement errorMsg = driver.findElement(By.cssSelector(".invalid-error"));
            String actual = errorMsg.getText();
            String expected = "Great minds think alike - someone already has this username.";
            assertEquals(true, actual.contains(expected));
        } else if (succeeded.equalsIgnoreCase("no, no email")) {

            WebElement errorMsg = driver.findElement(By.cssSelector(".invalid-error"));
            String actual = errorMsg.getText();
            String expected = "An email address must contain a single @.";
            assertEquals(expected, actual);
        }
    }
}
