package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.*;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

public class EmploymentAndIncome {

        @Test
    public void EmploymentAndIncome ( ) {

        DataBaseUtility.createConnection ( );
        System.out.println ( " Connection is created " );
        Faker fake = new Faker ( );

        /*------------------------------login--------------------------------------------*/
        LoginPage loginPage = new LoginPage ( );
        loginPage.usernameField.sendKeys (
                 ConfigReader.getProperty ( "emailTK" ) );
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


       /* -----------------------Rental Payment ---------------------------------------------------*/
        ExpensesPage expensesPage = new ExpensesPage();
        Faker faker = new Faker();
        if (!expensesPage.rentChekBox.isSelected()) {
            expensesPage.rentChekBox.click();
        }
        expensesPage.monthlyRentalPayment.sendKeys(faker.number().digits(4));
        expensesPage.nextButton.click();


        /*---------------------------Employment and Income---------------------------------------*/
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
    }



}
