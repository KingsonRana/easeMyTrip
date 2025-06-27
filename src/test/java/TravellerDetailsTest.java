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
        page = new TravellerDetailPage( getDriver(),getWait());
    }

    @Test(description = "Verify Email Id is required", priority = 0)
    public void verifyEmailFieldIsRequired() {
        page.clickContinueButton();  // assume this triggers validation
        boolean isEmpty = page.isEmailFieldEmpty();
        Assert.assertTrue(isEmpty, "Email field should be marked as empty.");
    }
    @Test(description = "Enter email id" ,dataProvider = "userCredentials", dataProviderClass = DataProvider.class, priority = 1)
    public void verifyEmailFieldClassChangesAfterInput(String countryCode, String phoneNumber,String email) {
        boolean isUpdated = page.enterEmailAndVerifyClassChange(email);
        Assert.assertTrue(isUpdated, "Email field class did not update to 'ng-not-empty' after input.");
    }

    @Test(description = "Verify phone number is required", priority = 2)
    public void verifyPhoneNumberFieldValidation() {
        page.clickContinueButton();
        boolean isEmpty = page.isPhoneNumberFieldEmpty();
        Assert.assertTrue(isEmpty, "Phone number field should be marked as empty.");
    }
    @Test(description = "Enter Phone Number " ,dataProvider = "userCredentials", dataProviderClass = DataProvider.class, priority = 3)
    public void verifyPhoneFieldClassChangesAfterInput(String countryCode, String phoneNumber,String email) {
        boolean isUpdated = page.enterPhoneAndVerifyClassChange(phoneNumber);
        Assert.assertTrue(isUpdated, "Phone field class did not update to 'ng-not-empty' after input.");
    }
    @Test(description = "Verify alert message on empty title",priority = 4)
    public void verifyTitleValidationError  (){
        page.selectTitle("Title");
        page.clickContinueButton();
        boolean isErrorVisible = page.isErrorVisible();
        System.out.println("Title not entered alert displayed  = " + isErrorVisible);
        Assert.assertTrue(isErrorVisible, "Error message should be shown when a valid title is not entered");
    }
    @Test(description = "Verify alert message not displayed when title is not empty",priority = 5)
    public void verifyNoErrorDisplayedWhenTitleEntered (){
        page.selectTitle("Mr");
        page.clickContinueButton();
        boolean isErrorVisible = page.isErrorMessageDisplayed("Adult 1 title is required");
        System.out.println("Title entered alert displayed  = " + isErrorVisible);
        Assert.assertFalse(isErrorVisible, "Error message should not be shown when a valid title is entered");

    }

    @Test(description = "Verify alert message on empty first name",priority = 6)
    public void verifyFirstNameValidationError(){
        page.clickContinueButton();
        boolean isErrorVisible = page.isErrorVisible();
        System.out.println("First name not entered alert displayed  = " + isErrorVisible);
        Assert.assertTrue(isErrorVisible, "Error message should be shown when a valid first name is not entered");

    }
    @Test(description = "Verify alert message on empty first name",priority = 7,dataProvider = "travellerDetails", dataProviderClass = DataProvider.class)
    public void verifyNoErrorShownWhenFirstNameEntered(String firstName, String lastName) {
        page.enterAdultFirstName(firstName);
        page.clickContinueButton();
        boolean isErrorVisible  = page.isErrorMessageDisplayed("Adult 1 First Name should have minimum 1");
        System.out.println("First Name entered alert displayed  = " + isErrorVisible);
        Assert.assertFalse(isErrorVisible,"Error message should not be shown when First name is entered");
        }
    @Test(description = "Verify alert message on empty last name",priority = 8)
    public void verifyLastNameValidationError  (){
        page.clickContinueButton();
        boolean isErrorVisible = page.isErrorVisible();
        System.out.println("Last name not entered alert displayed  = " + isErrorVisible);
        Assert.assertTrue(isErrorVisible, "Error message should be shown when a valid first name is not entered");

    }
    @Test(description = "Verify alert message on empty last name",priority = 9,dataProvider = "travellerDetails", dataProviderClass = DataProvider.class)
    public void verifyNoErrorShownWhenLastNameEntered(String firstName, String lastName) {
        System.out.println("Last name is " +lastName);
        page.enterAdultLastName(lastName);
        page.clickContinueButton();
        boolean isErrorVisible  = page.isErrorMessageDisplayed("Adult 1 Last Name should have minimum 1character.");
        System.out.println("Last name entered alert displayed  = " + isErrorVisible);
        Assert.assertFalse(isErrorVisible,"Error message should not be shown when First name is entered");
    }
    @Test(description = "skip seat selection", dependsOnMethods = "verifyNoErrorShownWhenLastNameEntered")
    public void skipSeatSelection(){
       page.skipSeatSelection();
       page.skipToPayment();
    }

}
