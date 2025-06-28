package tests;

import Base.BaseTest;
import DataProviders.DataProvider;
import Pages.DashboardPage;
import Utility.CommonMethods;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginAndSearchFlightTest extends BaseTest
{
    DashboardPage page;
    @BeforeClass
    public void setUpPage() {
        page = new DashboardPage(getDriver(), getWait());
    }
    @Test(description = "Login to the user account", dataProvider = "userCredentials", dataProviderClass = DataProvider.class)
    public void login(String countryCode, String phoneNumber, String email) throws InterruptedException {
        System.out.println("Country code is " + countryCode + " Phone number is " + phoneNumber);
        page.clickSignInPanel();
        page.clickCustomerLogin();
        page.enterPhoneNumber(phoneNumber);
        Assert.assertTrue(page.isProfileBoxVisible(), "Profile box should be visible");
        page.declineGoingToProfile();
    }

    @Test(description = "Search for flights and validate the results page URL", dependsOnMethods = "login", dataProvider = "sourceAndDestination", dataProviderClass = DataProvider.class)
    public void searchFlightAndValidateUrl(String source, String destination, String date) {
        setSource(source);
        setDestination(destination);
        setDepartureDate(date);
        page.enterSource(source);
        page.enterDestination(destination);
        page.enterDepartureDate(date);
        page.searchFlight();
        String pageUrl = page.getPageUrl();
        Assert.assertTrue(pageUrl.contains("https://flight.easemytrip.com/FlightList/Index"), "URL of flight list page is incorrect");
    }

    @Test(description = "Verify that the matching result percentage is greater than 90%", dependsOnMethods = "searchFlightAndValidateUrl")
    public void verifyFlightMatchAccuracy() {
        int resultCount = page.getResultCount();
        Assert.assertTrue(resultCount > 0, "No flight results found.");
        int matchingResultCount = page.getMatchingFlightCount(getSource(), getDestination());
        Assert.assertTrue(matchingResultCount > 0, "No matching flights for source and destination.");
        int percentage = CommonMethods.calculatePercentage(matchingResultCount, resultCount);
        Assert.assertTrue(percentage > 90, "Matching percentage should be greater than 90");
    }

    @Test(description = "Validate that the listed cheapest flight is not priced above the expected 'CHEAPEST' fare", dependsOnMethods = "searchFlightAndValidateUrl", priority = 0)
    public void validateCheapestFlightPrice() {
        int expectedCheapestFlight = page.getExpectedCheapestPrice();
        int actualCheapestPrice = page.getActualCheapestPrice();
        System.out.println("Expected cheapest price is " + expectedCheapestFlight);
        System.out.println("Actual cheapest price is " + actualCheapestPrice);
        Assert.assertTrue(expectedCheapestFlight > 0, "Expected cheapest price is 0 — possible failure in fetching expected fare.");
        Assert.assertTrue(actualCheapestPrice > 0, "Actual cheapest price is 0 — possible failure in scraping actual fares.");
        Assert.assertTrue(actualCheapestPrice <= expectedCheapestFlight,
                String.format("Actual cheapest price (%d) is greater than expected cheapest price (%d)",
                        actualCheapestPrice, expectedCheapestFlight));
    }

    @Test(description = "Verify that sorting by highest price works correctly", dependsOnMethods = "searchFlightAndValidateUrl", priority = 1)
    public void verifySortByHighestPrice() throws InterruptedException {
        boolean isSorted = page.sortByHighest();
        Assert.assertTrue(isSorted, "Price is not sorted from highest to lowest");
    }

    @Test(description = "Book the cheapest flight and validate the checkout page URL", dependsOnMethods = {"verifySortByHighestPrice"}, alwaysRun = true)
    public void bookFlightAndVerifyCheckoutUrl() {
        setCheapestPrice(page.getCheapestExactMatchFlight());
        setDuration(page.getFlightDuration());
        page.clickBookNow();
        String pageUrl = page.getPageUrl();
        Assert.assertTrue(pageUrl.contains("https://flight.easemytrip.com/Review/CheckOut"), "URL of review page is incorrect");
    }
}
