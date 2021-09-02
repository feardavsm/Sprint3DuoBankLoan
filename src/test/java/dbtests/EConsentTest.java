package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import pages.LoginPage;
import uitests.TestBase;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EConsentTest extends TestBase {

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