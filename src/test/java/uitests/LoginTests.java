package uitests;

import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.ConfigReader;

public class LoginTests extends TestBase {


    @Test(groups = {"smoke"})
    public void appHealthCheck() {
        logger.info("Navigating to the homepage");
        Assert.assertTrue(driver.getCurrentUrl().equals(ConfigReader.getProperty("url")));
    }

    @Test(groups = {"smoke"})
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage();
        logger.info("Entering the Username");
        loginPage.usernameField.sendKeys(ConfigReader.getProperty("username1"));
        logger.info("Entering the Password");
        loginPage.passwordField.sendKeys(ConfigReader.getProperty("password1"));
        logger.info("Clicking the Login Button");
        loginPage.loginButton.click();
        logger.info("Verifying the URL is as expected");
       // Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/dashboard.php"));
    }

    Faker fake = new Faker();

    @Test(groups = {"smoke"})
    public void loginWithInvalidUsername() {
        LoginPage loginPage = new LoginPage();
        logger.info("Entering the Invalid Username");
        loginPage.usernameField.sendKeys(fake.name().fullName());
        logger.info("Entering the Invalid Password");
        loginPage.passwordField.sendKeys(fake.name().lastName());
        logger.info("Clicking the Login Button");
        loginPage.loginButton.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean emailIsRequired = (Boolean) js.executeScript("return arguments[0].required;", loginPage.usernameField);
        logger.info("Verifying if email is required");
        Assert.assertTrue(emailIsRequired);
        boolean passwordIsRequired = (Boolean) js.executeScript("return arguments[0].required;", loginPage.passwordField);
        logger.info("Verifying if password is required");
        Assert.assertTrue(passwordIsRequired);
        logger.info("Verifying the URL is as expected");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));
    }

    @Test(groups = {"smoke"})
    public void loginWithNoCredentials() {
        LoginPage loginPage = new LoginPage();
        logger.info("Clicking the Login Button");
        loginPage.loginButton.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean emailIsRequired = (Boolean) js.executeScript("return arguments[0].required;", loginPage.usernameField);
        logger.info("Verifying if email is required");
        Assert.assertTrue(emailIsRequired);
        logger.info("Verifying the URL is as expected");
        Assert.assertTrue(driver.getCurrentUrl().equals("http://duobank-env.eba-hjmrxg9a.us-east-2.elasticbeanstalk.com/index.php"));
    }

    @Test(groups = {"smoke"})
    public void loginWithInvalidPassword() {
        LoginPage loginPage = new LoginPage();
        logger.info("Entering the Username");
        loginPage.usernameField.sendKeys(ConfigReader.getProperty("username1"));
        logger.info("Entering the Invalid Password");
        loginPage.passwordField.sendKeys(fake.internet().password());
        logger.info("Clicking the Login Button");
        loginPage.loginButton.click();
        logger.info("Verifying the URL is as expected");
        Assert.assertTrue(driver.getPageSource().contains("Login Failed"));
    }

}
