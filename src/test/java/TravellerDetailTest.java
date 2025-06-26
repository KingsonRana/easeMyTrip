import Base.BaseTest;
import Pages.BookingPage;
import Pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TravellerDetailTest extends BaseTest {
    BookingPage page;
    @BeforeClass
    public void setUpPage() {
        page = new BookingPage( getDriver(),getWait());
    }
    @Test(description = "Verify the price of the cheapest flight booked")
    public void verifyPrice(){
       int grandTotal = page.getGrandTotal();
        Assert.assertEquals(LoginAndSearchFlightTest.cheapestPrice,grandTotal,"Grand Total is not same");
    }

}
