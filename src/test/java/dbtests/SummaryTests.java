package dbtests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ExpensesPage;
import pages.LoginPage;
import uitests.*;
import utilities.ConfigReader;
import utilities.DataBaseUtility;
import utilities.Driver;

import java.util.List;

public class SummaryTests extends TestBase {
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



    @Test
    public void t4() throws InterruptedException {

        Driver.getDriver().findElement(By.id("ExpenseEdit")).click();
        Thread.sleep(3000);

        ExpensesPage e = new ExpensesPage();
        ExpensesTests et = new ExpensesTests();
        System.out.println("monthly rental payment amount: $"+ et.num);

        Driver.getDriver().findElement(By.id("steps-uid-0-t-6")).click();
        Driver.getDriver().findElement(By.xpath("//a[@href=\"#finish\"]")).click();

        List<List<Object>> monthlyRentalPayment = DataBaseUtility.getQueryResultAsListOfLists("select monthly_rental_payment from tbl_mortagage where monthly_rental_payment ="+et.num+"");
        List<List<Object>> checkMonthlyRentalPayment = DataBaseUtility.getQueryResultAsListOfLists("select monthly_rental_payment from tbl_mortagage ORDER BY id DESC LIMIT 1;");

        System.out.println("expected monthly rental payment amount: $" + monthlyRentalPayment.get(0).get(0));

        Assert.assertEquals(checkMonthlyRentalPayment, monthlyRentalPayment);
    }
    //  Test t5 tests if gross monthly income from EmploymentAndIncome is saved to MySQL table and relayed back.
    @Test
    public void t5() throws InterruptedException {
        Driver.getDriver().findElement(By.id("EmploymentIncomeEdit")).click();
        Thread.sleep(2000);

        EmploymentAndIncomeTests e = new EmploymentAndIncomeTests();
        System.out.println("Gross monthly income is: " + "$"+ e.grossMonthly);


        Driver.getDriver().findElement(By.id("steps-uid-0-t-6")).click(); // return to summary page
        Thread.sleep(2000);
        Driver.getDriver().findElement(By.xpath("//a[@href=\"#finish\"]")).click(); // saves forms

        List<List<Object>> grossMonthlyIncome = DataBaseUtility.getQueryResultAsListOfLists("select gross_monthly_income from tbl_mortagage where gross_monthly_income ="+e.grossMonthly+"");
        List<List<Object>> checkGrossMonthlyIncome = DataBaseUtility.getQueryResultAsListOfLists("select gross_monthly_income from tbl_mortagage ORDER BY id DESC limit 1;");

        System.out.println( "expected amount: $"+ grossMonthlyIncome.get(0).get(0));

        Assert.assertEquals(checkGrossMonthlyIncome, grossMonthlyIncome);

    }











    //test #2
}
