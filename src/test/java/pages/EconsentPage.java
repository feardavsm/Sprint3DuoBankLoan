package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EconsentPage extends PageBase {

    @FindBy(id="eConsentdeclarerFirstName")
    public WebElement firsName;
    @FindBy(id="eConsentdeclarerLastName")
    public WebElement lastName;
    @FindBy(id="eConsentdeclarerEmail")
    public WebElement email;
    @FindBy(xpath="(//label[@class='custom-control-label'])[1]")
    public WebElement acceptButton;
    @FindBy(id="dontagree")
    public WebElement dontAgreeButton;
    @FindBy(xpath="(//a[@class='btn btn-light-primary'])[2]")
    public WebElement nextButton;
}
