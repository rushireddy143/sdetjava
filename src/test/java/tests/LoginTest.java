package tests;

import base.BaseTest;
import listeners.EmailSuiteListener;
import listeners.RetryAnalyzer;
import org.testng.annotations.*;
import org.testng.Assert;
import pages.LoginPage;
import utils.ExcelUtils;
import org.testng.annotations.Listeners;


@Listeners(EmailSuiteListener.class)
public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return ExcelUtils.getData("src/main/resources/credentials.xlsx", "Sheet1")
            .stream().map(row -> new Object[]{row.get("email"), row.get("password"), row.get("expected")})
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "loginData", retryAnalyzer = RetryAnalyzer.class, groups = {"regression", "login"})
    public void testLogin(String email, String password, String expected) {
        driver.get(utils.ConfigManager.get("base.url"));
        loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        if ("success".equalsIgnoreCase(expected)) {
            Assert.assertTrue(driver.getCurrentUrl().contains("home"));
        } else {
            Assert.assertTrue(loginPage.getErrorMessage().contains("*Could not log you in. Please check your email and password."));
        }
    }
}