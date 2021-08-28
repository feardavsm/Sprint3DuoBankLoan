package dbtests;

import com.github.javafaker.Faker;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import uitests.TestBase;
import utilities.DataBaseUtility;

import java.sql.SQLException;


public class LoginTest extends TestBase {

    @Test
    public void verifyUserSignUpFlowFromDatabaseToUI(){

        // Checking connection to database is successful
        logger.info("Connect to database");
        DataBaseUtility.createConnection();
        System.out.println("Connection successful");

        // Creating new user credentials
        logger.info("Creating a new user");
        String expectedEmailAddress = new Faker().internet().emailAddress();
        String expectedPassword = new Faker().internet().password();
        String md5hash = DigestUtils.md5Hex(expectedPassword);
        String expectedFirstName = new Faker().name().firstName();
        String expectedLastName = new Faker().name().lastName();

        // Inserting newly created credentials into the MySQL Database
        String query = "INSERT INTO loan.tbl_user ( email, password, first_name, last_name, phone, image, type, " +
                "created_at, modified_at, zone_id, church_id, country_id, active) " +
                "values " +
                "('"+expectedEmailAddress+"', '"+md5hash+"','"+expectedFirstName+"', '"+expectedLastName+"', '', '', " +
                "'2', '', '', '0', '0', '0', '1')";

        // Verifying the user credentials in the UI
        DataBaseUtility.updateQuery(query);
        logger.info("Verify the user creation on the UI");

        // Verify the user creation on the UI by logging with newly created credentials
        LoginPage loginPage = new LoginPage();
        logger.info("Logging in");
        loginPage.login(expectedEmailAddress, expectedPassword);
        Assert.assertFalse(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));

        // Printing the newly created credentials that were used to sign up and login and
        System.out.println("Email Address: " + expectedEmailAddress + " | Expected Password: " + expectedPassword + " | Hash Password: " + md5hash +
                " | First Name: " + expectedFirstName + " | Last Name: " + expectedLastName);

    }


    }
