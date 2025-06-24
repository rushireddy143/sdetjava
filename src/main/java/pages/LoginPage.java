package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    @FindBy(xpath = "//input[@type='email']")
    WebElement emailInput;

    @FindBy(xpath = "//input[@type='password']")
    WebElement passwordInput;

    @FindBy(xpath = "//div[contains(text(),'Continue')]")
    WebElement loginButton;

    @FindBy(css = "div[data-testid='SignIn-Text-Error']")
    WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}