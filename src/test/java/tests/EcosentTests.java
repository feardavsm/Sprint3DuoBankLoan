package tests;

import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EconsentPage;
import utilities.ConfigReader;

public class EcosentTests extends TestBase{
    @BeforeMethod
    public void beforeOpeningEcosentTest(){
        CreditReportTest creditReportTest=new CreditReportTest();
        creditReportTest.beforePagesUpload();
        creditReportTest.positiveAnswerforCreditReport();
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));
    }
    @Test
    public void EcosentWithValidCredentials(){
        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(ConfigReader.getProperty("firstname"));
        econsentPage.lastName.sendKeys(ConfigReader.getProperty("lastname"));
        econsentPage.email.sendKeys(ConfigReader.getProperty("email"));
        econsentPage.acceptButton.click();
        econsentPage.nextButton.click();
    }

    @Test
    public void EcosentWithWrongValidCredentials(){
        Faker fake=new Faker();

        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(fake.address().buildingNumber());
        econsentPage.lastName.sendKeys(fake.business().creditCardNumber());
        econsentPage.email.sendKeys(fake.name().fullName());
        econsentPage.acceptButton.click();
        econsentPage.nextButton.click();
        Assert.assertFalse(!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));

    }
    @Test(alwaysRun = true)
    public void EcosentWithNoCredetials(){
        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(" ");
        econsentPage.lastName.sendKeys("   ");
        econsentPage.email.sendKeys( "");
        econsentPage.acceptButton.click();
        econsentPage.nextButton.click();
        Assert.assertFalse(!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));

    }


}
