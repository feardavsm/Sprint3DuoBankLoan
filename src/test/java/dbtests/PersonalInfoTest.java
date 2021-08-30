package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PersonalInformationPage;
import uitests.PreApprovalDetailsTests;
import uitests.TestBase;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PersonalInfoTest extends TestBase {

    @Test
    public void verifyPersonalInformationFromDatabaseToUI() {


        //Signing Up
        LoginPage loginPage = new LoginPage();
        logger.info("Logging in");
        loginPage.login(ConfigReader.getProperty("username2"), ConfigReader.getProperty("password2"));
        Assert.assertFalse(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));

        loginPage.mortgageApplicationMenu.click();
        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();

        // Sign Up New Random User Using Faker
        PersonalInformationPage pip = new PersonalInformationPage();
        logger.info("Selecting coBorrower checkbox");
        if (!pip.coBorrowerNoCheckBox.isSelected()) {
            pip.coBorrowerNoCheckBox.click();
        }
        Faker faker = new Faker();
        logger.info("Entering first name");
        String expectedFirstName = faker.name().firstName();
        pip.firstName.sendKeys(expectedFirstName);
        logger.info("Entering middle name");
        String expectedMiddleName = faker.name().nameWithMiddle();
        pip.middleName.sendKeys(expectedMiddleName);
        logger.info("Entering last name");
        String expectedLastName = faker.name().lastName();
        pip.lastName.sendKeys(expectedLastName);
        logger.info("Selecting suffix checkbox");
        Select selectBoxSuffix = new Select(pip.suffixDropDownList);
        selectBoxSuffix.selectByIndex(1);
        logger.info("Entering email address");
        String expectedEmail = faker.internet().emailAddress();
        pip.email.sendKeys(expectedEmail);
        logger.info("Entering date of birth");
        pip.dateOfBirth.sendKeys("01271990");
        logger.info("Entering ssn");
        String expectedSsn = faker.number().digits(9);
        pip.ssn.sendKeys(expectedSsn);
        //pip.ssn.sendKeys(faker.number().digits(9));
        logger.info("Selecting marital status");
        Select selectBoxStatus = new Select(pip.maritalStatus);
        selectBoxStatus.selectByIndex(1);
        logger.info("Entering cell phone");
        String expectedCell = faker.phoneNumber().cellPhone();
        pip.cellPhone.sendKeys(expectedCell);
        //pip.cellPhone.sendKeys(faker.phoneNumber().cellPhone());
        logger.info("Entering home phone");
        String expectedHome = faker.phoneNumber().phoneNumber();
        pip.homePhone.sendKeys(expectedHome);
        //pip.homePhone.sendKeys(faker.phoneNumber().phoneNumber());
        logger.info("Selecting privacy policy checkbox");
        if (!pip.privacyPolicyCheckBox.isSelected()) {
            pip.privacyPolicyCheckBox.click();
        }
        logger.info("Clicking next button");
        pip.nextButton.click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-2']//span[.='current step: ']")).isEnabled());


        // Checking connection to database is successful
        logger.info("Connect to database");
        DataBaseUtility.createConnection();
        System.out.println("Connection successful");

        // Inserting newly created credentials into the MySQL Database
        String query = "INSERT INTO loan.tbl_mortagage (b_firstName,b_middleName,b_lastName," +
                "b_suffix,b_email,d_dob,b_ssn,b_marital,b_cell,b_home)" +
                "values" +
                "('"+expectedFirstName+"', '"+expectedMiddleName+"','"+expectedLastName+"' " +
                "'"+selectBoxSuffix+"','"+expectedEmail+"', '"+ pip.dateOfBirth+"', '"+expectedSsn+"', '"+ pip.maritalStatus+"', '"+expectedCell+"','"+expectedHome+"')";



        // Printing the newly created credentials that were used to sign up and login and
      System.out.println("First Name: " + expectedFirstName + " | Middle Name: " + expectedMiddleName +
             " | Last Name: " + expectedLastName + " | Suffix: " + selectBoxSuffix + " | Email Address: "
             + expectedEmail + " | Date of Birth: " +pip.dateOfBirth+" | Social Security: " + expectedSsn +
              " | Marital Status: " + selectBoxStatus + " | Cell Phone: " + expectedCell + " | Home Phone: " + expectedHome);



    }
}
