package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExpensesPage extends PageBase {

    @FindBy(xpath = "//*[@id='expense1']")
    public WebElement houseExpenses;

    @FindBy(xpath = "//*[@id='monthlyrentalpayment']")
    public WebElement monthlyRentalPayment;


}
