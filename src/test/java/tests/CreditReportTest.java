package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CreditReportPage;
import utilities.SequenceOfPagesOpening;


public class CreditReportTest extends TestBase{
    @BeforeMethod
    public void beforePagesUpload(){
        SequenceOfPagesOpening sequenceOfPagesOpening=new SequenceOfPagesOpening();
        sequenceOfPagesOpening.LoginPageOpening();
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));
    }
        @Test
        public void positiveAnswerforCreditReport(){

            CreditReportPage creditReportPage=new CreditReportPage();
            creditReportPage.yesCheckBox.click();
            creditReportPage.nextButton.click();
            Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));
Assert.assertFalse(!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));
        }
        @Test
        public void negativeAnswerforCreditReport(){
            CreditReportPage creditReportPage=new CreditReportPage();
            creditReportPage.noCheckBox.click();
            creditReportPage.nextButton.click();
            Assert.assertFalse(!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/mortagage.php"));

        }

}
