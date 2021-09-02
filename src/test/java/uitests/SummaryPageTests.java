package uitests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utilities.ConfigReader;
import utilities.DataBaseUtility;
import utilities.Driver;

import java.sql.DriverManager;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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

   // Test t1 Positive log-in test
    @Test
    public void summaryPositiveTest() {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[contains(text(),'Save')]")));
        }

        // Test t2 checks for Edit buttons on summary page and returns the amount/success if 5.
    @Test
    public void t2() {
        Assert.assertTrue(Driver.getDriver().getPageSource().contains("PreApproval Inquiry"));
        Assert.assertTrue(Driver.getDriver().getPageSource().contains("Current Monthly Housing Expenses"));

        SummaryPage s = new SummaryPage();
        List<WebElement> edits = Driver.getDriver().findElements(By.xpath("//a[contains(text(),'Edit')]"));
        int count = 0;
        for (WebElement e: edits) {
            System.out.println(e.getText());
            count++;

        }
        Assert.assertEquals(count, 5);// there are 5 edit buttons
        if (count == 5) {
            System.out.println("There are 5 edit buttons");
        }else{
            System.out.println("Something went wrong, There should be 5 buttons");
        }

    }
// Test t3 checks if the webpage contains the text "Current Monthly Housing Expenses"
@Test
    public void t3(){
    Assert.assertTrue(Driver.getDriver().getPageSource().contains("Current Monthly Housing Expenses"));

}
// Test t4 tests if monthly rental payment from ExpensesTest is ran and saved onto MySQL and relayed back.
//@Test
//    public void t4() throws InterruptedException {
//
//        Driver.getDriver().findElement(By.id("ExpenseEdit")).click();
//        Thread.sleep(3000);
//
//        ExpensesPage e = new ExpensesPage();
//        ExpensesTests et = new ExpensesTests();
//    System.out.println("monthly rental payment amount: $"+ et.num);
//
//    Driver.getDriver().findElement(By.id("steps-uid-0-t-6")).click();
//    Driver.getDriver().findElement(By.xpath("//a[@href=\"#finish\"]")).click();
//
//    List<List<Object>> monthlyRentalPayment = DataBaseUtility.getQueryResultAsListOfLists("select monthly_rental_payment from tbl_mortagage where monthly_rental_payment ="+et.num+"");
//    List<List<Object>> checkMonthlyRentalPayment = DataBaseUtility.getQueryResultAsListOfLists("select monthly_rental_payment from tbl_mortagage ORDER BY id DESC LIMIT 1;");
//
//    System.out.println("expected monthly rental payment amount: $" + monthlyRentalPayment.get(0).get(0));
//
//    Assert.assertEquals(checkMonthlyRentalPayment, monthlyRentalPayment);
//}
//    //  Test t5 tests if gross monthly income from EmploymentAndIncome is saved to MySQL table and relayed back.
//@Test
//    public void t5() throws InterruptedException {
//    Driver.getDriver().findElement(By.id("EmploymentIncomeEdit")).click();
//    Thread.sleep(2000);
//
//    EmploymentAndIncomeTests e = new EmploymentAndIncomeTests();
//    System.out.println("Gross monthly income: " + "$"+ e.grossMonthly);
//
//
//    Driver.getDriver().findElement(By.id("steps-uid-0-t-6")).click(); // return to summary page
//    Thread.sleep(2000);
//    Driver.getDriver().findElement(By.xpath("//a[@href=\"#finish\"]")).click(); // saves forms
//
//    List<List<Object>> grossMonthlyIncome = DataBaseUtility.getQueryResultAsListOfLists("select gross_monthly_income from tbl_mortagage where gross_monthly_income ="+e.grossMonthly+"");
//    List<List<Object>> checkGrossMonthlyIncome = DataBaseUtility.getQueryResultAsListOfLists("select gross_monthly_income from tbl_mortagage ORDER BY id DESC limit 1;");
//
//    System.out.println( "expected amount: $"+ grossMonthlyIncome.get(0).get(0));
//
//    Assert.assertEquals(checkGrossMonthlyIncome, grossMonthlyIncome);
//
//    }
    /*@Test
    public void t5() throws InterruptedException {

        Driver.getDriver().findElement(By.id("b_email")).click();
        Thread.sleep(2000);// click on pre approval
//
//
        PersonalInformationPage p  = new PersonalInformationPage();

        System.out.println("here "+p.email.getText().toString());
//
//        Driver.getDriver().findElement(By.id("")).click(); // return to summary page
//        Thread.sleep(2000);
//        Driver.getDriver().findElement(By.xpath("")).click(); // saves forms
//
//        List<List<Object>> emailAddress = DataBaseUtility.getQueryResultAsListOfLists("select b_cell from tbl_mortagage where b_cell =" + p.cellPhone + "");
//        List<List<Object>> checkEmailAddress =DataBaseUtility.getQueryResultAsListOfLists("select b_cell from tbl_mortagage order by id desc limit 1;");
//
//        System.out.println("" + .get(0).get(0));
//
//        Assert.assertEquals(checkCellPhoneNo, cellPhoneNo);

    }
*/




}













