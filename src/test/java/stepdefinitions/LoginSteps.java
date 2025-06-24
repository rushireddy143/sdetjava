package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import org.testng.Assert;

public class LoginSteps {
    WebDriver driver;
    LoginPage loginPage;

    @Given("I am on the Diathrive login page")
    public void i_am_on_the_login_page() {
        driver = new ChromeDriver();
        driver.get("https://web-staging.diathrive.com/login");
        loginPage = new LoginPage(driver);
    }

    @When("I enter email {string} and password {string}")
    public void i_enter_email_and_password(String email, String password) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
    }

    @And("I click the login button")
    public void i_click_login_button() {
        loginPage.clickLogin();
    }

    @Then("I should see {string}")
    public void i_should_see_result(String expected) {
        if (expected.equals("Dashboard")) {
            Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        } else {
            Assert.assertTrue(loginPage.getErrorMessage().contains(expected));
        }
        driver.quit();
    }
}