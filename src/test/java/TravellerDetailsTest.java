import Base.BaseTest;
import DataProviders.DataProvider;
import Pages.TravellerDetailPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TravellerDetailsTest extends BaseTest {
    TravellerDetailPage page;

    @BeforeClass
    public void setUpPage() {
        page = new TravellerDetailPage(getDriver(), getWait());
    }

    @Test(description = "Verify Email Id is required", priority = 0)
    public void verifyEmailFieldIsRequired() {
        page.clickContinueButton();  // assume this triggers validation
        boolean isEmpty = page.isEmailFieldEmpty();
        Assert.assertTrue(isEmpty, "Email field should be marked as empty.");
    }

    @Test(description = "Enter email id", dataProvider = "userCredentials", dataProviderClass = DataProvider.class, priority = 1)
    public void verifyEmailFieldClassChangesAfterInput(String countryCode, String phoneNumber, String email) {
        setEmailId(email);
        boolean isUpdated = page.enterEmailAndVerifyClassChange(email);
        Assert.assertTrue(isUpdated, "Email field class did not update to 'ng-not-empty' after input.");
    }

    @Test(description = "Verify phone number is required", priority = 2)
    public void verifyPhoneNumberFieldValidation() {
        page.clickContinueButton();
        boolean isEmpty = page.isPhoneNumberFieldEmpty();
        Assert.assertTrue(isEmpty, "Phone number field should be marked as empty.");
    }

    @Test(description = "Enter Phone Number ", dataProvider = "userCredentials", dataProviderClass = DataProvider.class, priority = 3)
    public void verifyPhoneFieldClassChangesAfterInput(String countryCode, String phoneNumber, String email) {
        setPhoneNumber(phoneNumber);
        boolean isUpdated = page.enterPhoneAndVerifyClassChange(phoneNumber);
        Assert.assertTrue(isUpdated, "Phone field class did not update to 'ng-not-empty' after input.");
    }

    @Test(description = "Verify alert message on empty title", priority = 4)
    public void verifyTitleValidationError() {
        page.selectTitle("Title");
        page.clickContinueButton();
        boolean isErrorVisible = page.isErrorVisible();
        Assert.assertTrue(isErrorVisible, "Error message should be shown when a valid title is not entered");
    }

    @Test(description = "Verify alert message not displayed when title is not empty", priority = 5)
    public void verifyNoErrorDisplayedWhenTitleEntered() {
        setSalutation("Mr");
        page.selectTitle("Mr");
        boolean isErrorVisible = page.isErrorVisible();
        Assert.assertFalse(isErrorVisible, "Error message should not be shown when a valid title is entered");
    }

    @Test(description = "Verify alert message displayed when first name is empty", priority = 6)
    public void verifyFirstNameValidationError() throws InterruptedException {
        page.clickContinueButton();
        Thread.sleep(1000);
        boolean isErrorVisible = page.isErrorVisible();
        Assert.assertTrue(isErrorVisible, "Error message should be shown when a valid first name is not entered");

    }

    @Test(description = "Verify alert message when first name is not empty", priority = 7, dataProvider = "travellerDetails", dataProviderClass = DataProvider.class)
    public void verifyNoErrorShownWhenFirstNameEntered(String firstName, String lastName) {
        setFirstName(firstName);
        page.enterAdultFirstName(firstName);
        boolean isErrorVisible = page.isErrorVisible();
        Assert.assertTrue(isErrorVisible, "Error message should not be shown when a valid first name is entered");
    }

    @Test(description = "Verify alert message on empty last name", priority = 8)
    public void verifyLastNameValidationError() throws InterruptedException {
        page.clickContinueButton();
        Thread.sleep(1000);
        boolean isErrorVisible = page.isErrorVisible();
        Assert.assertTrue(isErrorVisible, "Error message should be shown when a valid last name is not entered");

    }

    @Test(description = "Verify alert message when last name is entered", priority = 9, dataProvider = "travellerDetails", dataProviderClass = DataProvider.class)
    public void verifyNoErrorShownWhenLastNameEntered(String firstName, String lastName) throws InterruptedException {
        setLastName(lastName);
        page.enterAdultLastName(lastName);
        page.clickContinueButton();
        boolean isErrorVisible = page.isErrorVisible();
        Assert.assertFalse(isErrorVisible, "Error message should not be shown when a valid last name is entered");
    }

    @Test(description = "skip seat selection", dependsOnMethods = "verifyNoErrorShownWhenLastNameEntered",priority=10)
    public void skipSeatSelection() {
        page.skipSeatSelection();
        page.skipToPayment();
        Assert.assertTrue(page.verifyAndPayButtonIsVisible(),"Button should be visible");
    }

    @Test(description ="Verify Travellers detials", priority = 11,dependsOnMethods="skipSeatSelection")
    public void verifyTravellerDetail() throws InterruptedException {
      boolean isEmailVisible = page.verifyUserEmailAddress(getEmailId());
      Assert.assertTrue(isEmailVisible,"Email id is not visible");

      boolean isContactVisible = page.verifyUserContact(getPhoneNumber());
      Assert.assertTrue(isContactVisible,"Contact is not visible");

      String fullName = getSalutation() +" "+getFirstName()+" "+getLastName();
      boolean isFullNameVisible = page.verifyUserFullName(fullName);
      Assert.assertTrue(isFullNameVisible,"Full name not visible");
    }

}
