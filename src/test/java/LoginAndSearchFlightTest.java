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
    public void login(String countryCode, String phoneNumber,String email){
        System.out.println("Country code is " + countryCode + " Phone number is " + phoneNumber);
    }
    @Test(description = "Search Flight", dependsOnMethods = "login", dataProvider = "sourceAndDestination" , dataProviderClass = DataProvider.class)
    public void search(String source, String destination, String date) {
        page.enterSource(source);
        page.enterDestination(destination);
        page.enterDepartureDate(date);
        page.searchFlight();

        int resultCount = page.getResultCount();
        Assert.assertTrue(resultCount > 0, "No flight results found.");

        int matchingResultCount = page.getMatchingFlightCount(source, destination);
        Assert.assertTrue(matchingResultCount > 0, "No matching flights for source and destination.");

        int percentage = CommonMethods.calculatePercentage(matchingResultCount, resultCount);
        Assert.assertTrue(percentage > 90, "Matching percentage should be greater than 90");
    }

    @Test(description = "Verify listed cheapest flight is not more than the 'CHEAPEST' price shown", dependsOnMethods = "search",priority = 0)
    public void verifyCheapestFlightValid() {
        int expectedCheapestFlight = page.getExpectedCheapestPrice();
        int actualCheapestPrice = page.getActualCheapestPrice();
        Assert.assertTrue(expectedCheapestFlight > 0, "Expected cheapest price is 0 — possible failure in fetching expected fare.");
        Assert.assertTrue(actualCheapestPrice > 0, "Actual cheapest price is 0 — possible failure in scraping actual fares.");
        Assert.assertTrue(actualCheapestPrice <= expectedCheapestFlight,
                String.format("Actual cheapest price (%d) is greater than expected cheapest price (%d)",
                        actualCheapestPrice, expectedCheapestFlight));
    }

    @Test(description = "Verify sorting by Highest price is working",dependsOnMethods = "search",priority = 1)
    public void sortByPriceHighest() throws InterruptedException {
        boolean isSorted = page.sortByHighest();
        Assert.assertTrue(isSorted, "Price is not sorted from highest to lowest");
    }
    @Test(description = "Book the cheapest flight",dependsOnMethods = {"sortByPriceHighest"}, alwaysRun = true)
    public void bookCheapestFlight(){
        setCheapestPrice(page.getCheapestExactMatchFlight());
        setDuration(page.getFlightDuration());
        page.clickBookNow();

    }


}
