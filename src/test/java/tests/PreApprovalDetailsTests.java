package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
        loginPage.login(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
        loginPage.mortgageApplicationMenu.click();

    }

    @Test
    public void positiveTestPreApprovalDetails() {

        PreApprovalDetaisPage preApprovalDetaisPage = new PreApprovalDetaisPage();
        Faker fake = new Faker();

        if (!preApprovalDetaisPage.checkBoxrealtor1.isSelected()) {
            preApprovalDetaisPage.checkBoxrealtor1.click();
        } else {
            System.out.println("Yes checkbox is preselected");
        }

        preApprovalDetaisPage.realtorInfo.sendKeys(fake.name().fullName());

        if (!preApprovalDetaisPage.checkBoxLoanOfficer1.isSelected()) {
            preApprovalDetaisPage.checkBoxLoanOfficer1.click();
        } else {
            System.out.println("Yes checkbox is preselected");
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

//        Assert.assertTrue(driver.getCurrentUrl().equals(""));  How to check if the next button succesfully passed?


    }


    @Test
    public void negativeTestPreApprovalDetailsWithoutData() {
        PreApprovalDetaisPage preApprovalDetaisPage = new PreApprovalDetaisPage();
        preApprovalDetaisPage.next.click();

        Assert.assertEquals(preApprovalDetaisPage.realtorRequired.getText(), "THIS FIELD IS REQUIRED.");
        Assert.assertEquals(preApprovalDetaisPage.estimatedPriceRequired.getText(), "THIS FIELD IS REQUIRED.");
        Assert.assertEquals(preApprovalDetaisPage.downPaymentRequired.getText(), "THIS FIELD IS REQUIRED.");
        Assert.assertEquals(preApprovalDetaisPage.downPaymentPercentRequired.getText(), "THIS FIELD IS REQUIRED.");


    }
}
