package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.SeleniumUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;

    protected static ExtentReports reporter;
    protected static ExtentSparkReporter htmlReporter;
    protected static ExtentTest logger;


    @BeforeSuite(alwaysRun = true)
    public void setupReport() {

        reporter = new ExtentReports();
        String path = System.getProperty("user.dir") + "/test-output/extentReports/index.html";
        htmlReporter = new ExtentSparkReporter(path);
        htmlReporter.config().setReportName("DUOBANK AUTOMATION TESTS");

        reporter.attachReporter(htmlReporter);

        // Configuration settings
        reporter.setSystemInfo("QA Tester", "team_duo10");
        reporter.setSystemInfo("Environment", "Window 10");
        reporter.setSystemInfo("Browser", ConfigReader.getProperty("browser"));
    }


    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setupMethod(@Optional String browser, Method method) {

        driver = Driver.getDriver(browser);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(ConfigReader.getProperty("url"));

        logger = reporter.createTest("TEST CASE: " + method.getName());
    }


    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            logger.pass("PASSED: " + result.getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.skip("SKIPPED: " + result.getName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            logger.fail("FAILED: " + result.getName());
            logger.fail(result.getThrowable());
            String path = SeleniumUtils.getScreenshot("failureScreenshot");
            logger.addScreenCaptureFromPath(path);
        }
        Driver.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownReport() {
        reporter.flush();
    }
}
