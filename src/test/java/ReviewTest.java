import Base.BaseTest;
import DataProviders.DataProvider;
import Pages.ReviewPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReviewTest extends BaseTest {
    ReviewPage page;
    @BeforeClass
    public void setUpPage() {
        page = new ReviewPage( getDriver(),getWait());
    }
    @Test(description = "Verify the price of the cheapest flight booked",priority = 0)
    public void verifyPrice() {
        int grandTotal = page.getGrandTotal();
        Assert.assertTrue(grandTotal > 0, "Grand total is 0 — possible issue retrieving fare.");
        Assert.assertTrue( getCheapestPrice() > 0, "Cheapest price is 0 — possibly not set correctly.");
        Assert.assertEquals(grandTotal, getCheapestPrice(), "Grand Total is not same as the cheapest price.");
    }

    @Test(description = "Verify Source and destination",dataProvider = "sourceAndDestination" , dataProviderClass = DataProvider.class,priority = 1)
    public void verifySourceAndDestination(String source, String destination, String date){
       String expectedTitle = source + " to " + destination;
       String actualTitle = page.getFlightTitle();
       Assert.assertTrue(actualTitle.toLowerCase().contains(expectedTitle.toLowerCase()),"Expected and Actual Title are different");
    }
    @Test(description = "Verify flight duration",priority = 2)
    public void verifyDurationOfFlight(){
        boolean flightDuration = page.isFlightDurationPresent(getDuration());
        Assert.assertTrue(flightDuration);
    }
   @Test(description = "Verify user can not proceed without selecting an insurance option",priority = 3)
    public void verifyTravelInsurance(){
      page.clickContinueButton();
      String errorMessage = page.getErrorMessage();
      Assert.assertTrue(errorMessage.contains("Please select Yes or No to continue."),"Message not present");
   }
   @Test(description = "Book flight without insurance",priority = 4)
    public void verifyBookingWithoutInsurance(){
      page.clickDoNotInsure();
      page.clickContinueButton();
       int grandTotal = page.getGrandTotal();
       Assert.assertTrue(grandTotal > 0, "Grand total is 0 — possible issue retrieving fare.");
       Assert.assertTrue(getCheapestPrice() > 0, "Cheapest price is 0 — possibly not set correctly.");
       Assert.assertEquals(grandTotal, getCheapestPrice(), "Grand Total is not same as the cheapest price.");
   }

}
