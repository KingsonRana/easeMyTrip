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
    static int cheapestPrice;
    @BeforeClass
    public void setUpPage() {
        page = new DashboardPage( getDriver(),getWait());
    }
    @Test(description = "Log in to you Account",dataProvider = "userCredentials", dataProviderClass = DataProvider.class)
    public void login(String countryCode, String phoneNumber){
        System.out.println("Country code is " + countryCode + " Phone number is " + phoneNumber);
    }
    @Test(description = "Search Flight", dependsOnMethods = "login", dataProvider = "sourceAndDestination" , dataProviderClass = DataProvider.class)
    public void search(String source, String destination, String date) throws InterruptedException {
        System.out.println("Source = " + source);
        System.out.println("Destination = " +destination);
        System.out.println("Date = " + date);
        page.enterSource(source);
        page.enterDestination(destination);
        page.enterDepartureDate(date);
        page.searchFlight();
        int resultCount = page.getResultCount();
        int matchingResultCount = page.getMatchingFlightCount(source,destination);
        System.out.println("Result count = " + resultCount);
        System.out.println("Matching result count = " + matchingResultCount);
        int percentage = CommonMethods.calculatePercentage(matchingResultCount,resultCount);
        System.out.println("Percentage of accurate result = " + percentage);
        Assert.assertTrue(percentage > 90, "Percentage should be greater than 60");
    }
    @Test(description = "Verify listed cheapest flight is not more than the 'CHEAPEST' price shown", dependsOnMethods = "search",priority = 0)
    public void selectTheCheapestFlight(){
      int expectedCheapestFlight = page.getExpectedCheapestPrice();
      int actualCheapestPrice = page.getAcutalCheapestPrice();
        System.out.println("Expected cheapest price = " + expectedCheapestFlight);
        System.out.println("Actual cheapest price = " + actualCheapestPrice);
        cheapestPrice = actualCheapestPrice;
        Assert.assertTrue(actualCheapestPrice <= expectedCheapestFlight, "Actual cheapest price less that expected cheapest flight");
    }
    @Test(description = "Verify sorting by Highest price is working",dependsOnMethods = "search",priority = 1)
    public void sortByPriceHighest() throws InterruptedException {
        boolean isSorted = page.sortByHighest();
        Assert.assertTrue(isSorted, "Price is not sorted from highest to lowest");
    }
    @Test(description = "Book the cheapest flight",dependsOnMethods = {"sortByPriceHighest"}, alwaysRun = true)
    public void bookCheapestFlight() throws InterruptedException {
        page.bookCheapestFlightByPrice();
        Thread.sleep(10000);
    }


}
