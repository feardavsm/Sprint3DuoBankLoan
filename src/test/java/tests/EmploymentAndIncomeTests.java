package tests;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.EmploymentAndIncomePage;
import pages.LoginPage;
import pages.PersonalInformationPage;
import utilities.CSVReader;
import utilities.ConfigReader;

import java.io.IOException;

public class EmploymentAndIncomeTests extends TestBase{

    @BeforeMethod(alwaysRun = true)
    public void loginSetup() {
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        loginPage.mortgageApplicationMenu.click();

        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
    }

    @DataProvider(name = "fromCsvFile")
    public Object[][] getDataFromCSV() throws IOException {
        return CSVReader.readData("validTestDataForPersonalInfoPage.csv");
    }

    @Test(dataProvider = "fromCsvFile")
    public void verifyWithValidDataEmploymentAndIncome(String firstName, String middleName, String lastName,
                                            String email, String dateOfBirth, String ssn,
                                            String cellPhone, String homePhone) {

        ExpensesTests expenses = new ExpensesTests();
        expenses.verifyWithValidDataExpenses(firstName, middleName, lastName,
                email, dateOfBirth, ssn, cellPhone, homePhone);

        EmploymentAndIncomePage employmentAndIncomePage = new EmploymentAndIncomePage();
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        if (!employmentAndIncomePage.currentJob.isSelected()) {
            employmentAndIncomePage.currentJob.click();
        }
        employmentAndIncomePage.employName.sendKeys(firstName);
        employmentAndIncomePage.position.sendKeys("Automation Engineer");
        employmentAndIncomePage.city.sendKeys("United State");
        Select selectBoxStatus = new Select(employmentAndIncomePage.state);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        employmentAndIncomePage.startDate.sendKeys("12/08/1980");
        employmentAndIncomePage.monthlyGrossIncome.sendKeys("10000");
        employmentAndIncomePage.monthlyOvertime.sendKeys("5000");
        employmentAndIncomePage.monthlyBonus.sendKeys("3000");
        employmentAndIncomePage.monthlyCommission.sendKeys("2000");
        employmentAndIncomePage.monthlyDivident.sendKeys("200");

        personalInformationPage.nextButton.click();
    }

}
