package uitests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PreApprovalDetaisPage;
import utilities.ConfigReader;

public class PreApprovalDetailsTests extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void preApprovalTestsSetup() {
        LoginPage loginPage = new LoginPage();
        do {
            loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        }
        while (!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));

        loginPage.mortgageApplicationMenu.click();

    }

    @Test
    public void positiveTestPreApprovalDetails() {

        PreApprovalDetaisPage preApprovalDetaisPage = new PreApprovalDetaisPage();
        Faker fake = new Faker();

        if (!preApprovalDetaisPage.checkBoxrealtor1.isSelected()) {
            preApprovalDetaisPage.checkBoxrealtor1.click();
        }

        preApprovalDetaisPage.realtorInfo.sendKeys(fake.name().fullName());

        if (!preApprovalDetaisPage.checkBoxLoanOfficer1.isSelected()) {
            preApprovalDetaisPage.checkBoxLoanOfficer1.click();
        }

//        Select selectPurposeOfLoan = new Select(preApprovalDetaisPage.purposeOfLoan);
//        selectPurposeOfLoan.selectByVisibleText("Purchase a Home");

//        String actualText = preApprovalDetaisPage.purposeOfLoan.getText();
//        String expectedText = "Purchase a Home";
//        Assert.assertEquals(actualText, expectedText);

        preApprovalDetaisPage.estimatedPrice.sendKeys("600000");
        preApprovalDetaisPage.downPayment.sendKeys("100000");

        String actualDownPaymentPercent = preApprovalDetaisPage.downPaymentPercent.getText();
        String expectedDownPaymentPercent = "16";

        String actualLoanAmount = preApprovalDetaisPage.loanAmount.getText();
        String expectedLoanAmount = "500000 $";

        Assert.assertEquals(actualLoanAmount, expectedLoanAmount);

//       Select selectAdditionalFunds = new Select(preApprovalDetaisPage.additionalFunds);
//       selectAdditionalFunds.selectByVisibleText("Checking/Savings (most recent bank statement)");

//        System.out.println(preApprovalDetaisPage.additionalFunds);

        preApprovalDetaisPage.next.click();

    }


    @Test
    public void negativeTestPreApprovalDetailsWithoutData() {
        PreApprovalDetaisPage preApprovalDetaisPage = new PreApprovalDetaisPage();
        preApprovalDetaisPage.next.click();

        Assert.assertEquals(preApprovalDetaisPage.realtorRequired.getText(), "THIS FIELD IS REQUIRED.");
        Assert.assertEquals(preApprovalDetaisPage.estimatedPriceRequired.getText(), "THIS FIELD IS REQUIRED.");
        Assert.assertEquals(preApprovalDetaisPage.downPaymentRequired.getText(), "THIS FIELD IS REQUIRED.");
        Assert.assertEquals(preApprovalDetaisPage.downPaymentPercentRequired.getText(), "THIS FIELD IS REQUIRED.");

        Assert.assertTrue(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-0']//span[.='current step: ']")).isEnabled());


    }
}
