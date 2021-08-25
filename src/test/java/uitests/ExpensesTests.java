package uitests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ExpensesPage;
import pages.LoginPage;

import utilities.ConfigReader;


public class ExpensesTests extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void loginSetup() {
        LoginPage loginPage = new LoginPage();
        do {
            loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        }
        while (!driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));

        loginPage.mortgageApplicationMenu.click();

        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
        PersonalInformationTests personalInformationTests = new PersonalInformationTests();
        personalInformationTests.verifyWithValidCredentials();
    }

    @Test
    public void verifyWithRentCheckBox() {
        ExpensesPage expensesPage = new ExpensesPage();
        Faker faker = new Faker();
        if (!expensesPage.rentChekBox.isSelected()) {
            expensesPage.rentChekBox.click();
        }
        expensesPage.monthlyRentalPayment.sendKeys(faker.number().digits(4));
        expensesPage.nextButton.click();
    }

    @Test
    public void verifyWithOwnCheckBox() {
        ExpensesPage expensesPage = new ExpensesPage();
        Faker faker = new Faker();
        expensesPage.ownChekBox.click();
        expensesPage.firstMortgageTotalPayment.sendKeys(faker.number().digits(6));
        expensesPage.nextButton.click();

    }

    @Test
    public void verifyWithNoCredentials() {
        ExpensesPage expensesPage = new ExpensesPage();
        expensesPage.nextButton.click();
        Assert.assertFalse(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-3']//span[.='current step: ']")).isEnabled());
    }

}
