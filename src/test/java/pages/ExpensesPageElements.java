package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExpensesPageElements extends PageBase{
    @FindBy(xpath="//label[@for='expense2']")
    public WebElement OwnAHouseButton;
    @FindBy(id="firtmortagagetotalpayment")
    public WebElement totalMortgagePayment;
    @FindBy(xpath = "(//a[@class='btn btn-light-primary'])[2]")
    public WebElement nextButton;
}
