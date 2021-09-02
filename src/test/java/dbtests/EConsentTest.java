package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import uitests.TestBase;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EConsentTest extends TestBase {

    @Test
    public void verifyUserSignUpFlowFromUIToDatabase() {

        // Checking connection to database is successful

        DataBaseUtility.createConnection();
        System.out.println("Connection successful");

        Faker fake = new Faker();
        // comment


        /*---------------------------------login--------------------------------------------*/
        LoginPage loginPage = new LoginPage ( );
        loginPage.usernameField.sendKeys ( ConfigReader.getProperty ( "username3" ) );
        loginPage.passwordField.sendKeys ( ConfigReader.getProperty ( "password3" ) );
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
        Select selectBoxStatus = new Select ( personalInformationPage.maritalStatus );
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


        /* -----------------------Credit Report ---------------------------------------------------*/
        CreditReportPage creditReportPage = new CreditReportPage();
        if (!creditReportPage.yesCheckBox.isSelected()) {
            creditReportPage.nextButton.click();
        }

        /* -----------------------EConsent Page ---------------------------------------------------*/
        EconsentPage econsentPage = new EconsentPage();
        Select selectBoxSuffix1 = new Select ( econsentPage.suffixDropDownList );
        selectBoxSuffix1.selectByIndex ( (int) ( 1 + ( Math.random ( ) * 3 ) ) );
        econsentPage.firsName.sendKeys ( fake.name ( ).firstName ( ) );
        econsentPage.lastName.sendKeys ( fake.name ( ).lastName ( ) );
        econsentPage.email.sendKeys ( fake.internet ( ).emailAddress ( ) );



    }

    @Test //Verifying information from DB
    public void verifyInfoFromDB() throws SQLException {

        DataBaseUtility.createConnection();


        String expectedFirstName = "Cameron";
        String expectedLastName = "Parisian";
        String expectedEmail = "dalton.schmidt@hotmail.com";

        String query = "select * from tbl_mortagage where eConsent_declarer_Email = '" + expectedEmail + "'";
        List<Map<String, Object>> listOfMaps = DataBaseUtility.getQueryResultListOfMaps(query);
        Map<String, Object> map = listOfMaps.get(0);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(map.get("eConsent_declarer_Email"), expectedEmail);
        softAssert.assertEquals(map.get("eConsent_declarer_FirstName"), expectedFirstName);
        softAssert.assertEquals(map.get("eConsent_declarer_LastName"), expectedLastName);
        softAssert.assertAll();
    }

    @Test //Updating Information in DB
    public void updateInfoInDB() throws SQLException {


        String expectedFirstName = "Tess";
        String expectedLastName = "Watson";
        String expectedEmail = "wtess@gmail.com";
        String updatedQuery = "update tbl_mortagage set eConsent_declarer_Email='" + expectedEmail + "', eConsent_declarer_FirstName='" + expectedFirstName + "', eConsent_declarer_LastName='" + expectedLastName + "' where id='519'";
        DataBaseUtility.updateQuery(updatedQuery);

        String selectedQuery = "select * from tbl_mortagage where id = '519'";
        List<Map<String, Object>> listOfMaps = DataBaseUtility.getQueryResultListOfMaps(selectedQuery);
        Map<String, Object> map = listOfMaps.get(0);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(map.get("eConsent_declarer_Email"), expectedEmail);
        softAssert.assertEquals(map.get("eConsent_declarer_FirstName"), expectedFirstName);
        softAssert.assertEquals(map.get("eConsent_declarer_LastName"), expectedLastName);
        softAssert.assertAll();
    }

    @Test //Checking if there are duplicate emails
    public void verifyNoDuplicateEmails() {
        List<List<Object>> lisOfLists = DataBaseUtility.getQueryResultAsListOfLists("select eConsent_declarer_Email, count(*) from tbl_mortagage group by b_email having count(*)>1;");
        DataBaseUtility.getColumnNames("select * from tbl_mortagage limit 1");
        Assert.assertTrue(lisOfLists.isEmpty(), "The list is not empty, its size is " + lisOfLists.size()); //there is a duplicate email: wtess9539@gmail.com
    }

    @Test //Checking if Econsent page has all columns
    public void verifyEConsentHasALlColumns(){


        List<String> expectedColumns = Arrays.asList(
                "eConsent_declarer", "eConsent_declarer_FirstName", "eConsent_declarer_LastName", "eConsent_declarer_Email");
        List<String> actualColumns = DataBaseUtility.getColumnNames("select 'eConsent_declarer', 'eConsent_declarer_FirstName','eConsent_declarer_LastName','eConsent_declarer_Email'from tbl_mortagage limit 1");

        Assert.assertEquals(actualColumns, expectedColumns);
    }

    @Test //Verify Econsent field types
    public void verifyECOnsentFieldType(){

        String query = "update tbl_mortagage set eConsent_declarer_LastName ='Guliyeva' where id='314'";

        try{
            DataBaseUtility.updateQuery(query);
            Assert.assertTrue(true);
        }catch(Exception exception1){
            Assert.assertTrue(false);
        }
    }
}