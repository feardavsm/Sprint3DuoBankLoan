package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmployementAndIncomePageElements extends PageBase{

    @FindBy(xpath = "//label[@for='currentjob1']")
    public WebElement checkboxCurrentJob;

    @FindBy(id = "employername1")
    public WebElement employerName1;

    @FindBy(id = "position1")
    public WebElement position1;

    @FindBy(id = "city")
    public WebElement city1;

    @FindBy(id = "state1")
    public WebElement state1;

    @FindBy(id = "start_date1")
    public WebElement start_date;

    @FindBy(id = "end_date1")
    public WebElement end_date;

    @FindBy(id = "grossmonthlyincome")
    public WebElement grossMonthlyIncome;

    @FindBy(id = "monthlyovertime")
    public WebElement monthlyOverTime;

    @FindBy(id = "monthlybonuses")
    public WebElement monthlyBonuses;

    @FindBy(id = "monthlycommission")
    public WebElement monthlyCommission;

    @FindBy(id = "monthlydividents")
    public WebElement monthlyDividents;

    @FindBy(id = "monthlydividents")
    public WebElement previousButton;

    @FindBy(xpath = "(//a[@class='btn btn-light-primary'])[2]")
    public WebElement nextButton;

    @FindBy(xpath = "//div[@class='borrowertotalmonthlyincome']")
    public WebElement totalIncome;




}
