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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.Assert.assertEquals;
public class RegisterMailChimpStepdefs {
    private WebDriver driver;
    private WebDriverWait wait;
    @Before
    public void SetUp() {
    }
    @After
    public void TearDown() {
        driver.close();
        driver.quit();
    }
    public class WebDriverFactory {
        public static WebDriver createWebDriver(String browser) {
            if (browser.equalsIgnoreCase("brave")) {
                System.setProperty("webdriver.chrome.driver", "G:\\Selenium\\chromedriver_win32\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
                options.addArguments("--remote-allow-origins=*", "ignore-certificate-errors");
                return new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("edge")) {
                return new EdgeDriver();
            } else {
                throw new IllegalArgumentException("Invalid browser type: " + browser);
            }
        }
    }
    @Given("I have opened my {string} and I'm on the MailChimp sign up page")
    public void iHaveOpenedMyAndIMOnTheMailChimpSignUpPage(String browser) {
        driver = WebDriverFactory.createWebDriver(browser);
        driver.manage().window().maximize();
        driver.get("https://login.mailchimp.com/signup/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    @Given("I have entered my {string} and my {string} and my {string}")
    public void iHaveEnteredMyAndMyAndMy(String email, String username, String password) {
        LocalDateTime currentDateTime = LocalDateTime.now();  // Get current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  // Format date and time as a string
        String dateTimeString = currentDateTime.format(formatter);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
        WebElement mail = driver.findElement(By.name("email"));
        mail.sendKeys(email);
        WebElement pwd = driver.findElement(By.name("password"));
        pwd.sendKeys(password);
        if (username.equalsIgnoreCase("kaninis")) {
            WebElement user = driver.findElement(By.name("username"));
            user.sendKeys("");
            user.clear();
            user.sendKeys("kaninis");
        } else {
            WebElement user = driver.findElement(By.name("username"));
            user.sendKeys("");
            user.clear();
            user.sendKeys(username + dateTimeString);
        }
    }
    @When("I click the Sign Up button")
    public void iClickTheSignUpButton() {
        WebElement button = driver.findElement(By.id("create-account-enabled"));
        Actions actions = new Actions(driver);
        actions.moveToElement(button).perform();
        button.click();
    }
    private String waitForError() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".invalid-error")));
        String actual = errorMsg.getText();
        return actual;
    }
    @Then("I have {string} to register")
    public void iHaveToRegister(String succeeded) {
        if (succeeded.equalsIgnoreCase("yes")) {
            wait.until(ExpectedConditions.titleIs("Success | Mailchimp"));
            String expected = "Success | Mailchimp";
            String actual = driver.getTitle();
            assertEquals(expected, actual);
        } else if (succeeded.equalsIgnoreCase("no, long Username")) {
            String actual = waitForError();
            String expected = "Enter a value less than 100 characters long";
            assertEquals(expected, actual);
        } else if (succeeded.equalsIgnoreCase("no, Username taken")) {
            String actual = waitForError();
            String expected = "Great minds think alike - someone already has this username.";
            assertEquals(true, actual.contains(expected));
        } else if (succeeded.equalsIgnoreCase("no, no email")) {
            String actual = waitForError();
            String expected = "An email address must contain a single @.";
            assertEquals(expected, actual);
        }
    }
}