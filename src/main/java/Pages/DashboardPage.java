package Pages;

import Utility.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashboardPage {
    WebDriver driver;
    WebDriverWait wait;
    WebElement fromTextBox;
    WebElement toTextBox;
    WebElement submit;
    Actions actions;
    WebElement phoneNumberTextBox;
    WebElement continueButton;
    WebElement expectedCheapestFare;
    WebElement sortByButton;
    WebElement sortByHighestButton;
    List<WebElement> flightsResult;
    WebElement bookNowButton;
    private String cheapestFlightPrice;


    public DashboardPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(this.driver);
    }

    public void enterPhoneNumber(String phoneNumber) throws InterruptedException {
        phoneNumberTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Mobile Number']")));
        phoneNumberTextBox.sendKeys(phoneNumber);
        continueButton= wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-cy='continueBtn']")));
        continueButton.click();
    }

    public void enterSource(String source) throws InterruptedException {
        try{
        WebElement fromDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("frmcity")));
        replicateHumaneMouseMovement(fromDiv);
        fromTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_FromSector_show")));
        replicateHumaneMouseMovement(fromTextBox);
        fromTextBox.sendKeys(source);
        WebElement dropDown = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath( "//li[contains(.,'" + source + "')]")));
        replicateHumaneMouseMovement(dropDown);}catch (Exception e){
            Thread.sleep(180000);
            System.out.println(e);
        }
    }

    public void enterDestination(String destination){
        toTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_Editbox13_show")));
        replicateHumaneMouseMovement(toTextBox);
        toTextBox.sendKeys(destination);
        WebElement dropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(.,'" + destination + "')]")));
        replicateHumaneMouseMovement(dropDown);

    }
    public void enterDepartureDate(String formattedDate){
        WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@id,'"+formattedDate+"')]")));
        replicateHumaneMouseMovement(dateElement);
    }

    public void searchFlight() throws InterruptedException {
        submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("srchBtnSe")));
        replicateHumaneMouseMovement(submit);
    }
    public int getResultCount(){
        CommonMethods.waitUntilPageIsFullyLoaded(driver);
        flightsResult= wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.row.no-margn.fltResult.ng-scope")));
        return flightsResult.size();
    }
    public int getMatchingFlightCount(String source, String destination) {
        String xpath = String.format("//div[contains(@class,'fltResult') and contains(.,'%s') and contains(.,'%s')]",
                source, destination);
        List<WebElement> list = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath))
        );
        return list.size();
    }
    public int getExpectedCheapestPrice(){
        String xpath = "//div[contains(@id,'QUC_TOTALFARE') and contains(.,'Cheapest')]//div[contains(@class,'_fntsm')]";
        expectedCheapestFare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        String rawText = expectedCheapestFare.getText();
        String pricePart = rawText.split("\\|")[0].trim();
        System.out.println(rawText + " " + pricePart
        );
        return Integer.parseInt(pricePart);
    }
    public int getAcutalCheapestPrice(){
        String xpath = "//div[contains(@class,'fareflex')]//span[contains(@id,'spnPrice')]";
        List<WebElement> priceList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
        int cheapest = Integer.MAX_VALUE;
        for(WebElement prices: priceList) {
            String price = prices.getText().trim();
            int fPrice = Integer.parseInt(price.replace(",",""));
            if(fPrice<cheapest){
                cheapest = fPrice;
                cheapestFlightPrice = price;
            }
        }
        return cheapest;
    }
    public boolean sortByHighest() throws InterruptedException {
        sortByButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'_regflx') and contains(.,'Other Sort')]")));
        replicateHumaneMouseMovement(sortByButton);
        sortByHighestButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("QUC_HIGHESTPRICE")));
        replicateHumaneMouseMovement(sortByHighestButton);
        String xpath = "//div[contains(@class,'slash_price')]//span[contains(@class,'cut-pric-v3')]";
        List<WebElement> priceList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
        List<Integer> priceListInteger = new ArrayList<>();
        for(WebElement prices: priceList) {
            int fPrice = Integer.parseInt(prices.getText().trim().replace(",",""));
            priceListInteger.add(fPrice);
        }
     return CommonMethods.isSortedByHighest(priceListInteger);
    }
    public void bookCheapestFlightByPrice() throws InterruptedException {
        String xpath = "(//div[contains(@class,'fltResult') and contains(.,'" + cheapestFlightPrice + "')]//button[contains(text(),'Book Now')])";
        System.out.println(xpath);
      bookNowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
      replicateHumaneMouseMovement(bookNowButton);
    }
    private void replicateHumaneMouseMovement(WebElement element){
        actions.moveToElement(element).pause(Duration.ofMillis(200)).click().perform();
    }


}
