package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class ReviewPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    WebElement grandTotal;
    WebElement flightTitle;
    WebElement durationElement;
    WebElement continueButton;
    WebElement errorMessage;
    WebElement doNotInsure;
    public ReviewPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(this.driver);
    }
    public int getGrandTotal() {
        try {
            WebElement DiscountDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@id,'divFareSummary')]//div[contains(.,'Discount')]")));
            grandTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("spnGrndTotal")));
            return Integer.parseInt(grandTotal.getText().trim().replace(",", ""));
        } catch (Exception e) {
            System.out.println("Grand total element not found or unreadable.");
            return 0;
        }
    }

    public String getFlightTitle() {
        try {
            flightTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.flighttitle")));
            return flightTitle.getText();
        } catch (Exception e) {
            System.out.println("Flight title element not found.");
            return "";
        }
    }

    public boolean isFlightDurationPresent(String expectedDuration) {
        try {
            durationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'flightinfo')]/strong")));
            return durationElement.getText().contains(expectedDuration);
        } catch (Exception e) {
            System.out.println("Flight duration element not found.");
            return false;
        }
    }

    public void clickContinueButton() {
        try {
            continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("spnVerifyEmail")));
            replicateHumaneMouseMovement(continueButton);
        } catch (Exception e) {
            System.out.println("Continue button not found or not clickable.");
        }
    }

    public String getErrorMessage() {
        try {
            errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("DivInsuAlert")));
            return errorMessage.getText().trim();
        } catch (Exception e) {
            System.out.println("Error message element not found.");
            return "";
        }
    }
    public void clickDoNotInsure(){
     try{
         String donNoInsureXpath = "//label[contains(.,'No, I do not want to insure my trip') and not(contains(.,'.'))]";
         doNotInsure = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(donNoInsureXpath)));
         replicateHumaneMouseMovement(doNotInsure);
     }catch (Exception e){
         System.out.println("Do Not Insure button not found or is not clickable");
     }
    }

    private void replicateHumaneMouseMovement(WebElement element){
        actions.moveToElement(element).pause(Duration.ofMillis(200)).click().perform();
    }

}
