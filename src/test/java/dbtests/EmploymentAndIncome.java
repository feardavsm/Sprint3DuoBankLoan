package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.*;
import uitests.*;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

public class EmploymentAndIncome extends TestBase{

    @Test
    public void EmploymentAndIncome (){
           /*--- creating connection*/
        DataBaseUtility.createConnection ( );
        System.out.println ( " Connection is created " );

        /*--- setting the faker */
        Faker fake = new Faker ( );

        /*--- short cut to the "Expenses" page*/
        LoginPage loginPage = new LoginPage();
        do {loginPage.login(ConfigReader.getProperty("usernameTK"), ConfigReader.getProperty("passwordTK"));}
        while (!driver.getCurrentUrl().
        equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));
        loginPage.mortgageApplicationMenu.click();
        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
        PersonalInformationTests personalInformationTests = new PersonalInformationTests();
        personalInformationTests.verifyWithValidCredentials();
        ExpensesTests expensesTests = new ExpensesTests();
        expensesTests.verifyWithRentCheckBox();


       /*--- MONTHLY RENTAL PAYMENT */
       ExpensesPage expensesPage = new ExpensesPage();
       String expectedMonthlyRentalPay=fake.number().digits(4);
       expensesPage.monthlyRentalPayment.sendKeys(expectedMonthlyRentalPay);
       expensesPage.nextButton.click();

       /*---completing the application*/
       EmploymentAndIncomeTests aplication= new EmploymentAndIncomeTests ();
       aplication.verifyWithValidDataEmploymentAndIncome();

       CreditReportTest creditReportTest = new CreditReportTest();
       creditReportTest.positiveAnswerForCreditReport();

       EconsentTests econsentTests = new EconsentTests ();
       econsentTests.EconsentWithValidCredentials();

       JavascriptExecutor executor = (JavascriptExecutor)driver;
       executor.executeScript("arguments[0].click();",
          driver.findElement( By.xpath("//a[contains(text(),'Save')]")));


























        /*--- creating connection
        DataBaseUtility.createConnection ( );
        System.out.println ( " Connection is created " );

        /*--- setting the faker
        Faker fake = new Faker ( );

        /*--- short cut to the "Expenses" page
        LoginPage loginPage = new LoginPage();
        do {loginPage.login(ConfigReader.getProperty("usernameTK"), ConfigReader.getProperty("passwordTK"));}
        while (!driver.getCurrentUrl().
        equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));
        loginPage.mortgageApplicationMenu.click();

        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();

        PersonalInformationTests personalInformationTests = new PersonalInformationTests();
        personalInformationTests.verifyWithValidCredentials();

        ExpensesTests expensesTest = new ExpensesTests();
        expensesTest.verifyWithRentCheckBox();

         EmploymentAndIncomePage employmentAndIncomePage = new EmploymentAndIncomePage();
         Faker faker = new Faker();
         if (!employmentAndIncomePage.currentJob.isSelected()) {
            employmentAndIncomePage.currentJob.click();
        }
        String firstName = faker.name ( ).firstName ( );
        String  position = faker.job().position();
        String city = faker.address().city();
        String state  = faker.address().state ();

        Select selectBoxStatus = new Select(employmentAndIncomePage.state);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        employmentAndIncomePage.startDate.sendKeys("15/09/1989");


        String grossMonthluIncome;
        employmentAndIncomePage.monthlyGrossIncome.sendKeys(faker.number().digits(5));
        employmentAndIncomePage.monthlyOvertime.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyBonus.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyCommission.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyDivident.sendKeys(faker.number().digits(3));
        employmentAndIncomePage.nextButton.click();

        /*---completing the application
        CreditReportTest creditReportTest = new CreditReportTest();
        creditReportTest.positiveAnswerForCreditReport();
        EconsentTests econsentTests = new EconsentTests ();
        econsentTests.EconsentWithValidCredentials();

//        SummaryPage save = new SummaryPage();
//        save.saveButton.click();*/
    }

   }