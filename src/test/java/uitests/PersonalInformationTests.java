package uitests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
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
        do {
            loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        }
        while (driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));

        loginPage.mortgageApplicationMenu.click();
        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
    }

    @Test()
    public void verifyWithValidCredentials() {
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        Faker faker = new Faker();
        logger.info("Selecting coBorrower checkbox");
        if (!personalInformationPage.coBorrowerNoCheckBox.isSelected()) {
            personalInformationPage.coBorrowerNoCheckBox.click();
        }
        logger.info("Entering first name");
        personalInformationPage.firstName.sendKeys(faker.name().firstName());
        logger.info("Entering middle name");
        personalInformationPage.middleName.sendKeys(faker.name().firstName());
        logger.info("Entering last name");
        personalInformationPage.lastName.sendKeys(faker.name().lastName());
        logger.info("Selecting suffix checkbox");
        Select selectBoxSuffix = new Select(personalInformationPage.suffixDropDownList);
        selectBoxSuffix.selectByIndex((int) (1 + (Math.random() * 5)));
        logger.info("Entering email address");
        personalInformationPage.email.sendKeys(faker.internet().emailAddress());
        logger.info("Entering date of birth");
        personalInformationPage.dateOfBirth.sendKeys("01012000");
        logger.info("Entering ssn");
        personalInformationPage.ssn.sendKeys(faker.number().digits(9));
        logger.info("Selecting martial status");
        Select selectBoxStatus = new Select(personalInformationPage.martialStatus);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        logger.info("Entering cell phone");
        personalInformationPage.cellPhone.sendKeys(faker.phoneNumber().cellPhone());
        logger.info("Entering home phone");
        personalInformationPage.homePhone.sendKeys(faker.phoneNumber().phoneNumber());
        logger.info("Selecting privacy policy checkbox");
        if (!personalInformationPage.privacyPolicyCheckBox.isSelected()) {
            personalInformationPage.privacyPolicyCheckBox.click();
        }
        logger.info("Clicking next button");
        personalInformationPage.nextButton.click();
    }

    @DataProvider(name = "fromCsvFile")
    public Object[][] getDataFromCSV() throws IOException {
        return CSVReader.readData("validTestDataForPersonalInfoPage.csv");
    }

    @Test(dataProvider = "fromCsvFile")
    public void verifyWithValidCredentialsFromCsvFile(String firstName, String middleName, String lastName,
                                           String email, String dateOfBirth, String ssn,
                                           String cellPhone, String homePhone) {
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        logger.info("Selecting coBorrower checkbox");
        if (!personalInformationPage.coBorrowerNoCheckBox.isSelected()) {
            personalInformationPage.coBorrowerNoCheckBox.click();
        }
        logger.info("Entering first name");
        personalInformationPage.firstName.sendKeys(firstName);
        logger.info("Entering middle name");
        personalInformationPage.middleName.sendKeys(middleName);
        logger.info("Entering last name");
        personalInformationPage.lastName.sendKeys(lastName);
        logger.info("Selecting suffix checkbox");
        Select selectBoxSuffix = new Select(personalInformationPage.suffixDropDownList);
        selectBoxSuffix.selectByIndex((int) (1 + (Math.random() * 5)));
        logger.info("Entering email address");
        personalInformationPage.email.sendKeys(email);
        logger.info("Entering date of birth");
        personalInformationPage.dateOfBirth.sendKeys(dateOfBirth);
        logger.info("Entering ssn");
        personalInformationPage.ssn.sendKeys(ssn);
        logger.info("Selecting martial status");
        Select selectBoxStatus = new Select(personalInformationPage.martialStatus);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        logger.info("Entering cell phone");
        personalInformationPage.cellPhone.sendKeys(cellPhone);
        logger.info("Entering home phone");
        personalInformationPage.homePhone.sendKeys(homePhone);
        logger.info("Selecting privacy policy checkbox");
        if (!personalInformationPage.privacyPolicyCheckBox.isSelected()) {
            personalInformationPage.privacyPolicyCheckBox.click();
        }
        logger.info("Clicking next button");
        personalInformationPage.nextButton.click();
        logger.info("Assertion of next page");
        Assert.assertTrue(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-2']//span[.='current step: ']")).isEnabled());
    }


    @Test()
    public void verifyRequiredFields() {
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        logger.info("Clicking next button");
        personalInformationPage.nextButton.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean firstNameRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.firstName);
        logger.info("Checking whether username field is mandatory");
        Assert.assertTrue(firstNameRequired);
        boolean lastNameRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.lastName);
        logger.info("Checking whether lastname field is mandatory");
        Assert.assertTrue(lastNameRequired);
        boolean emailRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.email);
        logger.info("Checking whether email field is mandatory");
        Assert.assertTrue(emailRequired);
        boolean dateRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.dateOfBirth);
        logger.info("Checking whether date of birth field is mandatory");
        Assert.assertTrue(dateRequired, "Date of birth field is not mandatory.");
        boolean ssnRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.ssn);
        logger.info("Checking whether ssn field is mandatory");
        Assert.assertTrue(ssnRequired);
        boolean statusRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.martialStatus);
        logger.info("Checking whether status dropdown list is mandatory");
        Assert.assertTrue(statusRequired);
        boolean cellPhoneRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.cellPhone);
        logger.info("Checking whether cell phone field is mandatory");
        Assert.assertTrue(cellPhoneRequired);
        boolean privacyPolicyRequired = (Boolean) js.executeScript("return arguments[0].required;", personalInformationPage.privacyPolicyCheckBox);
        logger.info("Checking whether privacy policy checkbox is mandatory");
        Assert.assertTrue(privacyPolicyRequired);
    }


    @Test()
    public void verifyWithNoCredentials() {
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        logger.info("Clicking next button");
        personalInformationPage.nextButton.click();
        logger.info("Assertion current page");
        Assert.assertFalse(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-2']//span[.='current step: ']")).isEnabled());
    }


    @DataProvider(name = "fromCsvFile2")
    public Object[][] getDataFromCSV2() throws IOException {
        return CSVReader.readData("numericDataInNameFieldsForPersonalInfoPage.csv");
    }

    @Test(dataProvider = "fromCsvFile2")
    public void verifyNameFieldsWithNumericCredentials(String firstName, String middleName, String lastName,
                                           String email, String dateOfBirth, String ssn,
                                           String cellPhone, String homePhone) {
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        logger.info("Selecting coBorrower checkbox");
        if (!personalInformationPage.coBorrowerNoCheckBox.isSelected()) {
            personalInformationPage.coBorrowerNoCheckBox.click();
        }
        logger.info("Entering first name");
        personalInformationPage.firstName.sendKeys(firstName);
        logger.info("Entering middle name");
        personalInformationPage.middleName.sendKeys(middleName);
        logger.info("Entering last name");
        personalInformationPage.lastName.sendKeys(lastName);
        logger.info("Selecting suffix checkbox");
        Select selectBoxSuffix = new Select(personalInformationPage.suffixDropDownList);
        selectBoxSuffix.selectByIndex((int) (1 + (Math.random() * 5)));
        logger.info("Entering email address");
        personalInformationPage.email.sendKeys(email);
        logger.info("Entering date of birth");
        personalInformationPage.dateOfBirth.sendKeys(dateOfBirth);
        logger.info("Entering ssn");
        personalInformationPage.ssn.sendKeys(ssn);
        logger.info("Selecting martial status");
        Select selectBoxStatus = new Select(personalInformationPage.martialStatus);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        logger.info("Entering cell phone");
        personalInformationPage.cellPhone.sendKeys(cellPhone);
        logger.info("Entering home phone");
        personalInformationPage.homePhone.sendKeys(homePhone);
        logger.info("Selecting privacy policy checkbox");
        if (!personalInformationPage.privacyPolicyCheckBox.isSelected()) {
            personalInformationPage.privacyPolicyCheckBox.click();
        }
        logger.info("Clicking next button");
        personalInformationPage.nextButton.click();
        logger.info("Assertion of next page");
        Assert.assertTrue(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-2']//span[.='current step: ']")).isEnabled());
    }


    @DataProvider(name = "fromCsvFile3")
    public Object[][] getDataFromCSV3() throws IOException {
        return CSVReader.readData("invalidEmailAddresForPersonalInfoPage.csv");
    }

    @Test(dataProvider = "fromCsvFile3")
    public void verifyEmailFieldWithInvalidEmailAddress(String firstName, String middleName, String lastName,
                                                       String email, String dateOfBirth, String ssn,
                                                       String cellPhone, String homePhone) {
        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        logger.info("Selecting coBorrower checkbox");
        if (!personalInformationPage.coBorrowerNoCheckBox.isSelected()) {
            personalInformationPage.coBorrowerNoCheckBox.click();
        }
        logger.info("Entering first name");
        personalInformationPage.firstName.sendKeys(firstName);
        logger.info("Entering middle name");
        personalInformationPage.middleName.sendKeys(middleName);
        logger.info("Entering last name");
        personalInformationPage.lastName.sendKeys(lastName);
        logger.info("Selecting suffix checkbox");
        Select selectBoxSuffix = new Select(personalInformationPage.suffixDropDownList);
        selectBoxSuffix.selectByIndex((int) (1 + (Math.random() * 5)));
        logger.info("Entering email address");
        personalInformationPage.email.sendKeys(email);
        logger.info("Entering date of birth");
        personalInformationPage.dateOfBirth.sendKeys(dateOfBirth);
        logger.info("Entering ssn");
        personalInformationPage.ssn.sendKeys(ssn);
        logger.info("Selecting martial status");
        Select selectBoxStatus = new Select(personalInformationPage.martialStatus);
        selectBoxStatus.selectByIndex((int) (1 + (Math.random() * 3)));
        logger.info("Entering cell phone");
        personalInformationPage.cellPhone.sendKeys(cellPhone);
        logger.info("Entering home phone");
        personalInformationPage.homePhone.sendKeys(homePhone);
        logger.info("Selecting privacy policy checkbox");
        if (!personalInformationPage.privacyPolicyCheckBox.isSelected()) {
            personalInformationPage.privacyPolicyCheckBox.click();
        }
        logger.info("Checking error message");
        Assert.assertTrue(driver.getPageSource().contains("Please enter a valid email address."));
        personalInformationPage.nextButton.click();
        logger.info("Assertion of next page");
        Assert.assertTrue(driver.findElement(By.xpath("//a[@id='steps-uid-0-t-1']//span[.='current step: ']")).isEnabled());
    }

}
