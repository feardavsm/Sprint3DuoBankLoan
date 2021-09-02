package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import pages.*;
import uitests.*;
import utilities.ConfigReader;
import utilities.DataBaseUtility;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static utilities.DataBaseUtility.executeQuery;


public class EmploymentAndIncomeTest extends TestBase {
    public static void main ( String[] args ) {
    }
    @Test
    public void CheckingTableStability ( ){

        DataBaseUtility.createConnection ( );
        System.out.println ( "Connection is successful with DataBase" );
        List<List<Object>> queryResultAsListOfList = DataBaseUtility.getQueryResultAsListOfLists
                ( "select * from loan.tbl_mortagage" );
        String expectedName = "Wonder Woman";
        Object actualName = queryResultAsListOfList.get ( 148 ).get ( 35 ) ;
        if (expectedName.equals(actualName)){
            System.out.println ("System is working perfect" );
        }else{
            System.out.println ("System needs to be fixed!" );
        }
        System.out.println ( "expected name: "+expectedName);
        System.out.println ( "actual name:   "+actualName);
    }
    /*in this testcase I'm checking if the entered information remains
    in the same row and do not move if there other changes*/

    @Test //bug
    public void EmploymentAndIncomeFunctionality ( ) throws SQLException {
        DataBaseUtility.createConnection ( );
        LoginPage loginPage = new LoginPage();
        do {
            loginPage.login(ConfigReader.getProperty("username5"), ConfigReader.getProperty("password5"));
        }
        while (!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));
        loginPage.mortgageApplicationMenu.click();
        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
        PersonalInformationTests personalInformationTests = new PersonalInformationTests();
        personalInformationTests.verifyWithValidCredentials();
        ExpensesTests expensesTests = new ExpensesTests();
        expensesTests.verifyWithRentCheckBox();
        EmploymentAndIncomePage employmentAndIncomePage = new EmploymentAndIncomePage();
        Faker fake= new Faker();
        if (!employmentAndIncomePage.currentJob.isSelected()) {
            employmentAndIncomePage.currentJob.click();
        }
        System.out.println ( "Connection is successful with DataBase" );
        String firstName = fake.name ( ).firstName ( );
        String positionE = fake.job ( ).position ( );
        String cityE = fake.address ( ).city ( );
        String grossMonthlyIncome = fake.number ( ).digits ( 5 );
        String monthlyOvertimeE = fake.number ( ).digits ( 4 );
        String monthlyBonusE = fake.number ( ).digits ( 4 );
        String monthlyCommissionE = fake.number ( ).digits ( 4 );
        String monthlyDividentE = fake.number ( ).digits ( 3 );
        employmentAndIncomePage.employName.sendKeys(firstName);
        employmentAndIncomePage.position.sendKeys(positionE);
        employmentAndIncomePage.city.sendKeys(cityE);
        Select selectBoxStatus = new Select (employmentAndIncomePage.state);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        employmentAndIncomePage.startDate.sendKeys("15/08/1987");
        employmentAndIncomePage.monthlyGrossIncome.sendKeys(grossMonthlyIncome);
        employmentAndIncomePage.monthlyOvertime.sendKeys(monthlyOvertimeE);
        employmentAndIncomePage.monthlyBonus.sendKeys(monthlyBonusE);
        employmentAndIncomePage.monthlyCommission.sendKeys(monthlyCommissionE);
        employmentAndIncomePage.monthlyDivident.sendKeys(monthlyDividentE);
        employmentAndIncomePage.nextButton.click();
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//a[contains(text(),'Save')]")));
        String url = ConfigReader.getProperty ( "db_url" );
        String user = ConfigReader.getProperty ( "db_user" );
        String password = ConfigReader.getProperty ( "db_password" );
        Connection conectionTK = ( DriverManager.getConnection ( url, user, password ) );
        Statement statement = conectionTK.createStatement ( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE );
        ResultSet resultSet = statement.executeQuery ( "select * from loan.tbl_mortagage;" );
        resultSet.last ( );
        String expectedName=firstName;
        Object actualName=resultSet.getObject ( "employer_name" );
        if (expectedName.equals(actualName)){
            System.out.println ("System is working perfect" );
        }else{
            System.out.println ("System needs to be fixed!" );
        }
        System.out.println ( "Displaying Expected EMPLOYER NAME : " + firstName );
        System.out.println ( "Displaying  Actual  EMPLOYER NAME : " + resultSet.getObject ( "employer_name" ) );

    }
    /*this test case is checking if the original expected EMPLOYER NAME is stored in the correct format and in the right field.
    I created the program that runs the "Mortgage application" and fills out it. After program  hit the
     button save It pulling , just created, information from in DataBase . And after that comparing  by using the if-else statement.*/







}
