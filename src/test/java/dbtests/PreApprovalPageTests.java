package dbtests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import uitests.TestBase;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PreApprovalPageTests extends TestBase {




// BUSINESS RULES TESTING

    //1. Verify that the Mortgage Table has all the necessary columns
    @Test
    public void verifyMortgageTableALlColumns(){


        List<String> expectedColumns = Arrays.asList("id", "realtor_status", "realtor_info", "loan_officer_status", "purpose_loan", "est_purchase_price",
                "down_payment", "down_payment_percent", "total_loan_amount", "src_down_payment", "add_fund_available", "apply_co_borrower", "b_firstName", "b_middleName",
                "b_lastName", "b_suffix", "b_email", "b_dob", "b_ssn", "b_marital", "b_cell", "b_home", "c_firstName", "c_middleName", "c_lastName", "c_suffix",
                "c_email", "c_dob", "c_ssn", "c_marital", "c_cell", "c_home","rent_own_status",  "monthly_rental_payment", "first_mortagage_total_payment", "employer_name",
                "position", "city", "state", "start_date", "end_date", "current_job", "co_employer_name", "co_position", "co_city", "co_state", "co_start_date",
                "co_end_date", "co_current_job", "gross_monthly_income", "monthly_over_time", "monthly_bonuses", "monthly_commision", "monthly_dividents",
                "c_gross_monthly_income", "c_monthly_over_time", "c_monthly_bonuses", "c_monthly_commision", "c_monthly_dividents", "add_belong", "income_source",
                "amount", "eConsent_declarer", "eConsent_declarer_FirstName", "eConsent_declarer_LastName", "eConsent_declarer_Email", "created_on", "modified_on",
                "loan_status", "user_id", "active");

        List<String> actualColumns = DataBaseUtility.getColumnNames("select * from tbl_mortagage limit 1");

        Assert.assertEquals(actualColumns, expectedColumns);
    }


    //2. Verify that the Pre-Approval page contains all the necessary columns
    @Test
    public void verifyMortgageTablePreApprovalColumns(){


        List<String> expectedColumns = Arrays.asList("id", "realtor_status", "realtor_info", "loan_officer_status", "purpose_loan", "est_purchase_price",
                "down_payment", "down_payment_percent", "total_loan_amount", "src_down_payment", "add_fund_available");

        List<String> actualColumns = DataBaseUtility.getColumnNames("select id, realtor_status, realtor_info, loan_officer_status, purpose_loan, est_purchase_price, down_payment, down_payment_percent, total_loan_amount, \n" +
                "src_down_payment, add_fund_available from tbl_mortagage limit 1");

        Assert.assertEquals(actualColumns, expectedColumns);
    }

    //3. Verify Realtor Status field range - make sure that the user cannot enter value higher/lower than the int
    @Test
    public void verifyRealtorStatusFiledRange()  {
        String query = "update tbl_mortagage set loan_officer_status ='300000000000000' where id='314'";

        try{
            DataBaseUtility.updateQuery(query);
            Assert.assertTrue(false);
        }catch(Exception exception1){
            Assert.assertTrue(true);
        }

    }

    //4. Verify that the Realtor Status field accepts only numeric data
    @Test
    public void verifyRealtorStatusFieldType(){

        String query = "update tbl_mortagage set loan_officer_status ='abc' where id='314'";

        try{
            DataBaseUtility.updateQuery(query);
            Assert.assertTrue(false);
        }catch(Exception exception1){
            Assert.assertTrue(true);
        }
    }

    //5. Verify unicode support

    @Test
    public void verifyUnicodeSupport() throws SQLException {

        String expectedRealtorInfo = "黄 麗";
        String query = "update tbl_mortagage set realtor_info ='"+expectedRealtorInfo+"' where id='517'";
        DataBaseUtility.updateQuery(query);

        Map<String, Object> map = DataBaseUtility.getQueryResultListOfMaps("select * from tbl_mortagage where id = '517'").get(0);

        String actualRealtorInfo = (String)(map.get("realtor_info"));

        Assert.assertEquals(actualRealtorInfo, expectedRealtorInfo);
    }

    // 6. Verify that null values are not allowed for the primary key column
    // For the Mortgage table id column should not accept null value

    @Test
    public void verifyThrowsExceptionForNullValueForPrimaryKeyColumn() throws SQLException {
        String query = "update tbl_mortagage set id = null where id = '314'";

        try{
            DataBaseUtility.updateQuery(query);
            Assert.assertTrue(false);

        }catch(Exception exception1){
            Assert.assertTrue(true);

        }
        String queryToReverseTheNullValueBack = "update tbl_mortagage set id = '314' where id = '0'";
        DataBaseUtility.updateQuery(queryToReverseTheNullValueBack); //in case if query was accepted by the system sets the column value to the value before test
    }


















    }





