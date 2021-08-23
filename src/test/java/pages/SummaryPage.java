package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SummaryPage {

        @FindBy (xpath = "//a[@href='mortagage.php']")
            WebElement mortgageApplicationButton;

}
