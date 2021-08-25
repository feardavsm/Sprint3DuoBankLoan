package uitests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EconsentPage;
import pages.LoginPage;
import utilities.ConfigReader;

public class EconsentTests extends TestBase{
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
        CreditReportTest creditReportTest = new CreditReportTest();
        creditReportTest.positiveAnswerForCreditReport();
    }

    @Test

    public void EconsentWithValidCredentials(){
        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(ConfigReader.getProperty("firstname"));
        econsentPage.lastName.sendKeys(ConfigReader.getProperty("lastname"));
        econsentPage.email.sendKeys(ConfigReader.getProperty("email"));
        econsentPage.agreeButton.click();
        econsentPage.nextButton.click();
    }

    @Test
    public void EconsentWithInValidCredentials(){
        Faker fake=new Faker();
        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(fake.address().buildingNumber());
        econsentPage.lastName.sendKeys(fake.business().creditCardNumber());
        econsentPage.email.sendKeys(fake.name().fullName());
        econsentPage.dontAgreeButton.click();
        econsentPage.nextButton.click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-5']//span[.='current step: ']")).isEnabled());

    }
    @Test()
    public void EconsentWithNoCredetials(){
        EconsentPage econsentPage=new EconsentPage();
        econsentPage.nextButton.click();
        Assert.assertFalse(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-6']//span[.='current step: ']")).isEnabled());
    }


}
