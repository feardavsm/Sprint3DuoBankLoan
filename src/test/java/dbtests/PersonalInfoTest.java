package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.PersonalInformationPage;
import uitests.PreApprovalDetailsTests;
import uitests.TestBase;
import utilities.ConfigReader;
import utilities.DataBaseUtility;


import java.sql.SQLException;
import java.util.*;

public class PersonalInfoTest extends TestBase {

    @Test
    public void verifyPersonalInformationFromDatabaseToUI() {

        // Login In To Web Application
        LoginPage loginPage = new LoginPage();
        logger.info("Logging in");
        loginPage.login(ConfigReader.getProperty("username2"), ConfigReader.getProperty("password2"));
        Assert.assertFalse(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));

        // Processing PreApproval Credentials InOrder To Proceed With The Personal Information Testing
        loginPage.mortgageApplicationMenu.click();
        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();

        // Generating Random Realtor & User Credentials For Database Insertion
        Faker faker = new Faker();
        String expectedRetailerFullName = new Faker().name().fullName();
        String expectedRetailEmail = new Faker().internet().emailAddress();
        String expectedEstPurchasePrice = new Faker().number().digits(6);
        String expectedDownPaymentAmount = new Faker().number().digits(5);
        int expectedDownPaymentPercentage = new Faker().number().numberBetween(10, 100);
        String expectedTotalLoanAmount = new Faker().number().digits(5);
        String expectedMonthlyRentalPayment = new Faker().number().digits(4);
        String expectedEmployeeName = new Faker().company().name();
        String expectedGrossMonthlyIncome = new Faker().number().digits(5);
        Date expectedDate = new Faker().date().birthday();
        String expectedUserId = new Faker().number().digits(4);
        String expectedMaritalStatus = new Faker().demographic().maritalStatus();

        // Using Random Credentials To Fill In The Personal Information Page Input Fields
        PersonalInformationPage pip = new PersonalInformationPage();
        logger.info("Selecting coBorrower checkbox");
        if (!pip.coBorrowerNoCheckBox.isSelected()) {
            pip.coBorrowerNoCheckBox.click();
        }
        logger.info("Entering first name");
        String expectedFirstName = faker.name().firstName();
        pip.firstName.sendKeys(expectedFirstName);
        logger.info("Entering last name");
        String expectedLastName = faker.name().lastName();
        pip.lastName.sendKeys(expectedLastName);
        logger.info("Entering email address");
        String expectedEmail = faker.internet().emailAddress();
        pip.email.sendKeys(expectedEmail);
        logger.info("Entering ssn");
        String expectedSsn = faker.number().digits(9);
        pip.ssn.sendKeys(expectedSsn);
        logger.info("Selecting marital status");
        Select maritalStatus = new Select(pip.maritalStatus);
        maritalStatus.selectByIndex(1);
        logger.info("Entering cell phone");
        String expectedCell = faker.phoneNumber().cellPhone();
        pip.cellPhone.sendKeys(expectedCell);
        logger.info("Selecting privacy policy checkbox");
        if (!pip.privacyPolicyCheckBox.isSelected()) {
            pip.privacyPolicyCheckBox.click();
        }
        logger.info("Clicking next button");
        pip.nextButton.click();


        // Inserting The Randomly Created Credentials Into Loan Mortagage MySQL Database
        String query = "INSERT INTO loan.tbl_mortagage (realtor_status, realtor_info, loan_officer_status, purpose_loan, " +
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
                "('1', '" + expectedRetailerFullName + ", " + expectedRetailEmail + "', '1', 'Purchase a Home', '" + expectedEstPurchasePrice + "', " +
                "'" + expectedDownPaymentAmount + "', '" + expectedDownPaymentPercentage + "', '" + expectedTotalLoanAmount + "', " +
                "'Checking/Savings (most recent bank statement)', '', '2', '" + expectedFirstName + "', '', '" + expectedLastName + "', '', " +
                "'" + expectedEmail + "', '', '" + expectedSsn + "', '" + expectedMaritalStatus + "', '" + expectedCell + "', '', '', '', '', '', '', '', '', '', '', '', 'Rent', " +
                "'" + expectedMonthlyRentalPayment + "', '', '" + expectedEmployeeName + "', '', '', '', '', '', '', '', '', '', '', '', '', '', " +
                "'" + expectedGrossMonthlyIncome + "', '', '', '', '', '', '', '', '', '', '', '', '', '', '" + expectedFirstName + "', '" + expectedLastName + "', " +
                "'" + expectedEmail + "', '" + expectedDate + "', '', '', '" + expectedUserId + "')";

        // Updating the MySQL Query Database
        DataBaseUtility.updateQuery(query);


        // Printing the newly created credentials that were used to sign up and login and
        System.out.println("Below You Will See The Randomly Generated Credentials!");
        System.out.println("First Name: " + expectedFirstName + " | Last Name: " + expectedLastName + '\n' +
                "Email Address: " + expectedEmail + " | Social Security: " + expectedSsn + '\n' +
                "Marital Status: " + expectedMaritalStatus + " | Cell Phone: " + expectedCell);
        System.out.println('\n' + "You Can Check To See In your MySQL Database Whether These Credentials Match.");




    }

    @Test
    public void verifyPersonalInfoUpdate() throws SQLException {

        // Syntax to update certain column in the DataBase
        String expectedName = "(607) 009 0150";
        String personalInfo = "UPDATE tbl_mortagage SET b_cell='" + expectedName + "' where id='556'";
        // Send update query to mortagage table under user ID " " and changes it from actual to expected
        DataBaseUtility.updateQuery(personalInfo);
    }

        @Test
        public void verifyPersonalInfoDuplicates () throws SQLException {
        // Syntax to verify for any duplicates in the DataBase

            // Testing for duplicate firstnames
            List<List<Object>> lisOflists = DataBaseUtility.getQueryResultAsListOfLists("SELECT\n" +
                    "\tb_firstName, COUNT(b_firstName),\n" +
                    "    b_lastName, COUNT(b_lastName),\n" +
                    "    b_email, COUNT(b_email),\n" +
                    "    b_ssn, COUNT(b_ssn),\n" +
                    "    b_marital, COUNT(b_marital),\n" +
                    "    b_cell, COUNT(b_cell)\n" +
                    "FROM\n" +
                    "\tloan.tbl_mortagage\n" +
                    "GROUP BY\n" +
                    "\tb_firstName,\n" +
                    "    b_lastName,\n" +
                    "    b_email, \n" +
                    "    b_ssn, \n" +
                    "    b_marital, \n" +
                    "    b_cell\n" +
                    "HAVING COUNT(b_firstName) > 1\n" +
                    "    AND COUNT(b_lastName) > 1\n" +
                    "\tAND COUNT(b_email) > 1\n" +
                    "    AND COUNT(b_ssn) > 1\n" +
                    "    AND COUNT(b_marital) > 1\n" +
                    "    AND COUNT(b_cell) > 1;");

            // If the list is empty test passes
            Assert.assertFalse(lisOflists.isEmpty(), "The list is not empty, its size is " + lisOflists.size());
            System.out.println(lisOflists);




        }


    }
