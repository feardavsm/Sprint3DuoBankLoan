package dbtests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import uitests.TestBase;
import utilities.DataBaseUtility;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.testng.Assert.assertEquals;

public class MortgageApplicationTestByPH extends TestBase {

    @Test
    public void verifyInfoFromDB() throws SQLException {

        DataBaseUtility.createConnection();

        String expectedRealtorName = "Leif Denesik";
        String expectedFirstName = "Genevie";
        String expectedLastName = "Kassulke";
        String expectedEmail = "rosalie.kub@gmail.com";

        String query = "select * from tbl_mortagage where realtor_info = '" + expectedRealtorName + "'";
        List<Map<String, Object>> listOfMaps = DataBaseUtility.getQueryResultListOfMaps(query);
        Map<String, Object> map = listOfMaps.get(0);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(map.get("realtor_info"), expectedRealtorName);
        softAssert.assertEquals(map.get("b_firstName"), expectedFirstName);
        softAssert.assertEquals(map.get("b_lastName"), expectedLastName);
        softAssert.assertEquals(map.get("b_email"), expectedEmail);
        softAssert.assertAll();
    }


    @Test
    public void updateInfoInDB() throws SQLException {

        String expectedRealtorName = "Parviz Hatamov";
        String expectedFirstName = "John";
        String expectedLastName = "Smith";
        String expectedEmail = "john.smith@gmail.com";
        String updatedQuery = "update tbl_mortagage set realtor_info='" + expectedRealtorName + "', b_firstName='" + expectedFirstName + "', b_lastName='" + expectedLastName + "', b_email='" + expectedEmail + "' where id='563'";
        DataBaseUtility.updateQuery(updatedQuery);

        String selectedQuery = "select * from tbl_mortagage where id = '563'";
        List<Map<String, Object>> listOfMaps = DataBaseUtility.getQueryResultListOfMaps(selectedQuery);
        Map<String, Object> map = listOfMaps.get(0);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(map.get("realtor_info"), expectedRealtorName);
        softAssert.assertEquals(map.get("b_firstName"), expectedFirstName);
        softAssert.assertEquals(map.get("b_lastName"), expectedLastName);
        softAssert.assertEquals(map.get("b_email"), expectedEmail);
        softAssert.assertAll();
    }


    @Test
    public void verifyNoDuplicateEmails() {
        List<List<Object>> lisOfLists = DataBaseUtility.getQueryResultAsListOfLists("select b_email, count(*) from tbl_mortagage group by b_email having count(*)>1;");
        Assert.assertTrue(lisOfLists.isEmpty(), "The list is not empty, its size is " + lisOfLists.size());
    }


    @Test
    public void verifyNoDuplicateSSN() {

        List<List<Object>> listOfLists = DataBaseUtility.getQueryResultAsListOfLists("select b_ssn from tbl_mortagage");
        List<String> ssnNumbers = new ArrayList<>();

        for (List<Object> e : listOfLists) {
            ssnNumbers.add((String) (e.get(0)));
        }

        Collections.sort(ssnNumbers);

        boolean noDuplicate = true;
        for (int i = 0; i < ssnNumbers.size() - 1; i++) {
            if (ssnNumbers.get(i).equals(ssnNumbers.get(i + 1))) {
                noDuplicate = false;
            }
        }
        Assert.assertTrue(noDuplicate, "There are duplicated SSN numbers in the list");
    }

    @Test
    public void verifyEstPurchasePriceFieldRange(){

        String query = "update tbl_mortagage set est_purchase_price ='5000000000' where id='600'";

        try{
            DataBaseUtility.updateQuery(query);
            Assert.assertTrue(true);
        }catch(Exception exception1){
            Assert.assertTrue(false);
        }
    }


}
