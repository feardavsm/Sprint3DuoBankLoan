package tests;

import com.github.javafaker.Faker;
import javafx.util.converter.LocalDateStringConverter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PersonalInformationPage;
import pages.PreApprovalDetaisPage;
import utilities.CSVReader;
import utilities.ConfigReader;
import utilities.Driver;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;


public class PersonalInformationTests extends TestBase {

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
    public void verifyWithValidInformation(String firstName, String middleName, String lastName,
                                            String email, String dateOfBirth, String ssn,
                                            String cellPhone, String homePhone) {

        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        if (!personalInformationPage.coBorrowerNoCheckBox.isSelected()) {
            personalInformationPage.coBorrowerNoCheckBox.click();
        }
        personalInformationPage.firstName.sendKeys(firstName);
        personalInformationPage.middleName.sendKeys(middleName);
        personalInformationPage.lastName.sendKeys(lastName);
        Select selectBoxSuffix = new Select(personalInformationPage.suffixDropDownList);
        selectBoxSuffix.selectByIndex((int) (1 + (Math.random() * 5)));
        personalInformationPage.email.sendKeys(email);
        personalInformationPage.dateOfBirth.sendKeys(dateOfBirth);
        personalInformationPage.ssn.sendKeys(ssn);
        Select selectBoxStatus = new Select(personalInformationPage.martialStatus);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        personalInformationPage.cellPhone.sendKeys(cellPhone);
        personalInformationPage.homePhone.sendKeys(homePhone);
        if (!personalInformationPage.privacyPolicyCheckBox.isSelected()) {
            personalInformationPage.privacyPolicyCheckBox.click();
        }
        personalInformationPage.nextButton.click();
    }


}
