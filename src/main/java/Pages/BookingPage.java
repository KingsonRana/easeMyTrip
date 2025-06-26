package Pages;

import Utility.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class BookingPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;
    WebElement grandTotal;
    public BookingPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(this.driver);
    }
    public int getGrandTotal(){
        WebElement DiscountDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,'divFareSummary')]//div[contains(.,'Discount')]")));
        grandTotal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("spnGrndTotal")));
        return Integer.parseInt(grandTotal.getText().trim().replace(",",""));
    }

    private void replicateHumaneMouseMovement(WebElement element){
        actions.moveToElement(element).pause(Duration.ofMillis(200)).click().perform();
    }

}
