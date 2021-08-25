package uitests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EmploymentAndIncomePage;
import pages.LoginPage;
import utilities.ConfigReader;

public class EmploymentAndIncomeTests extends TestBase{

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
        ExpensesTests expensesTests = new ExpensesTests();
        expensesTests.verifyWithRentCheckBox();
    }

    @Test
    public void verifyWithValidDataEmploymentAndIncome() {
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
    }

    @Test
    public void verifyWithNoCredentials() {
        EmploymentAndIncomePage employmentAndIncomePage = new EmploymentAndIncomePage();
        employmentAndIncomePage.nextButton.click();
        Assert.assertTrue(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-4']//span[.='current step: ']")).isEnabled());
    }

}
