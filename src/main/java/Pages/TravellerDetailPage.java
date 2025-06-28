package Pages;

import Utility.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TravellerDetailPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    WebElement continueButton;
    WebElement emailInput;
    WebElement phoneInput;
    WebElement errorDiv;
    WebElement firstNameField;
    WebElement lastNameField;
    WebElement titleDropDown;
    WebElement skipSeatSelection;
    WebElement skipToPayment;
    public By travllerXpath = By.xpath("//div[contains(@class,'po-re') and contains(.,'Traveller Details')]");
    public TravellerDetailPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(this.driver);
    }
    public void clickContinueButton() {
        try {
            continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("spnTransaction")));
            replicateHumaneMouseMovement(continueButton);
        } catch (Exception e) {
            System.out.println("Continue button not found or not clickable.");
        }
    }
    public boolean isEmailFieldEmpty() {
        try {
            emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtEmailId")));
            String classAttr = emailInput.getAttribute("class");
            return classAttr != null && classAttr.contains("ng-empty");
        } catch (Exception e) {
            System.out.println("Could not verify email field state: " + e.getMessage());
            return false;
        }
    }
    public boolean enterEmailAndVerifyClassChange(String email) {
        try {
            emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtEmailId")));
            emailInput.clear();
            emailInput.sendKeys(email);
            String classAttr = emailInput.getAttribute("class");
            return classAttr != null && classAttr.contains("ng-not-empty");
        } catch (Exception e) {
            System.out.println("Failed to verify email field class change: " + e.getMessage());
            return false;
        }
    }

    public boolean isPhoneNumberFieldEmpty() {
        try {
            phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtCPhone")));
            String classAttr = phoneInput.getAttribute("class");
            return classAttr != null && classAttr.contains("ng-empty");
        } catch (Exception e) {
            System.out.println("Could not verify phone number field state: " + e.getMessage());
            return false;
        }
    }
    public boolean enterPhoneAndVerifyClassChange(String phoneNumber) {
        try {
            phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtCPhone")));
            phoneInput.clear();
            phoneInput.sendKeys(phoneNumber);
            String classAttr = phoneInput.getAttribute("class");
            return classAttr != null && classAttr.contains("ng-not-empty");
        } catch (Exception e) {
            System.out.println("Failed to verify phone field class change: " + e.getMessage());
            return false;
        }
    }
    public boolean isErrorMessageDisplayed(String message) {
        try {
            WebElement error = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//span[contains(@id,'spanErrorAdult') and contains(text(),'" + message + "')]")
            ));
            System.out.println(error.getText());
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public void selectTitle(String title){
       try{
           titleDropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("titleAdult0")));
           replicateHumaneMouseMovement(titleDropDown);
           Select dropDown = new Select(titleDropDown);
           dropDown.selectByVisibleText(title);
       }catch (Exception e){
           System.out.println("Failed to select title");
       }
    }

    public void enterAdultFirstName(String firstName) {
         firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtFNAdult0")));
         firstNameField.clear();
         firstNameField.sendKeys(firstName);
    }
    public void enterAdultLastName(String lastName) {
        lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtLNAdult0")));
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }
    public boolean isErrorVisible() {
        try {
            errorDiv = driver.findElement(By.id("divErrorAdult0"));
            String style = errorDiv.getAttribute("style");
            return style != null && !style.contains("display: none");
        } catch (Exception e) {
            return false;
        }
    }
     public void skipSeatSelection(){
        try{
            skipSeatSelection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'seatPo_0')]//a[contains(text(),'Skip')]")));
            replicateHumaneMouseMovement(skipSeatSelection);
        } catch (Exception e) {
            System.out.println("Skip button not found or is not clickable");
        }
    }
    public void skipToPayment(){
        try{
            skipToPayment = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("skipPop")));
            replicateHumaneMouseMovement(skipToPayment);
            CommonMethods.waitForLoaderToDisappear(wait);
        } catch (Exception e) {
            System.out.println("Skip to payment button not found or is not clickable");
        }
    }
    public boolean verifyAndPayButtonIsVisible() {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(text(),'Verify & Pay')]")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean verifyUserEmailAddress(String email) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'"+email+"')]")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean verifyUserContact(String contact) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(text(),'"+contact+"')]")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean verifyUserFullName(String fullName) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(@class,'fnt-g') and contains(text(),'"+fullName+"')]")));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void replicateHumaneMouseMovement(WebElement element){
        actions.moveToElement(element).pause(Duration.ofMillis(200)).click().perform();
        CommonMethods.waitUntilPageIsFullyLoaded(driver);
    }

    public void moveToElement(By elementXpath){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementXpath));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }



}
