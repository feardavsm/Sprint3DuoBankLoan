package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SignUpPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SignUpTests extends TestBase {

    @BeforeMethod()
    public void setupMethod() {
        new LoginPage().signUpLink.click();
    }


    @Test(groups = {"smoke"})
    public void signUpWithFaker() {
        SignUpPage signUpPage = new SignUpPage();
        Faker fake = new Faker();
        signUpPage.firstName.sendKeys(fake.name().firstName());
        signUpPage.lastName.sendKeys(fake.name().lastName());
        String email = fake.internet().emailAddress();
        signUpPage.email.sendKeys(email);
        String password = fake.internet().password();
        signUpPage.password.sendKeys(password);

        signUpPage.registerButton.click();

        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/register.php"));

    }

    @Test(groups = {"smoke"})
    public void signUpWithoutData() {
        SignUpPage signUpPage = new SignUpPage();

        signUpPage.registerButton.click();

        List<WebElement> signUpTable = new ArrayList<>();
        signUpTable.add(signUpPage.firstName);
        signUpTable.add(signUpPage.lastName);
        signUpTable.add(signUpPage.email);
        signUpTable.add(signUpPage.password);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean isRequired;

        for (int i = 0; i < signUpTable.size(); i++) {

            WebElement inputElement = signUpTable.get(i);
            isRequired = (Boolean) js.executeScript("return arguments[0].required;", inputElement);
            Assert.assertTrue(isRequired);
        }


    }

    @Test(groups = {"smoke"})
    public void signUpWithWrongEmailInput() {
        SignUpPage signUpPage = new SignUpPage();
        Faker fake = new Faker();
        signUpPage.firstName.sendKeys(fake.name().firstName());
        signUpPage.lastName.sendKeys(fake.name().lastName());
        signUpPage.email.sendKeys(fake.name().firstName()); //name instead of email.

        signUpPage.registerButton.click();

        WebElement inputEmail = signUpPage.email;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean requiredEmailAddressErrorMessage = (Boolean) js.executeScript("return arguments[0].required;", inputEmail);
        Assert.assertTrue(requiredEmailAddressErrorMessage);

    }


}
