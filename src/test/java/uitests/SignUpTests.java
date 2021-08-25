package uitests;

import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.SignUpPage;
import utilities.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignUpTests extends TestBase {

    @BeforeMethod()
    public void setupMethod() {
        new LoginPage().signUpLink.click();
    }

    @DataProvider(name = "fromCsvFile1")
    public Object[][] getDataFromCSV3() throws IOException {
        return CSVReader.readData("mockDataForSignUpPage.csv");
    }


    @Test(dataProvider = "fromCsvFile1", groups = {"smoke"})
    public void signUpWithMockData(String firstName, String lastName, String email, String password) {
        SignUpPage signUpPage = new SignUpPage();
        logger.info("Entering first name");
        signUpPage.firstName.sendKeys(firstName);
        logger.info("Entering last name");
        signUpPage.lastName.sendKeys(lastName);
        logger.info("Entering email name");
        signUpPage.email.sendKeys(email);
        logger.info("Entering password name");
        signUpPage.password.sendKeys(password);
        logger.info("Clicking register button");
        signUpPage.registerButton.click();
        logger.info("Assertion register button");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/register.php"));
    }

    @Test(groups = {"smoke"})
    public void signUpWithoutData() {
        SignUpPage signUpPage = new SignUpPage();

        logger.info("Click register button without entering required information");
        signUpPage.registerButton.click();
        List<WebElement> signUpTable = new ArrayList<>();
        signUpTable.add(signUpPage.firstName);
        signUpTable.add(signUpPage.lastName);
        logger.info("Email");
        signUpTable.add(signUpPage.email);
        signUpTable.add(signUpPage.password);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean isRequired;

        for (int i = 0; i < signUpTable.size(); i++) {

            WebElement inputElement = signUpTable.get(i);
            isRequired = (Boolean) js.executeScript("return arguments[0].required;", inputElement);
            logger.info("Assertion of required fields");
            Assert.assertTrue(isRequired);
        }


    }

    @Test(groups = {"smoke"})
    public void signUpWithWrongEmailInput() {
        SignUpPage signUpPage = new SignUpPage();
        Faker fake = new Faker();
        logger.info("Entering first name");
        signUpPage.firstName.sendKeys(fake.name().firstName());
        logger.info("Entering last name");
        signUpPage.lastName.sendKeys(fake.name().lastName());
        logger.info("Entering the email in wrong format");
        signUpPage.email.sendKeys(fake.name().firstName()); //name instead of email.

        signUpPage.registerButton.click();

        WebElement inputEmail = signUpPage.email;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean requiredEmailAddressErrorMessage = (Boolean) js.executeScript("return arguments[0].required;", inputEmail);
        Assert.assertTrue(requiredEmailAddressErrorMessage);

    }


}
