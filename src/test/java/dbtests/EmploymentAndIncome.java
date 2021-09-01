package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;


import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import uitests.*;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

import java.sql.*;

import java.util.List;




public class EmploymentAndIncome extends TestBase {




    @Test
    public void EmploymentAndIncome ( ) throws SQLException {
       /*--- creating connection*/
//          String url = ConfigReader.getProperty("db_url");
//        String user = ConfigReader.getProperty("db_user");
//        String password = ConfigReader.getProperty("db_password");
//     Connection conectionTK= (DriverManager.getConnection(url,user,password) );
//     Statement statement =conectionTK.createStatement ( ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);

        System.out.println ( " Connection is created " );

        /*--- setting the faker */
        Faker fake = new Faker ( );

        /*--- short cut to the "Expenses" page*/
        LoginPage loginPage = new LoginPage ( );

        do {
            loginPage.login ( ConfigReader.getProperty ( "usernameTK" ), ConfigReader.getProperty ( "passwordTK" ) );
        }
        while ( !driver.getCurrentUrl ( ).
                equals ( "http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php" ) );
        loginPage.mortgageApplicationMenu.click ( );
        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests ( );
        preApproval.positiveTestPreApprovalDetails ( );
        PersonalInformationTests personalInformationTests = new PersonalInformationTests ( );
        personalInformationTests.verifyWithValidCredentials ( );

        ExpensesTests expensesTests = new ExpensesTests ( );
        expensesTests.verifyWithOwnCheckBox ( );

        // Inserting newly created credentials into the MySQL Database

       EmploymentAndIncomeTests employmentAndIncome = new EmploymentAndIncomeTests ( );
        EmploymentAndIncomePage employmentAndIncomePage = new EmploymentAndIncomePage ( );
        employmentAndIncome.verifyWithValidDataEmploymentAndIncome();

        String firstName = fake.name ( ).firstName ( );
        String positionE = fake.job ( ).position ( );
        String cityE = fake.address ( ).city ( );
        String grossMonthlyIncome = fake.number ( ).digits ( 5 );
        String monthlyOvertimeE = fake.number ( ).digits ( 4 );
        String monthlyBonusE = fake.number ( ).digits ( 4 );
        String monthlyCommissionE = fake.number ( ).digits ( 4 );
        String monthlyDividentE = fake.number ( ).digits ( 3 );
        if ( !employmentAndIncomePage.currentJob.isSelected ( ) ) {
            employmentAndIncomePage.currentJob.click ( );
        }
//        employmentAndIncomePage.employName.sendKeys(firstName);
//        employmentAndIncomePage.position.sendKeys(positionE);
//        employmentAndIncomePage.city.sendKeys(cityE);
//        Select selectBoxStatus = new Select (employmentAndIncomePage.state);
//        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
//        employmentAndIncomePage.startDate.sendKeys("12/08/1980");
//        employmentAndIncomePage.monthlyGrossIncome.sendKeys(grossMonthlyIncome);
//        employmentAndIncomePage.monthlyOvertime.sendKeys(monthlyOvertimeE);
//        employmentAndIncomePage.monthlyBonus.sendKeys(monthlyBonusE);
//        employmentAndIncomePage.monthlyCommission.sendKeys(monthlyCommissionE);
//        employmentAndIncomePage.monthlyDivident.sendKeys(monthlyDividentE);


        String BorrowerEmploymentInformation =
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
                        "'','','','','Test','" + positionE + "','" + cityE + "', " +
                        "'','','','','','','','','','', " +
                        "'','" + grossMonthlyIncome + "','" + monthlyOvertimeE + "','" + monthlyBonusE + "','" + monthlyCommissionE + "','" + monthlyDividentE + "', " +
                        "'','','','','', " +
                        "'','','','','','', " +
                        "'','','','','')" +
                        "'')";
        DataBaseUtility.updateQuery ( BorrowerEmploymentInformation );
        System.out.println ( "Displaying Expected EMPLOYER NAME : " + firstName );
        System.out.println ( "Displaying Expected POSITION : " + positionE );
        System.out.println ( "Displaying Expected CITY : " + cityE );
        System.out.println ( "Displaying Expected GROSS MONTHLY INCOME : " + grossMonthlyIncome );
        System.out.println ( "Displaying Expected MONTHLY OVERTIME : " + monthlyOvertimeE );
        System.out.println ( "Displaying Expected MONTHLY BONUSES : " + monthlyBonusE );
        System.out.println ( "Displaying Expected MONTHLY COMMISSIONS : " + monthlyCommissionE );
        System.out.println ( "Displaying Expected MONTHLY DIVIDENDS/INTEREST : " + monthlyDividentE );

        employmentAndIncomePage.nextButton.click ( );

         CreditReportTest creditReportTest = new CreditReportTest();
        creditReportTest.positiveAnswerForCreditReport();

        EconsentTests econsentTests = new EconsentTests ();
        econsentTests.EconsentWithValidCredentials();

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//a[contains(text(),'Save')]")));

//     ResultSet resultSet=statement.executeQuery("select * from loan.tbl_mortagage;" );
//     resultSet.next();
//     System.out.println(resultSet.getObject("employer_name"));

    }





     @Test
    public void verifyNoDuplicateEmails() {
        List<List<Object>> lisOfLists = DataBaseUtility.getQueryResultAsListOfLists("select realtor_status, count(*)" +
                " from tbl_mortagage group by realtor_status having count(*)>1;");
        Assert.assertTrue(lisOfLists.isEmpty(), "The list is not empty, its size is " + lisOfLists.size());



    }


     @Test
     public void checkEmplyinme () throws SQLException {
             /*--- creating connection*/
             String url = ConfigReader.getProperty ( "db_url" );
             String user = ConfigReader.getProperty ( "db_user" );
             String password = ConfigReader.getProperty ( "db_password" );
             Connection conectionTK = ( DriverManager.getConnection ( url, user, password ) );
             Statement statement = conectionTK.createStatement ( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
             ResultSet resultSet=statement.executeQuery("select * from loan.tbl_mortagage;" );
             resultSet.next();
             System.out.println("Displaying Employer Name : "+ resultSet.getObject("employer_name"));

    }

         }



