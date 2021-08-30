package dbtests;


import com.github.javafaker.Faker;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.*;
import uitests.TestBase;
import utilities.ConfigReader;
import utilities.DataBaseUtility;




public class Expenses extends TestBase {

    // @BeforeMethod(alwaysRun = true)
    @Test
    public void RentalExpenses ( ) {

        DataBaseUtility.createConnection ( );
        System.out.println ( " Connection is created " );
        Faker fake = new Faker ( );


        /*---------------------------------login--------------------------------------------*/
        LoginPage loginPage = new LoginPage ( );
        loginPage.usernameField.sendKeys ( ConfigReader.getProperty ( "emailTK" ) );
        loginPage.passwordField.sendKeys ( ConfigReader.getProperty ( "passwordTK" ) );
        loginPage.loginButton.click ( );
        loginPage.mortgageApplicationMenu.click ( );

        /*-----------------------------preApprovalPage----------------------------------------*/
        PreApprovalDetaisPage preApprovalDetaisPage = new PreApprovalDetaisPage ( );
        if ( !preApprovalDetaisPage.checkBoxrealtor1.isSelected ( ) ) {
            preApprovalDetaisPage.checkBoxrealtor1.click ( );
        }
        preApprovalDetaisPage.realtorInfo.sendKeys ( fake.internet ( ).emailAddress ( ) );
        preApprovalDetaisPage.estimatedPrice.sendKeys ( "20000000" );
        preApprovalDetaisPage.downPayment.sendKeys ( "2000000" );
        preApprovalDetaisPage.next.click ( );


        /*-----------------------PersonalInformationPage---------------------------------------*/
        PersonalInformationPage personalInformationPage = new PersonalInformationPage ( );
        if ( !personalInformationPage.coBorrowerNoCheckBox.isSelected ( ) ) {
            personalInformationPage.coBorrowerNoCheckBox.click ( );
        }
        personalInformationPage.firstName.sendKeys ( fake.name ( ).firstName ( ) );
        personalInformationPage.middleName.sendKeys ( fake.name ( ).firstName ( ) );
        personalInformationPage.lastName.sendKeys ( fake.name ( ).lastName ( ) );
        Select selectBoxSuffix = new Select ( personalInformationPage.suffixDropDownList );
        selectBoxSuffix.selectByIndex ( (int) ( 1 + ( Math.random ( ) * 5 ) ) );
        personalInformationPage.email.sendKeys ( fake.internet ( ).emailAddress ( ) );
        personalInformationPage.dateOfBirth.sendKeys ( "01/01/2000" );
        personalInformationPage.ssn.sendKeys ( fake.number ( ).digits ( 9 ) );
        Select selectBoxStatus = new Select ( personalInformationPage.martialStatus );
        selectBoxStatus.selectByIndex ( (int) ( 1 + ( Math.random ( ) * 3 ) ) );
        personalInformationPage.cellPhone.sendKeys ( fake.phoneNumber ( ).cellPhone ( ) );
        personalInformationPage.homePhone.sendKeys ( fake.phoneNumber ( ).phoneNumber ( ) );
        if ( !personalInformationPage.privacyPolicyCheckBox.isSelected ( ) ) {
            personalInformationPage.privacyPolicyCheckBox.click ( );
        }
        personalInformationPage.nextButton.click ( );

       /* ------------------------rental payment ---------------------------------------------------*/

        ExpensesPage expensesPage = new ExpensesPage();
        Faker faker = new Faker();
        if (!expensesPage.rentChekBox.isSelected()) {
            expensesPage.rentChekBox.click();
        }
        String expectedMonthlyRentalPay=faker.number().digits(4);
        expensesPage.monthlyRentalPayment.sendKeys(expectedMonthlyRentalPay);
        expensesPage.nextButton.click();
        // Inserting newly created credentials into the MySQL Database
        String rentalExpenses=
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
                "('1', '', '', '1', 'Purchase a Home', '', " +
                "'', '', '', " +
                "'Checking/Savings (most recent bank statement)', '', '2', '', '', '', '', " +
                "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', " +
                "'"+expectedMonthlyRentalPay+"', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', " +
                "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', " +
                "'', '', '', '', '')";
                DataBaseUtility.updateQuery(rentalExpenses);

                System.out.println("Displaying Expected Monthly Rental Pay : "+expectedMonthlyRentalPay);


            EmploymentAndIncomePage employmentAndIncomePage = new EmploymentAndIncomePage();
        if (!employmentAndIncomePage.currentJob.isSelected()) {
            employmentAndIncomePage.currentJob.click();
        }
        employmentAndIncomePage.employName.sendKeys(faker.name().firstName());
        employmentAndIncomePage.position.sendKeys(faker.job().position());
        employmentAndIncomePage.city.sendKeys(faker.address().city());
        Select selectBoxStatus1 = new Select(employmentAndIncomePage.state);
        selectBoxStatus1.selectByIndex((int) (1 + (Math.random() * 3)));
        employmentAndIncomePage.startDate.sendKeys("12/08/1980");
        employmentAndIncomePage.monthlyGrossIncome.sendKeys(faker.number().digits(5));
        employmentAndIncomePage.monthlyOvertime.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyBonus.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyCommission.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyDivident.sendKeys(faker.number().digits(3));
        employmentAndIncomePage.nextButton.click();

        CreditReportPage creditReportPage = new CreditReportPage();
        creditReportPage.yesCheckBox.click();
        creditReportPage.nextButton.click();

        EconsentPage econsentPage=new EconsentPage();
        econsentPage.firsName.sendKeys(ConfigReader.getProperty("firstnameTK"));
        econsentPage.lastName.sendKeys(ConfigReader.getProperty("lastnameTK"));
        econsentPage.email.sendKeys(ConfigReader.getProperty("emailTK"));
        econsentPage.agreeButton.click();
        econsentPage.nextButton.click();

        SummaryPage summaryPage = new SummaryPage();
        summaryPage.saveButton.click( );




                /* "INSERT INTO loan.tbl_mortagage (id, realtor_status, realtor_info," +
                " loan_officer_status, purpose_loan, est_purchase_price, down_payment, " +
                "down_payment_percent, total_loan_amount, src_down_payment, add_fund_available," +
                " apply_co_borrower, b_firstName, b_middleName, b_lastName, b_suffix, b_email, " +
                "b_dob, b_ssn, b_marital, b_cell, b_home, c_firstName, c_middleName, c_lastName, " +
                "c_suffix, c_email, c_dob, c_ssn, c_marital, c_cell, c_home, rent_own_status, " +
                "('"+ExpectedMonthlyRentalPay+"', first_mortagage_total_payment, employer_name, position, " +
                "city, state, start_date, end_date, current_job, co_employer_name, co_position, " +
                "co_city, co_state, co_start_date, co_end_date, co_current_job, gross_monthly_income," +
                " monthly_over_time, monthly_bonuses, monthly_commision, monthly_dividents, " +
                "c_gross_monthly_income, c_monthly_over_time, c_monthly_bonuses, c_monthly_commision," +
                " c_monthly_dividents, add_belong, income_source, amount, eConsent_declarer, " +
                "eConsent_declarer_FirstName, eConsent_declarer_LastName, eConsent_declarer_Email, " +
                "created_on, modified_on, loan_status, user_id, active)  ";*/

       /* String query = "INSERT INTO loan.tbl_user ( email, password, first_name, last_name, phone, image, type, " +
                "created_at, modified_at, zone_id, church_id, country_id, active) " +
                "values " +
                "('"+expectedEmailAddress+"', '"+md5hash+"','"+expectedFirstName+"', '"+expectedLastName+"', '', '', " +
                "'2', '', '', '0', '0', '0', '1')";
*/
    }
    @Test
    public void MortageExpenses ( ) {

        DataBaseUtility.createConnection ( );
        System.out.println ( " Connection is created " );
        Faker fake = new Faker ( );


        /*------------------------------login--------------------------------------------*/
        LoginPage loginPage = new LoginPage ( );
        loginPage.usernameField.sendKeys ( ConfigReader.getProperty ( "emailTK" ) );
        loginPage.passwordField.sendKeys ( ConfigReader.getProperty ( "passwordTK" ) );
        loginPage.loginButton.click ( );
        loginPage.mortgageApplicationMenu.click ( );

        /*-------------------------preApprovalPage----------------------------------------*/
        PreApprovalDetaisPage preApprovalDetaisPage = new PreApprovalDetaisPage ( );
        if ( !preApprovalDetaisPage.checkBoxrealtor1.isSelected ( ) ) {
            preApprovalDetaisPage.checkBoxrealtor1.click ( );
        }
        preApprovalDetaisPage.realtorInfo.sendKeys ( fake.internet ( ).emailAddress ( ) );
        preApprovalDetaisPage.estimatedPrice.sendKeys ( "20000000" );
        preApprovalDetaisPage.downPayment.sendKeys ( "2000000" );
        preApprovalDetaisPage.next.click ( );


        /*--------------------PersonalInformationPage----------------------------------------*/
        PersonalInformationPage personalInformationPage = new PersonalInformationPage ( );
        if ( !personalInformationPage.coBorrowerNoCheckBox.isSelected ( ) ) {
            personalInformationPage.coBorrowerNoCheckBox.click ( );
        }
        personalInformationPage.firstName.sendKeys ( fake.name ( ).firstName ( ) );
        personalInformationPage.middleName.sendKeys ( fake.name ( ).firstName ( ) );
        personalInformationPage.lastName.sendKeys ( fake.name ( ).lastName ( ) );
        Select selectBoxSuffix = new Select ( personalInformationPage.suffixDropDownList );
        selectBoxSuffix.selectByIndex ( (int) ( 1 + ( Math.random ( ) * 5 ) ) );
        personalInformationPage.email.sendKeys ( fake.internet ( ).emailAddress ( ) );
        personalInformationPage.dateOfBirth.sendKeys ( "01/01/2000" );
        personalInformationPage.ssn.sendKeys ( fake.number ( ).digits ( 9 ) );
        Select selectBoxStatus = new Select ( personalInformationPage.martialStatus );
        selectBoxStatus.selectByIndex ( (int) ( 1 + ( Math.random ( ) * 3 ) ) );
        personalInformationPage.cellPhone.sendKeys ( fake.phoneNumber ( ).cellPhone ( ) );
        personalInformationPage.homePhone.sendKeys ( fake.phoneNumber ( ).phoneNumber ( ) );
        if ( !personalInformationPage.privacyPolicyCheckBox.isSelected ( ) ) {
            personalInformationPage.privacyPolicyCheckBox.click ( );
        }
        personalInformationPage.nextButton.click ( );

       /* -----------------------Mortagage Payment ---------------------------------------------------*/

        ExpensesPage expensesPage = new ExpensesPage();
        Faker faker = new Faker();
        expensesPage.ownChekBox.click();
        String expectedMortagePayment=faker.number().digits(6);
        expensesPage.firstMortgageTotalPayment.sendKeys(expectedMortagePayment);
        expensesPage.nextButton.click();


    }




}


