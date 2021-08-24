package tests;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EconsentPage;
import pages.LoginPage;
import utilities.ConfigReader;

public class EcosentTests extends TestBase{
    @BeforeMethod(alwaysRun = true)
    public void login() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        loginPage.mortgageApplicationMenu.click();

        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
    }
    @Test

    public void EcosentWithValidCredentials(){
        CreditReportTest creditReportTest=new CreditReportTest();
        creditReportTest.positiveAnswerforCreditReport();
        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(ConfigReader.getProperty("firstname"));
        econsentPage.lastName.sendKeys(ConfigReader.getProperty("lastname"));
        econsentPage.email.sendKeys(ConfigReader.getProperty("email"));
        econsentPage.acceptButton.click();
        econsentPage.nextButton.click();
    }

    @Test
    public void EcosentWithWrongValidCredentials(){
        CreditReportTest creditReportTest=new CreditReportTest();
        creditReportTest.positiveAnswerforCreditReport();
        Faker fake=new Faker();

        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(fake.address().buildingNumber());
        econsentPage.lastName.sendKeys(fake.business().creditCardNumber());
        econsentPage.email.sendKeys(fake.name().fullName());
        econsentPage.acceptButton.click();
        econsentPage.nextButton.click();
        Assert.assertFalse(!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));

    }
    @Test()
    public void EcosentWithNoCredetials(){
        CreditReportTest creditReportTest=new CreditReportTest();
        creditReportTest.positiveAnswerforCreditReport();
        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(" ");
        econsentPage.lastName.sendKeys("   ");
        econsentPage.email.sendKeys( "");
        econsentPage.acceptButton.click();
        econsentPage.nextButton.click();
        Assert.assertFalse(!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));

    }


}
