package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmploymentAndIncomePage extends PageBase{

    @FindBy(xpath = "//a[@href='applications.php']" )
    public WebElement applicationListLink;

    @FindBy (id = "employername1")
    public WebElement employerName;

    @FindBy (id = "position1")
    public WebElement position;

    @FindBy (name = "city[]")
    public WebElement city;

    @FindBy (xpath = "//select[@name='state[]']")
    public WebElement stateSelect;

    @FindBy (xpath = "//input[@name='start_date[]']")
    public WebElement startDate;

}
