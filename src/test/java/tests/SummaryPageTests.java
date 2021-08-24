package tests;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ExpensesPage;
import pages.LoginPage;
import pages.SummaryPage;
import utilities.ConfigReader;
import utilities.Driver;


public class SummaryPageTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void loginSetup() throws InterruptedException {
        LoginPage loginPage = new LoginPage();
        do {
            loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        }
        while (!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));

        loginPage.mortgageApplicationMenu.click();

        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
        PersonalInformationTests personalInformationTests = new PersonalInformationTests();
        personalInformationTests.verifyWithValidCredentials();
        ExpensesTests expensesTests = new ExpensesTests();
        expensesTests.verifyWithRentCheckBox();
        EmploymentAndIncomeTests employmentAndIncomeTests = new EmploymentAndIncomeTests();
        employmentAndIncomeTests.verifyWithValidDataEmploymentAndIncome();
        CreditReportTest creditReportTest = new CreditReportTest();
        creditReportTest.positiveAnswerForCreditReport();
        EconsentTests econsentTests = new EconsentTests();
        econsentTests.EconsentWithValidCredentials();
        Thread.sleep(3000);
    }
//
    @Test
    public void t1() {
//        SummaryPage s = new SummaryPage();
//        s.currentMonthlyHousingExpenses.click();
//        //Assert.assertTrue(Driver.getDriver().getPageSource().contains("Borrower Employment Information"));
//
//
//
//
//
//
//
    }








    }



