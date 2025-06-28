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
        page = new DashboardPage( getDriver(),getWait());
    }
    @Test(description = "Log in to you Account",dataProvider = "userCredentials", dataProviderClass = DataProvider.class)
    public void login(String countryCode, String phoneNumber,String email) throws InterruptedException {
        System.out.println("Country code is " + countryCode + " Phone number is " + phoneNumber);
        page.clickSignInPanel();
        page.clickCustomerLogin();
        page.enterPhoneNumber(phoneNumber);
        Assert.assertTrue(page.isProfileBoxVisible(),"Profile box should be visible");
        page.declineGoingToProfile();
    }
    @Test(description = "Search for Flight and verify the page url ", dependsOnMethods = "login", dataProvider = "sourceAndDestination" , dataProviderClass = DataProvider.class)
    public void searchFlightAnVerifyTheURl(String source, String destination, String date) {
        setSource(source);
        setDestination(destination);
        setDepartureDate(date);
        page.enterSource(source);
        page.enterDestination(destination);
        page.enterDepartureDate(date);
        page.searchFlight();
        String pageUrl = page.getPageUrl();
        Assert.assertTrue(pageUrl.contains("https://flight.easemytrip.com/FlightList/Index"),"Url of flight list page is different");
    }
    @Test(description = "Verify the matching result", dependsOnMethods = "searchFlightAnVerifyTheURl")
    public void verifyMatchingResultPercentage(){
        int resultCount = page.getResultCount();
        Assert.assertTrue(resultCount > 0, "No flight results found.");
        int matchingResultCount = page.getMatchingFlightCount(getSource(), getDestination());
        Assert.assertTrue(matchingResultCount > 0, "No matching flights for source and destination.");
        int percentage = CommonMethods.calculatePercentage(matchingResultCount, resultCount);
        Assert.assertTrue(percentage > 90, "Matching percentage should be greater than 90");
    }

    @Test(description = "Verify listed cheapest flight is not more than the 'CHEAPEST' price shown", dependsOnMethods = "searchFlightAnVerifyTheURl",priority = 0)
    public void verifyCheapestFlightValid() {
        int expectedCheapestFlight = page.getExpectedCheapestPrice();
        int actualCheapestPrice = page.getActualCheapestPrice();
        System.out.println("Expected cheaptest price is " + expectedCheapestFlight);
        System.out.println("Actual cheapest price is " + actualCheapestPrice) ;
        Assert.assertTrue(expectedCheapestFlight > 0, "Expected cheapest price is 0 — possible failure in fetching expected fare.");
        Assert.assertTrue(actualCheapestPrice > 0, "Actual cheapest price is 0 — possible failure in scraping actual fares.");
        Assert.assertTrue(actualCheapestPrice <= expectedCheapestFlight,
                String.format("Actual cheapest price (%d) is greater than expected cheapest price (%d)",
                        actualCheapestPrice, expectedCheapestFlight));
    }

    @Test(description = "Verify sorting by Highest price is working",dependsOnMethods = "searchFlightAnVerifyTheURl",priority = 1)
    public void sortByPriceHighest() throws InterruptedException {
        boolean isSorted = page.sortByHighest();
        Assert.assertTrue(isSorted, "Price is not sorted from highest to lowest");
    }
    @Test(description = "Book the cheapest flight and verify the PageUrl",dependsOnMethods = {"sortByPriceHighest"}, alwaysRun = true)
    public void bookCheapestFlightAndVerifyPageUrl(){
        setCheapestPrice(page.getCheapestExactMatchFlight());
        setDuration(page.getFlightDuration());
        page.clickBookNow();
        String pageUrl = page.getPageUrl();
        Assert.assertTrue(pageUrl.contains("https://flight.easemytrip.com/Review/CheckOut"),"URL of review page is different");
    }


}
