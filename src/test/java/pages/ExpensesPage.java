package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExpensesPage extends PageBase{

    @FindBy(xpath = "//input[@id='monthlyrentalpayment']")
    public WebElement monthlyRentalPaymentField;



@FindBy(xpath = "//a[contains(text(),'Next')]")
public WebElement nextButton;



@FindBy(xpath = "//a[contains(text(),'Previous')]")
public WebElement previousButton;
}
