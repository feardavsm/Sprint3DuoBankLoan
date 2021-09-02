package uitests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SummaryPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.List;


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
    public void summaryPositiveTest() {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//a[contains(text(),'Save')]")));
        }


    @Test
    public void t1() {
     //   SummaryPage s = new SummaryPage();
       // s.saveButton.click();
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

    }

@Test
    public void t2(){
    Assert.assertTrue(Driver.getDriver().getPageSource().contains("Current Monthly Housing Expenses"));

}






    }



