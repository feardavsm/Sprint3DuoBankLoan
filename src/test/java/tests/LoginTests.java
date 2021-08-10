package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.SeleniumUtils;

public class LoginTests extends TestBase {


    @Test(groups = {"smoke"})
    public void appHealthCheck() {
        Assert.assertTrue(driver.getCurrentUrl().equals(ConfigReader.getProperty("url")));
    }

    @Test(groups = {"smoke"})
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage();
        loginPage.usernameField.sendKeys(ConfigReader.getProperty("username1"));
        loginPage.passwordField.sendKeys(ConfigReader.getProperty("password1"));
        loginPage.loginButton.click();
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));
    }

    Faker fake = new Faker();

    @Test()
    public void loginWithInvalidUsername() {
        LoginPage loginPage = new LoginPage();
        loginPage.usernameField.sendKeys(fake.name().fullName());
        loginPage.passwordField.sendKeys(fake.name().lastName());
        loginPage.loginButton.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean emailIsRequired = (Boolean) js.executeScript("return arguments[0].required;", loginPage.usernameField);
        Assert.assertTrue(emailIsRequired);
        boolean passwordIsRequired = (Boolean) js.executeScript("return arguments[0].required;", loginPage.passwordField);
        Assert.assertTrue(passwordIsRequired);
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));
    }

    @Test()
    public void loginWithNoCredentials() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginButton.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean emailIsRequired = (Boolean) js.executeScript("return arguments[0].required;", loginPage.usernameField);
        Assert.assertTrue(emailIsRequired);
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));
    }

    @Test()
    public void loginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.usernameField.sendKeys(ConfigReader.getProperty("username1"));
        loginPage.passwordField.sendKeys(fake.internet().password());
        loginPage.loginButton.click();
        Assert.assertTrue(driver.getPageSource().contains("Login Failed"));
    }

}
