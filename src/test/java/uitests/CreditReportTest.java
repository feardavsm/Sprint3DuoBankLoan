package uitests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CreditReportPage;
import pages.LoginPage;
import utilities.ConfigReader;


public class CreditReportTest extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void loginSetup() {
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
    }


    @Test
    public void positiveAnswerForCreditReport() {
        CreditReportPage creditReportPage = new CreditReportPage();
        creditReportPage.yesCheckBox.click();
        creditReportPage.nextButton.click();
    }


        @Test
        public void negativeAnswerForCreditReport(){
            CreditReportPage creditReportPage=new CreditReportPage();
            creditReportPage.noCheckBox.click();
            creditReportPage.nextButton.click();
            Assert.assertFalse(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-5']//span[.='current step: ']")).isEnabled());
        }

}
