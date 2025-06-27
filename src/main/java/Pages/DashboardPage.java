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
    private String selectedSource;
    private String selectedDestination;
    private String cheapestFlightPrice;


    public DashboardPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(this.driver);
    }

    public void enterPhoneNumber(String phoneNumber) {
        try {
            phoneNumberTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Enter Mobile Number']")));
            phoneNumberTextBox.sendKeys(phoneNumber);

            continueButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@data-cy='continueBtn']")));
            continueButton.click();
        } catch (Exception e) {
            System.out.println("Failed to enter phone number or click continue: " + e.getMessage());
        }
    }

    public void enterSource(String source) {
        this.selectedSource = source;
        try {
            WebElement fromDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("frmcity")));
            replicateHumaneMouseMovement(fromDiv);

            fromTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_FromSector_show")));
            replicateHumaneMouseMovement(fromTextBox);
            fromTextBox.sendKeys(source);

            WebElement dropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//li[contains(.,'" + source + "')]")));
            replicateHumaneMouseMovement(dropDown);
        } catch (Exception e) {
            System.out.println("Failed to enter source location: " + e.getMessage());
        }
    }

    public void enterDestination(String destination) {
        this.selectedDestination = destination;
        try {
            toTextBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_Editbox13_show")));
            replicateHumaneMouseMovement(toTextBox);
            toTextBox.sendKeys(destination);

            WebElement dropDown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//li[contains(.,'" + destination + "')]")));
            replicateHumaneMouseMovement(dropDown);
        } catch (Exception e) {
            System.out.println("Failed to enter destination location: " + e.getMessage());
        }
    }

    public void enterDepartureDate(String formattedDate) {
        try {
            WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//li[contains(@id,'" + formattedDate + "')]")));
            replicateHumaneMouseMovement(dateElement);
        } catch (Exception e) {
            System.out.println("Failed to select departure date: " + e.getMessage());
        }
    }

    public void searchFlight() {
        try {
            submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("srchBtnSe")));
            replicateHumaneMouseMovement(submit);
        } catch (Exception e) {
            System.out.println("Search button not found or not clickable: " + e.getMessage());
        }
    }

    public int getResultCount() {
        try {
            CommonMethods.waitUntilPageIsFullyLoaded(driver);
            flightsResult = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.cssSelector("div.row.no-margn.fltResult.ng-scope")));
            return flightsResult.size();
        } catch (Exception e) {
            System.out.println("Failed to retrieve flight results: " + e.getMessage());
            return 0;
        }
    }

    public int getMatchingFlightCount(String source, String destination) {
        try {
            String xpath = String.format(
                    "//div[contains(@class,'fltResult') and contains(.,'%s') and contains(.,'%s')]",
                    source, destination);
            List<WebElement> list = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
            return list.size();
        } catch (Exception e) {
            System.out.println("Failed to retrieve matching flights for " + source + " to " + destination + ": " + e.getMessage());
            return 0;
        }
    }

    public int getExpectedCheapestPrice() {
        try {
            String xpath = "//div[contains(@id,'QUC_TOTALFARE') and contains(.,'Cheapest')]//div[contains(@class,'_fntsm')]";
            expectedCheapestFare = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            String rawText = expectedCheapestFare.getText();
            String pricePart = rawText.split("\\|")[0].trim();
            return Integer.parseInt(pricePart.replace(",", ""));
        } catch (Exception e) {
            System.out.println("Could not fetch expected cheapest fare: " + e.getMessage());
            return 0;
        }
    }

    public int getActualCheapestPrice() {
        try {
            String xpath = "//div[contains(@class,'fareflex')]//span[contains(@id,'spnPrice')]";
            List<WebElement> priceList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
            int cheapest = Integer.MAX_VALUE;

            for (WebElement prices : priceList) {
                int fPrice = parsePrice(prices.getText());
                if (fPrice < cheapest) {
                    cheapest = fPrice;
                }
            }

            return cheapest == Integer.MAX_VALUE ? 0 : cheapest;
        } catch (Exception e) {
            System.out.println("Failed to get actual cheapest price: " + e.getMessage());
            return 0;
        }
    }

    public int getCheapestExactMatchFlight() {
        try {
            String xpath = buildFlightXPath() + "//div[contains(@class,'fareflex')]//span[contains(@id,'spnPrice')]";
            List<WebElement> priceList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
            int cheapest = Integer.MAX_VALUE;

            for (WebElement prices : priceList) {
                String priceText = prices.getText().trim();
                int fPrice = parsePrice(priceText);
                if (fPrice < cheapest) {
                    cheapest = fPrice;
                    cheapestFlightPrice = priceText;
                }
            }

            return cheapest == Integer.MAX_VALUE ? 0 : cheapest;
        } catch (Exception e) {
            System.out.println("Failed to get cheapest exact match flight: " + e.getMessage());
            return 0;
        }
    }

    public void clickBookNow() {
        try {
            String xpath = buildFlightXPathWithPrice() + "//button[contains(text(),'Book Now')]";
            bookNowButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            replicateHumaneMouseMovement(bookNowButton);
        } catch (Exception e) {
            System.out.println("Failed to click 'Book Now' button: " + e.getMessage());
        }
    }

    public String getFlightDuration() {
        try {
            String xpath = buildFlightXPathWithPrice() + "//span[contains(@class,'dura_md')]";
            WebElement durationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return durationElement.getText().trim();
        } catch (Exception e) {
            System.out.println("Flight duration element not found: " + e.getMessage());
            return "";
        }
    }

    public boolean sortByHighest() throws InterruptedException {
        try {
            sortByButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'_regflx') and contains(.,'Other Sort')]")));
            replicateHumaneMouseMovement(sortByButton);

            sortByHighestButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("QUC_HIGHESTPRICE")));
            replicateHumaneMouseMovement(sortByHighestButton);

            String xpath = "//div[contains(@class,'slash_price')]//span[contains(@class,'cut-pric-v3')]";
            List<WebElement> priceList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));

            List<Integer> priceListInteger = new ArrayList<>();
            for (WebElement prices : priceList) {
                int fPrice = parsePrice(prices.getText());
                priceListInteger.add(fPrice);
            }

            return CommonMethods.isSortedByHighest(priceListInteger);
        } catch (Exception e) {
            System.out.println("Failed to sort flights by highest price: " + e.getMessage());
            return false;
        }
    }

    private void replicateHumaneMouseMovement(WebElement element){
        actions.moveToElement(element).pause(Duration.ofMillis(200)).click().perform();
    }

    private String buildFlightXPathWithPrice() {
        return String.format(
                "//div[contains(@class,'fltResult') and contains(.,'%s') and contains(.,'%s') and contains(.,'%s')]",
                selectedSource, selectedDestination, cheapestFlightPrice
        );
    }

    private String buildFlightXPath() {
        return String.format("//div[contains(@class,'fltResult') and contains(.,'%s') and contains(.,'%s')]",
                selectedSource, selectedDestination);
    }
    private int parsePrice(String priceText) {
        return Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    }

}
