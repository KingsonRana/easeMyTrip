package Base;

import Utility.CommonMethods;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static String firstName;
    private static String lastName;
    private static String emailId;
    private static String phoneNumber;
    private static int cheapestPrice;
    private static String duration;
    @BeforeSuite
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Disable notifications
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2); // 2 = Block
        options.setExperimentalOption("prefs", prefs);

        // Anti-automation & appearance settings
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        // Hide webdriver flag
        ((JavascriptExecutor) driver).executeScript(
                "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
        );

        driver.get("https://www.easemytrip.com");
        CommonMethods.waitUntilPageIsFullyLoaded(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        System.out.println("FirstName " + firstName);
        System.out.println("Last name " + lastName);
        System.out.println("Email " + emailId);
        System.out.println("PhoneNUmber "+ phoneNumber);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }
    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        BaseTest.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        BaseTest.lastName = lastName;
    }

    public static String getEmailId() {
        return emailId;
    }

    public static void setEmailId(String emailId) {
        BaseTest.emailId = emailId;
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber) {
        BaseTest.phoneNumber = phoneNumber;
    }

    public static int getCheapestPrice() {
        return cheapestPrice;
    }

    public static void setCheapestPrice(int cheapestPrice) {
        BaseTest.cheapestPrice = cheapestPrice;
    }

    public static String getDuration() {
        return duration;
    }

    public static void setDuration(String duration) {
        BaseTest.duration = duration;
    }


}
