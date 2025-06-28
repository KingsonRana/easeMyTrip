package tests;

import Base.BaseTest;
import Pages.PaymentMethodPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PaymentMethodTest extends BaseTest {
    PaymentMethodPage page;
    @BeforeClass
    public void setUpPage() {
        page = new PaymentMethodPage( getDriver(),getWait());
        page.moveToElement(page.paymentDivXpath);
    }
    @Test(description = "Verify UPI payment method is visible")
    public void verifyUPIPaymentMethodAvailability(){
        Assert.assertTrue(page.elementIsVisible(page.upiXpath),"UPI payment method not visible");
    }
    @Test(description = "Verify Credit/Debit/ATM Cards payment method is visible")
    public void verifyCardPaymentMethodAvailability(){
       Assert.assertTrue(page.elementIsVisible(page.cardXpath),"Card payment method not visible");
    }
    @Test(description = "Verify NetBanking payment method is visible")
    public void verifyNetBankingMethodAvailability(){
        Assert.assertTrue(page.elementIsVisible(page.netBankingXpath),"NetBanking payment method not visible");
    }
    @Test(description = "Verify Wallets  payment method is visible")
    public void verifyWalletsMethodAvailability(){
        Assert.assertTrue(page.elementIsVisible(page.walletXpath),"Wallets Payment method not visible");
    }
    @Test(description = "Verify EMI payment method is visible")
    public void verifyEMIMethodAvailability(){
        Assert.assertTrue(page.elementIsVisible(page.emiXpath),"EMI Payment method not visible");
    }
    @Test(description = "Verify Pay With Reward payment method is visible")
    public void verifyPayWithRewardMethodAvailability(){
        Assert.assertTrue(page.elementIsVisible(page.rewardsXpath),"Reward Payment method not visible");
    }

    @Test(description = "Verify GiftCard payment method is visible")
    public void verifyGiftCardMethodAvailability(){
        Assert.assertTrue(page.elementIsVisible(page.giftCardXpath),"Gift Card Payment method not visible");
    }
    @Test(description = "Verify Pay Later payment method is visible")
    public void verifyPayLaterMethodAvailability(){
        Assert.assertTrue(page.elementIsVisible(page.payLaterXpath),"Pay Later Payment method not visible");
    }
    @Test(description = "Verify GooglePay payment method is visible")
    public void verifyGooglePayMethodAvailability(){
        Assert.assertTrue(page.elementIsVisible(page.googlePayXpath),"Card Payment method not visible");
    }

}
