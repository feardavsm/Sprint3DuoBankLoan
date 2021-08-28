package dbtests;

import com.github.javafaker.Faker;
import org.apache.commons.codec.digest.DigestUtils;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.SignUpPage;
import uitests.TestBase;
import utilities.DataBaseUtility;

import java.util.List;
import java.util.Map;

public class SignUpTest extends TestBase {

    @Test
    public void verifyUserSignUpFlowFromUIToDatabase(){

        // Checking connection to database is successful
        DataBaseUtility.createConnection();
        System.out.println("Connection successful");

        // Sign Up New Random User Using Faker
        new LoginPage().signUpLink.click();
        SignUpPage signUpPage = new SignUpPage();
        Faker fake = new Faker();
        logger.info("Entering the first name");
        String expectedFirstName = fake.name().firstName();
        signUpPage.firstName.sendKeys(expectedFirstName);
        logger.info("Entering the last name");
        String expectedLastName = fake.name().lastName();
        signUpPage.lastName.sendKeys(expectedLastName);
        logger.info("Entering the email");
        String expectedEmail = fake.internet().emailAddress();
        signUpPage.email.sendKeys(expectedEmail);
        String expectedPassword = new Faker().internet().password();
        String md5hash = DigestUtils.md5Hex(expectedPassword); // Converts Hash Password
        logger.info("Entering the password");
        signUpPage.password.sendKeys(expectedPassword);
        signUpPage.registerButton.click();
        logger.info("Waiting for url to be validated");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/register.php"));


        // Send query to database to retrieve the information about the registered user
        String query = "select * from loan.tbl_user where email = '"+expectedEmail+"'";
        List<Map<String, Object>> listOfMaps = DataBaseUtility.getQueryResultListOfMaps(query);
        Map<String, Object> map = listOfMaps.get(0);
        System.out.println(map);


        // Comparing to make sure the registered data matches on the database
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(map.get("first_name").toString().toUpperCase(), expectedFirstName);
        softAssert.assertEquals(map.get("last_name").toString().toUpperCase(), expectedLastName);
        softAssert.assertEquals(map.get("email"), expectedEmail);
        softAssert.assertEquals(map.get("password"), md5hash);

        // After Successful SignUp, the system uses auto generated Faker credentials to login.
        LoginPage loginPage = new LoginPage();
        logger.info("Logging in");
        loginPage.login(expectedEmail, expectedPassword);
        Assert.assertFalse(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));
    }
}
