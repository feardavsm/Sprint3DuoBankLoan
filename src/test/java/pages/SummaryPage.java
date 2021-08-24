package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SummaryPage {

        @FindBy (xpath = "//a[@href='mortagage.php']")
           public WebElement mortgageApplicationButton;



    @FindBy (xpath = "//a[@id='PreApprovalEdit']")
   public WebElement preApprovalEdit;
//a[@id='EmploymentIncomeEdit']

    @FindBy (id = "EmploymentIncomeEdit")
    public WebElement currentMonthlyHousingExpenses;
}
