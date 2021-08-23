package utilities;

import com.github.javafaker.Faker;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.EmployementAndIncomePageElements;
import pages.EmployementAndIncomePageElements;
import pages.ExpensesPageElements;
import pages.LoginPage;
import tests.PersonalInformationTests;
import tests.PreApprovalDetailsTests;
import tests.TestBase;

public class SequenceOfPagesOpening extends TestBase {





    @Test

    public void LoginPageOpening() {

        LoginPage loginPage = new LoginPage();
        loginPage.usernameField.sendKeys(ConfigReader.getProperty("username1"));
        loginPage.passwordField.sendKeys(ConfigReader.getProperty("password1"));
        loginPage.loginButton.click();
        loginPage.mortgageApplicationMenu.click();



        PreApprovalDetailsTests preApprovalDetailsTests=new PreApprovalDetailsTests();
        preApprovalDetailsTests.positiveTestPreApprovalDetails();
        PersonalInformationTests personalInformationTests=new PersonalInformationTests();
        personalInformationTests.verifyWithValidCredentials("Sholita","Lolita","Molita","lolita.molita@gmail.com","09/12/21","673627736","7032313456","7034356273");


        ExpensesPageElements expensesPageElements =new ExpensesPageElements();
        expensesPageElements.OwnAHouseButton.click();
        expensesPageElements.totalMortgagePayment.sendKeys("200393");
        expensesPageElements.nextButton.click();

        Faker fake=new Faker();
        EmployementAndIncomePageElements employementAndIncomePage=new EmployementAndIncomePageElements();
        employementAndIncomePage.checkboxCurrentJob.click();
        employementAndIncomePage.employerName1.sendKeys(fake.name().firstName());
        employementAndIncomePage.position1.sendKeys(fake.job().position());
        employementAndIncomePage.city1.sendKeys(fake.address().city());
        Select slt=new Select(employementAndIncomePage.state1);
        slt.selectByIndex(2);
        employementAndIncomePage.start_date.sendKeys("08/04/2021");
        employementAndIncomePage.grossMonthlyIncome.sendKeys("6289328");
        employementAndIncomePage.monthlyOverTime.sendKeys("72872");
        employementAndIncomePage.monthlyBonuses.sendKeys("738127");
        employementAndIncomePage.monthlyCommission.sendKeys("89282");
        employementAndIncomePage.monthlyDividents.sendKeys("74874");
        employementAndIncomePage.nextButton.click();


    }}
