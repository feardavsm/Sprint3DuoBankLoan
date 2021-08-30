package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.w3c.dom.html.HTMLInputElement;

public class SummaryPage {

    @FindBy(xpath = "//a[@href='mortagage.php']")
    public WebElement mortgageApplicationButton;


    @FindBy(xpath = "//a[@id='PreApprovalEdit']")
    public WebElement preApprovalEdit;
//a[@id='EmploymentIncomeEdit']

    @FindBy(id = "EmploymentIncomeEdit")
    public WebElement currentMonthlyHousingExpenses;

    //*[@id='steps-uid-0']/div[3]/ul/li[3]/a
    // #steps-uid-0 > div.actions.clearfix > ul > li:nth-child(3) > a
    @FindBy(xpath = "//a[contains(text(),'Save')]")
    public WebElement saveButton;


    @FindBy(xpath = "//a[contains(text(),'Edit')]")
    public WebElement allEditButtons;


}