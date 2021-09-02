package dbtests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.*;
import uitests.*;
import utilities.ConfigReader;
import utilities.DataBaseUtility;

import java.sql.*;

public class ExpensesTest extends TestBase {
    // @BeforeMethod(alwaysRun = true)
    @Test
    public void RentalExpenses() throws SQLException {
        /*--- creating connection*/
        DataBaseUtility.createConnection();

        /*--- setting the faker */
        Faker fake = new Faker();

        /*--- short cut to the "Expenses" page*/
        LoginPage loginPage = new LoginPage();

        do {
            loginPage.login(ConfigReader.getProperty("username5"), ConfigReader.getProperty("password5"));
        }
        while (!driver.getCurrentUrl().
                equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));
        loginPage.mortgageApplicationMenu.click();
        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
        PersonalInformationTests personalInformationTests = new PersonalInformationTests();
        personalInformationTests.verifyWithValidCredentials();

        /*--- MONTHLY RENTAL PAYMENT */

        String expectedMonthlyRentalPay = fake.number().digits(4);
        ExpensesPage expensesPage = new ExpensesPage();

        if (!expensesPage.rentChekBox.isSelected()) {
            expensesPage.rentChekBox.click();
        }
        expensesPage.monthlyRentalPayment.sendKeys(expectedMonthlyRentalPay);
        expensesPage.nextButton.click();

        EmploymentAndIncomePage employmentAndIncomePage = new EmploymentAndIncomePage();

        Faker faker = new Faker();
        if (!employmentAndIncomePage.currentJob.isSelected()) {
            employmentAndIncomePage.currentJob.click();
        }
        employmentAndIncomePage.employName.sendKeys(faker.name().firstName());
        employmentAndIncomePage.position.sendKeys(faker.job().position());
        employmentAndIncomePage.city.sendKeys(faker.address().city());
        Select selectBoxStatus = new Select(employmentAndIncomePage.state);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        employmentAndIncomePage.startDate.sendKeys("12/08/1980");
        employmentAndIncomePage.monthlyGrossIncome.sendKeys(faker.number().digits(5));
        employmentAndIncomePage.monthlyOvertime.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyBonus.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyCommission.sendKeys(faker.number().digits(4));
        employmentAndIncomePage.monthlyDivident.sendKeys(faker.number().digits(3));
        employmentAndIncomePage.nextButton.click();

        CreditReportTest creditReportTest = new CreditReportTest();
        creditReportTest.positiveAnswerForCreditReport();

        EconsentTests econsentTests = new EconsentTests();
        econsentTests.EconsentWithValidCredentials();

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();",
                driver.findElement(By.xpath("//a[contains(text(),'Save')]")));

        String url = ConfigReader.getProperty("db_url");
        String user = ConfigReader.getProperty("db_user");
        String password = ConfigReader.getProperty("db_password");
        Connection conectionTK = (DriverManager.getConnection(url, user, password));
        Statement statement = conectionTK.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery("select * from loan.tbl_mortagage;");
        resultSet.last();
        Object actualMonthlyRentalPay = resultSet.getObject("monthly_rental_payment");
        if (expectedMonthlyRentalPay.equals(actualMonthlyRentalPay)) {
            System.out.println("System is working perfect");
        } else {
            System.out.println("System needs to be fixed!");
        }
        System.out.println("Displaying Expected Monthly Rental Pay : " + expectedMonthlyRentalPay);
        System.out.println("Displaying  Actual  Monthly Rental Pay : " + actualMonthlyRentalPay);

    }


}