package tests;

import com.github.javafaker.Faker;
import javafx.util.converter.LocalDateStringConverter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PersonalInformationPage;
import pages.PreApprovalDetaisPage;
import utilities.ConfigReader;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;


public class PersonalInformationTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void loginSetup(){

        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getProperty("username1"), ConfigReader.getProperty("password1"));
        loginPage.mortgageApplicationMenu.click();

        PreApprovalDetailsTests preApproval = new PreApprovalDetailsTests();
        preApproval.positiveTestPreApprovalDetails();
    }

    Faker fake = new Faker();

    @Test
    public void verifyWithValidInformation() throws InterruptedException {

        PersonalInformationPage personalInformationPage = new PersonalInformationPage();
        personalInformationPage.firstName.sendKeys(fake.name().firstName());
        personalInformationPage.middleName.sendKeys(fake.name().firstName());
        personalInformationPage.lastName.sendKeys(fake.name().lastName());

        Select selectBox = new Select(personalInformationPage.suffixDropDownList);
        selectBox.selectByIndex((int)(1+(Math.random()*5)));

        personalInformationPage.email.sendKeys(fake.internet().emailAddress());
        personalInformationPage.dateOfBirth.sendKeys("12122021");
        personalInformationPage.ssn.sendKeys(fake.regexify("([0-9]{9})"));

        Select selectBox2 = new Select(personalInformationPage.martialStatus);
        selectBox2.selectByIndex((int)(1+(Math.random()*3)));

        personalInformationPage.cellPhone.sendKeys(fake.phoneNumber().cellPhone());
        personalInformationPage.homePhone.sendKeys(fake.phoneNumber().phoneNumber());
        personalInformationPage.privacyPolicyCheckBox.isSelected();
        personalInformationPage.nextButton.click();



    }
}
