package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PersonalInformationPage;
import utilities.ConfigReader;

public class PersonalInformationTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void browseTestSetup(){
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
        loginPage.mortgageApplicationMenu.click();
    }

    @Test
    public void verifyWithCorrectInformation() {


    }
}
