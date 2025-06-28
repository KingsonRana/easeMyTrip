package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.xpath.XPath;

public class PaymentMethodPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    public By paymentDivXpath = By.id("divPaymentMode");
    public By upiXpath = By.className("upi-dtl");
    public By cardXpath = By.className("card-dtl");
    public By netBankingXpath = By.className("netbanking-dtl");
    public By walletXpath = By.className("wallet-dtl");
    public By emiXpath = By.className("emi-dtl");
    public By rewardsXpath = By.className("TwidPay-dtl");
    public By giftCardXpath = By.className("giftcard-dtl");
    public By payLaterXpath = By.className("epay_col");
    public By googlePayXpath = By.className("googlepay-dtl");
    public PaymentMethodPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(this.driver);
    }
    public void moveToElement(By elementXpath){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementXpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public boolean elementIsVisible(By xpath){
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
            return element.isDisplayed();
        }catch (Exception e){
            return false;
        }

    }

}
