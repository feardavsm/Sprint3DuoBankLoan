package dbtests;


import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import pages.*;
import uitests.*;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

import java.sql.*;

public class Expenses extends TestBase {
        // @BeforeMethod(alwaysRun = true)
    @Test
    public void RentalExpenses ( ) throws SQLException {
        /*--- creating connection*/
        DataBaseUtility.createConnection ( );


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

       // Inserting newly created credentials into the MySQL Database
       String rentalExpenses=
                 "INSERT INTO loan.tbl_mortagage (id, realtor_status, realtor_info, loan_officer_status, purpose_loan, " +
                "est_purchase_price, down_payment, down_payment_percent, total_loan_amount, src_down_payment, add_fund_available, " +
                "apply_co_borrower, b_firstName, b_middleName, b_lastName, b_suffix, b_email, b_dob, b_ssn, b_marital, " +
                "b_cell, b_home, c_firstName, c_middleName, c_lastName, c_suffix, c_email, c_dob, c_ssn, c_marital, c_cell, " +
                "c_home, rent_own_status, monthly_rental_payment, first_mortagage_total_payment, employer_name, position, city, " +
                "state, start_date, end_date, current_job, co_employer_name, co_position, co_city, co_state, co_start_date, co_end_date, " +
                "co_current_job, gross_monthly_income, monthly_over_time, monthly_bonuses, monthly_commision, monthly_dividents, " +
                "c_gross_monthly_income, c_monthly_over_time, c_monthly_bonuses, c_monthly_commision, c_monthly_dividents, " +
                "add_belong, income_source, amount, eConsent_declarer, eConsent_declarer_FirstName, eConsent_declarer_LastName, " +
                "eConsent_declarer_Email, created_on, modified_on, loan_status, user_id)" +
                "values, active" +
                "('','','','' " +
                "'','','','','','','', " +
                "'','','','','','','','','', " +
                "'','','','','','','','','','','', " +
                "'','','"+expectedMonthlyRentalPay+"','','','','', " +
                "'','','','','','','','','','', " +
                "'','','','','','', " +
                "'','','','','', " +
                "'','','','','','', " +
                "'','','','','','')" +
                "'')";
       DataBaseUtility.updateQuery(rentalExpenses);
       System.out.println("Displaying Expected Monthly Rental Pay : "+expectedMonthlyRentalPay);
         expensesPage.nextButton.click();

        /*---completing the application*/
        CreditReportTest creditReportTest = new CreditReportTest();
        creditReportTest.positiveAnswerForCreditReport();

        EconsentTests econsentTests = new EconsentTests ();
        econsentTests.EconsentWithValidCredentials();

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//a[contains(text(),'Save')]")));

        String url = ConfigReader.getProperty ( "db_url" );
        String user = ConfigReader.getProperty ( "db_user" );
        String password = ConfigReader.getProperty ( "db_password" );
        Connection conectionTK = ( DriverManager.getConnection ( url, user, password ) );
        Statement statement = conectionTK.createStatement ( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
        ResultSet resultSet = statement.executeQuery ( "select * from loan.tbl_mortagage;" );
        resultSet.last ( );
        String expectedName=expectedMonthlyRentalPay;
        Object actualName=resultSet.getObject ( "employer_name" );
        if (expectedName.equals(actualName)){
                System.out.println ("System is working perfect" );
            }else{
                System.out.println ("System needs to be fixed!" );
                 }
        System.out.println ( "Displaying Expected EMPLOYER NAME : " + expectedMonthlyRentalPay);
        System.out.println ( "Displaying  Actual  EMPLOYER NAME : " + resultSet.getObject ( "employer_name" ) );



        }


    @Test
    public void MortageExpenses ( ) {
          /*--- creating connection*/
        DataBaseUtility.createConnection ( );

        /*--- setting the faker */
        Faker fake = new Faker ( );

        /*--- short cut to the "Expenses" page*/
        LoginPage loginPage = new LoginPage();
        do {loginPage.login(ConfigReader.getProperty("usernameTK"),
                ConfigReader.getProperty("passwordTK"));}
        while (!driver.getCurrentUrl().
        equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));
        loginPage.mortgageApplicationMenu.click();
        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
        PersonalInformationTests personalInformationTests = new PersonalInformationTests();
        personalInformationTests.verifyWithValidCredentials();
        ExpensesPage expensesPage = new ExpensesPage();
        expensesPage.ownChekBox.click();



        /*--- FIRST MORTGAGE TOTAL PAYMENT */

       String expectedMORTGAGEPay=fake.number().digits(5);
       expensesPage.firstMortgageTotalPayment.sendKeys(expectedMORTGAGEPay);
       expensesPage.nextButton.click();
       // Inserting newly created credentials into the MySQL Database
       String MORTGAGEExpenses=
                "INSERT INTO loan.tbl_mortagage (realtor_status, realtor_info, loan_officer_status, purpose_loan, " +
                "est_purchase_price, down_payment, down_payment_percent, total_loan_amount, src_down_payment, add_fund_available, " +
                "apply_co_borrower, b_firstName, b_middleName, b_lastName, b_suffix, b_email, b_dob, b_ssn, b_marital, " +
                "b_cell, b_home, c_firstName, c_middleName, c_lastName, c_suffix, c_email, c_dob, c_ssn, c_marital, c_cell, " +
                "c_home, rent_own_status, monthly_rental_payment, first_mortagage_total_payment, employer_name, position, city, " +
                "state, start_date, end_date, current_job, co_employer_name, co_position, co_city, co_state, co_start_date, co_end_date, " +
                "co_current_job, gross_monthly_income, monthly_over_time, monthly_bonuses, monthly_commision, monthly_dividents, " +
                "c_gross_monthly_income, c_monthly_over_time, c_monthly_bonuses, c_monthly_commision, c_monthly_dividents, " +
                "add_belong, income_source, amount, eConsent_declarer, eConsent_declarer_FirstName, eConsent_declarer_LastName, " +
                "eConsent_declarer_Email, created_on, modified_on, loan_status, user_id)" +
                "values" +
                "('','','','' " +
                "'','','','','','', " +
                "'','','','','','','','','', " +
                "'','','','','','','','','','','', " +
                "'','','','"+expectedMORTGAGEPay+"','','','', " +
                "'','','','','','','','','','', " +
                "'','','','','','', " +
                "'','','','','', " +
                "'','','','','','', " +
                "'','','','','')" +
                "'')";

       DataBaseUtility.updateQuery(MORTGAGEExpenses);
       System.out.println("Displaying Expected FIRST MORTGAGE TOTAL PAYMENT : "
               +expectedMORTGAGEPay);

        /*---completing the application*/
        EmploymentAndIncomeTests aplication= new EmploymentAndIncomeTests ();
        aplication.verifyWithValidDataEmploymentAndIncome();

        CreditReportTest creditReportTest = new CreditReportTest();
        creditReportTest.positiveAnswerForCreditReport();

        EconsentTests econsentTests = new EconsentTests ();
        econsentTests.EconsentWithValidCredentials();

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//a[contains(text(),'Save')]")));
    }

   }


