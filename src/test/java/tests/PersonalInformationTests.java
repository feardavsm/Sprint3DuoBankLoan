package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PersonalInformationPage;
import utilities.CSVReader;
import utilities.ConfigReader;

import java.io.IOException;


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
    public void verifyWithValidCredentials(String firstName, String middleName, String lastName,
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


    @Test()
    public void verifyWithNoCredentials() {

        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        personalInformationPage.nextButton.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean firstNameRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.firstName);
        Assert.assertTrue(firstNameRequired);
        boolean lastNameRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.lastName);
        Assert.assertTrue(lastNameRequired);
        boolean emailRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.email);
        Assert.assertTrue(emailRequired);
        boolean dateRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.dateOfBirth);
        Assert.assertTrue(dateRequired, "Date of birth field is not mandatory.");
        boolean ssnRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.ssn);
        Assert.assertTrue(ssnRequired);
        boolean statusRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.martialStatus);
        Assert.assertTrue(statusRequired);
        boolean cellPhoneRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.cellPhone);
        Assert.assertTrue(cellPhoneRequired);
        boolean privacyPhoneRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.privacyPolicyCheckBox);
        Assert.assertTrue(privacyPhoneRequired);
    }


}
