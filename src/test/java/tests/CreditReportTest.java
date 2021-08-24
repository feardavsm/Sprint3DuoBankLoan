package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CreditReportPage;
import pages.LoginPage;
import utilities.ConfigReader;


public class CreditReportTest extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void login() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        loginPage.mortgageApplicationMenu.click();

        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
    }


    @Test
    public void positiveAnswerforCreditReport() {
        EmploymentAndIncomeTests employmentAndIncomeTests = new EmploymentAndIncomeTests();
        employmentAndIncomeTests.verifyWithValidDataEmploymentAndIncome("Aliay", "mid", "Lastname", "email.mail34@gmail.com", "08/22/1985", "5643762134", "609-435-12-32", "703-674-64-46");

        CreditReportPage creditReportPage = new CreditReportPage();
        creditReportPage.yesCheckBox.click();
        creditReportPage.nextButton.click();
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));
        Assert.assertFalse(!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));
    }

        @Test
        public void negativeAnswerforCreditReport(){
    EmploymentAndIncomeTests employmentAndIncomeTests = new EmploymentAndIncomeTests();
    employmentAndIncomeTests.verifyWithValidDataEmploymentAndIncome("Aliay", "mid", "Lastname", "email.mail34@gmail.com", "08/22/1985", "5643762134", "609-435-12-32", "703-674-64-46");


            CreditReportPage creditReportPage=new CreditReportPage();
            creditReportPage.noCheckBox.click();
            creditReportPage.nextButton.click();
            Assert.assertFalse(!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));

        }

}
