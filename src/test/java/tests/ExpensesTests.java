package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ExpensesPage;
import pages.LoginPage;
import pages.PersonalInformationPage;

import utilities.ConfigReader;


public class ExpensesTests extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void loginSetup() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        loginPage.mortgageApplicationMenu.click();

        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
    }

    @Test
    public void verifyWithValidDataExpenses(String firstName, String middleName, String lastName,
                                            String email, String dateOfBirth, String ssn,
                                            String cellPhone, String homePhone) {

        PersonalInformationTests personalInformation = new PersonalInformationTests();
        personalInformation.verifyWithValidCredentials(firstName, middleName, lastName,
                email, dateOfBirth, ssn, cellPhone, homePhone);

        ExpensesPage expensesPage = new ExpensesPage();
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        if (!expensesPage.houseExpenses.isSelected()) {
            expensesPage.houseExpenses.click();
        }
        expensesPage.monthlyRentalPayment.sendKeys("2000");
        personalInformationPage.nextButton.click();
    }

}
